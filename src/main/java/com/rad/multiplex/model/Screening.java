package com.rad.multiplex.model;


import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name="SCREENING")

public class Screening {
	@Expose
	@Id
	@Column(name="screeningid")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Expose
	@ManyToOne
	  @JoinColumn(name = "roomid")
	private Room room;
	@Expose
	@ManyToOne
	  @JoinColumn(name = "movieid")
	private Movie movie;
	@Expose
	private LocalDateTime date;
	private String seats;	

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getSeats() {
		return seats;
	}

	public void setSeats(String seats) {
		this.seats = seats;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

}
