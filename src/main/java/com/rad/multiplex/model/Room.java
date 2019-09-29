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
@Table(name="ROOM")

public class Room {
	@Expose
private int rows;
	@Expose
private int cols;
	@Expose
private int screensize;




@Id
@Column(name="roomid")
@GeneratedValue(strategy=GenerationType.IDENTITY)

private int id;
@OneToMany(fetch = FetchType.EAGER, mappedBy="room")
@Cascade(CascadeType.ALL)

private List<Screening> screenings;

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public int getRows() {
	return rows;
}

public void setRows(int rows) {
	this.rows = rows;
}

public int getCols() {
	return cols;
}

public void setCols(int cols) {
	this.cols = cols;
}

public int getScreensize() {
	return screensize;
}

public void setScreensize(int screensize) {
	this.screensize = screensize;
}

}
