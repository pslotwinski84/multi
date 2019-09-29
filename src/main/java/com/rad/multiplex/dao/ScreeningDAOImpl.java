package com.rad.multiplex.dao;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rad.multiplex.model.NotFoundException;
import com.rad.multiplex.model.Screening;
@Repository
public class ScreeningDAOImpl implements ScreeningDAO {
	
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}
	
	@SuppressWarnings("unchecked")
	public Screening getScreeningById(int id) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			Screening s = (Screening) session.createQuery("select c from Screening as c where c.id=:eid")
					.setParameter("eid", id).getSingleResult();
			return s;

		} catch (NoResultException e) {
			throw new NotFoundException("no such screening");
		}

		

	}
	
	@SuppressWarnings("unchecked")
	public List<Screening> getInterval(LocalDateTime start, LocalDateTime end) {

		Session session = this.sessionFactory.getCurrentSession();
		List<Screening> s = session.createQuery("select c from Screening as c where c.date>=:start and c.date<=:end")
				.setParameter("start", start).setParameter("end", end).list();

		if (!s.isEmpty()) {
			return s;
		} else {
			throw new NotFoundException("no screenings in the given interval");
		}

	}

	public void addScreening(Screening s) {
	}

	public void updateScreening(Screening s) {
	}
}