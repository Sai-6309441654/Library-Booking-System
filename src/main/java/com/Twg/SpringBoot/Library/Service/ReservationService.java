package com.Twg.SpringBoot.Library.Service;

import java.util.List;


import com.Twg.SpringBoot.Library.Entities.Reservation;

public interface ReservationService 
{
	 public Reservation getReservationById(Integer id);
     public Reservation saveReservation(Reservation reservation);
     public List<Reservation> getReservationsByUserId(Integer userid);
     public List<Reservation> getAllReservations();
     public Reservation updateReservation(Reservation reservation);
}
