package com.Twg.SpringBoot.Library.Service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Twg.SpringBoot.Library.Entities.Reservation;
import com.Twg.SpringBoot.Library.ExceptionHandler.ResourceNotFoundException;
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
		List<Reservation> reservations=reservationRepository.findByUserid(userid);
		if(reservations.isEmpty())
		{
			throw new ResourceNotFoundException("Reservations not found with userid:"+userid);
		}
		return reservations;
	}
	@Override
	public List<Reservation> getAllReservations() 
	{
		List<Reservation> reservations=reservationRepository.findAll();
		if(reservations.isEmpty())
		{
			throw new ResourceNotFoundException("Reservations are not listed");
		}
		return reservations;
	}
	@Override
	public Reservation getReservationById(Integer id) {
		
		return reservationRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Reservation not found with id:"+id));
	}
	@Override
	public Reservation  updateReservation(Reservation reservation)
	{
		Reservation updatedreservation=reservationRepository.save(reservation);
		return updatedreservation;
		
	}
}
