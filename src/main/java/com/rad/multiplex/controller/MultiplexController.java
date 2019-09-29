package com.rad.multiplex.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.rad.multiplex.model.BadRequestException;
import com.rad.multiplex.model.LocalDateTimeConverter;
import com.rad.multiplex.model.Reservation;
import com.rad.multiplex.model.Response;
import com.rad.multiplex.model.Screening;
import com.rad.multiplex.service.ScreeningService;

@RestController
public class MultiplexController {

	@Autowired
	private ScreeningService screeningService;

	private Gson gson = new GsonBuilder()
			.excludeFieldsWithoutExposeAnnotation()
			.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeConverter()).create();

	@PostMapping("/interval")
	public String getInterval(@RequestBody String string)
			throws JsonSyntaxException, UnsupportedEncodingException {

		string = URLDecoder.decode(string, "UTF-8");

		List<LocalDateTime> times =gson.fromJson(string, new TypeToken<List<LocalDateTime>>() {
				}.getType());

		if (times.size()!=2) {
			throw new BadRequestException("malformed request");
		}
		
		List<Screening> byInterval = screeningService.getInterval(times.get(0), times.get(1));
	
		return gson.toJson(byInterval);

	}

	@PostMapping("/getseats")
	public String getSeats(@RequestBody String string)
			throws JsonSyntaxException, UnsupportedEncodingException {
		string = URLDecoder.decode(string, "UTF-8");

		try {
			
			Integer screeningId =Integer.parseInt(string);


			return screeningService.getSeats(screeningId);
		} catch (NumberFormatException exception) {
			throw new BadRequestException("request was not a number");
		}

	}

	@PostMapping("/submit")
	public String submit(@RequestBody String string) throws UnsupportedEncodingException {

		string = URLDecoder.decode(string, "UTF-8");

		Reservation reservation = gson.fromJson(string, Reservation.class);

		Response response = screeningService.processReservation(reservation);

		return gson.toJson(response);

	}

}