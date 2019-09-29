package com.rad.multiplex.model;


import java.util.List;

import com.google.gson.annotations.Expose;

public class Reservation {
	@Expose
	private String name;
	@Expose
	private int id;
	@Expose
	private List<List<Integer>> seats;

	public Reservation(String name, int id, List<List<Integer>> seats) {
		this.name = name;
		this.id = id;
		this.seats = seats;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<List<Integer>> getSeats() {
		return seats;
	}

	public void setSeats(List<List<Integer>> seats) {
		this.seats = seats;
	}

}
