package com.rad.multiplex.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.google.gson.annotations.Expose;

@Entity
@Table(name="MOVIE")
public class Movie {
	
	
	@Id
	@Column(name="movieid")
	@GeneratedValue(strategy=GenerationType.IDENTITY)

	private int id;
	@Expose
	private String title;
	@Expose
	private String description;
	@Expose
	private int duration;

	@OneToMany(fetch = FetchType.EAGER, mappedBy="movie")
	@Cascade(CascadeType.ALL)
	
	private List<Screening> screenings;
	
	public String getTitle() {
		return title;
	}

	public List<Screening> getScreenings() {
		return screenings;
	}

	public void setScreenings(List<Screening> screenings) {
		this.screenings = screenings;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

}
