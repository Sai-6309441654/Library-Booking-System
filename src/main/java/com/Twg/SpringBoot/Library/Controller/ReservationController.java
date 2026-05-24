package com.Twg.SpringBoot.Library.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Twg.SpringBoot.Library.Entities.Reservation;
import com.Twg.SpringBoot.Library.Service.ReservationService;

@RestController
@RequestMapping("/reservations")
public class ReservationController 
{
	@Autowired
    private ReservationService reservationService; 
	public ReservationController() 
	{
		
	}
	public ReservationController(ReservationService reservationService) 
	{
		super();
		this.reservationService = reservationService;
	}

	public ReservationService getReservationService() {
		return reservationService;
	}
	public void setReservationService(ReservationService reservationService) {
		this.reservationService = reservationService;
	}
	
	@PostMapping("/")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Reservation> ReserveBook(@RequestBody Reservation reservation)
    {
		
			return ResponseEntity.status(201).body(reservationService.saveReservation(reservation));
		
    }
	@GetMapping("/{userid}")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public 	ResponseEntity<List<Reservation>> viewUserReservations(@PathVariable Integer userid)
	{
		return ResponseEntity.status(200).body(reservationService.getReservationsByUserId(userid));
	}
	@GetMapping("/")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Reservation>> viewReservations()
	{
		return ResponseEntity.status(200).body(reservationService.getAllReservations());
	}
	@PutMapping("/{id}")
	public ResponseEntity<Reservation> updateReservations(@RequestBody Reservation reservation,@PathVariable Integer id)
	{
		Reservation updateReservation=reservationService.getReservationById(id);
		updateReservation.setBookid(reservation.getBookid());
		updateReservation.setUserid(reservation.getUserid());
		updateReservation.setIssueDate(reservation.getIssueDate());
		updateReservation.setReturnDate(reservation.getReturnDate());
		updateReservation.setStatus(reservation.getStatus());
		Reservation updatedReservation =reservationService.updateReservation(updateReservation);
		return ResponseEntity.status(200).body(updatedReservation);
		
		
	}
}
