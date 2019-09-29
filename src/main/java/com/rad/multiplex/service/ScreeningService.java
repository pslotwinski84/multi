package com.rad.multiplex.service;

import java.time.LocalDateTime;
import java.util.List;

import com.rad.multiplex.model.Reservation;
import com.rad.multiplex.model.Response;
import com.rad.multiplex.model.Screening;

public interface ScreeningService {
	public List<Screening> getInterval(LocalDateTime start, LocalDateTime end);

	public String getSeats(Integer screeningId);

	public Response processReservation(Reservation reservation);
}
