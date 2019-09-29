package com.rad.multiplex.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rad.multiplex.dao.ScreeningDAO;
import com.rad.multiplex.model.BadRequestException;
import com.rad.multiplex.model.Reservation;
import com.rad.multiplex.model.Response;
import com.rad.multiplex.model.Screening;


@Service("screeningService")
public class ScreeningServiceImpl implements ScreeningService {

	private Gson gson=new Gson();
	@Autowired
	private ScreeningDAO screeningDAO;

	@Transactional
	public List<Screening> getInterval(LocalDateTime start, LocalDateTime end) {
		
		List<Screening> temp = screeningDAO.getInterval(start, end);
		
		temp.sort(
	    			Comparator.<Screening, String>comparing(s -> s.getMovie().getTitle())
	    					.thenComparing(s -> s.getDate()));
	    	   
	    return temp;
	}

	@Transactional
	public String getSeats(Integer id){
		
		Screening screeningToSend=screeningDAO.getScreeningById(id);
			
		String[][] tempseats=gson.fromJson(screeningToSend.getSeats(),new TypeToken<String[][]>(){}.getType());
		
		//here we reset the reservation table - if the seat's not free, we set the field to "res", as we don't want to give names to the client. what we also do here is reject outdated reservations - it's explained further, because we do the same thing when doing a submit.
		
		for(int i=0;i<tempseats.length;i++) {
			for (int j=0;j<tempseats[i].length;j++) {
				if (!tempseats[i][j].equals("0")) {
					String nameanddate=tempseats[i][j];
					if(LocalDateTime.parse(nameanddate.substring(nameanddate.indexOf(';')+1, nameanddate.length()))
							.isAfter(LocalDateTime.now())) {
						tempseats[i][j]="res";
					}
					else {
						//the reservation is outdated and we show it in the view. it's worth to note that the actual update of the database happens in another moment - during submit processing, when somebody books an "outdated" seat.
						tempseats[i][j]="0";
					}
				}
			}
		}
		
		String seatsToSend=gson.toJson(tempseats);
		
		return seatsToSend;
		
	}
	@Transactional
	public Response processReservation(Reservation reservation) {
		System.out.println(reservation.getName());
		if(!reservation.getName().matches( "[A-ZĄĆĘŁŃÓŚŻŹ]{1}[a-ząćęłńóśżź]{2,}[\\s]{1}[A-ZĄĆĘŁŃÓŚŻŹ]{1}[a-ząćęłńóśżź]{2,}(-{1}[A-ZĄĆĘŁŃÓŚŻŹ]{1}[a-ząćęłńóśżź]{2,}|\\b)$")) {
			
			return new Response(false, null, null, "malformed name");
		
		}
		Screening screening=screeningDAO.getScreeningById(reservation.getId());
		
		for(int i=0;i<reservation.getSeats().size();i++) {
			if (reservation.getSeats().get(i).size()!=3) {
				throw new BadRequestException("malformed request. bad object size");
			}
		
		}
		
		if(reservation.getSeats().size()==0||reservation.getSeats()==null) {
			throw new BadRequestException("reservation has to reserve at least one seat");
		}
		
		for(int i=0;i<reservation.getSeats().size();i++) {
			if(reservation.getSeats().get(i).get(0)<0||reservation.getSeats().get(i).get(0)>=screening.getRoom().getRows() || reservation.getSeats().get(i).get(1)<0 || reservation.getSeats().get(i).get(1)>=screening.getRoom().getCols()){
				throw new BadRequestException("malformed request. seat coordinates are beyond room size");
				}
		}
		
		if (screening.getDate().compareTo(LocalDateTime.now().plusMinutes(15) )<0) {
			return new Response(false,null,null,"reservation can be made minimum 15 minutes before screening");
		}
		//it will be our "modified" seats data. we change the fields here with regard to the submit info and at the last step we check if theres is no single place left over between two already reserved seats. if everything is fine, we save it and replace the old data with the modifed one.
		String[][] seatstemp= gson.fromJson(screening.getSeats(),new TypeToken<String[][]>(){}.getType());
		
		// it will be our "previous" data clone. we take it in every step of a loop to compare it with with submit info. if there is no error, we modify the modified copy, seatstemp.		
		String[][] alsotemp=seatstemp.clone();
		
		BigDecimal amount=new BigDecimal(0.00);

		LocalDateTime expiration = LocalDateTime.now().plusDays(3);
		// the reservations are stored in "Piotr Kowal;01-01-01T18:07" format string. when
		// checking if the seat is free, we compare the previous reservation date to today's date - but first
		// we substring the record to retrieve the date.
		for (int i = 0; i < reservation.getSeats().size(); i++) {
			
			String nameanddate = alsotemp[reservation.getSeats().get(i).get(0)][reservation.getSeats().get(i).get(1)];
			
			if (nameanddate.equals("0") || (!nameanddate.equals("0")
					&& LocalDateTime.parse(nameanddate.substring(nameanddate.indexOf(';')+1, nameanddate.length()))
							.isBefore(LocalDateTime.now())))

			{
				seatstemp[reservation.getSeats().get(i).get(0)][reservation.getSeats().get(i).get(1)] = reservation
						.getName() + ";" + expiration.toString();
				switch (reservation.getSeats().get(i).get(2)) {
				case 0:
					amount = amount.add(BigDecimal.valueOf(25.00));
					break;
				case 1:
					amount = amount.add(BigDecimal.valueOf(18.00));
					break;
				case 2:
					amount = amount.add(BigDecimal.valueOf(12.50));
					break;
				default:
					return new Response(false, null, null, "malformed ticket code");
				}

			}
			else {
				return new Response(false,null,null,"you tried to reserve already reserved seats");
			}
		}
		
		for (int i = 0; i < seatstemp.length; i++) {
			for (int j = 1; j < seatstemp[i].length - 1; j++) {
				if (seatstemp[i][j].equals("0") && !seatstemp[i][j - 1].equals("0")
						&& !seatstemp[i][j + 1].equals("0")) {
					return new Response(false, null, null,
							"there cannot be a single place left over between 2 already reserved places");
				}
			}
		}
		
		screening.setSeats(gson.toJson(seatstemp));
		
		
		
		return new Response(true, amount, expiration, null);
	}
}
