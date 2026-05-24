package com.Twg.SpringBoot.Library.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Twg.SpringBoot.Library.Entities.Reservation;
import com.Twg.SpringBoot.Library.Repository.ReservationRepository;
@Service
public class ReservationServiceImpl implements ReservationService
{
	@Autowired
    private ReservationRepository reservationRepository;
	
	public ReservationServiceImpl() 
	{
		
	}
	public ReservationServiceImpl(ReservationRepository reservationRepository) 
	{
		super();
		this.reservationRepository = reservationRepository;
	}
	public ReservationRepository getReservationRepository() 
	{
		return reservationRepository;
	}
	public void setReservationRepository(ReservationRepository reservationRepository) 
	{
		this.reservationRepository = reservationRepository;
	}
	
	@Override
	public Reservation saveReservation(Reservation reservation) 
	{
		return reservationRepository.save(reservation);
	}
	@Override
	public List<Reservation> getReservationsByUserId(Integer userid) 
	{
		return reservationRepository.findByUserid(userid);
	}
	@Override
	public List<Reservation> getAllReservations() 
	{
		return reservationRepository.findAll();
	}
	@Override
	public Reservation getReservationById(Integer id) {
		
		return reservationRepository.findById(id).get();
	}
	@Override
	public Reservation  updateReservation(Reservation reservation)
	{
		return reservationRepository.save(reservation);
	}
}
