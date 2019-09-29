package com.rad.multiplex.dao;

import java.time.LocalDateTime;
import java.util.List;

import com.rad.multiplex.model.Screening;

public interface ScreeningDAO {
public List<Screening> getInterval(LocalDateTime start, LocalDateTime end);
public Screening getScreeningById(int id);
public void addScreening(Screening s);
public void updateScreening(Screening s);
}
