package com.Twg.SpringBoot.Library.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Twg.SpringBoot.Library.Entities.Book;
import com.Twg.SpringBoot.Library.Entities.Reservation;
import com.Twg.SpringBoot.Library.Entities.User;
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> 
{

	public List<Reservation> findByUserid(int userid);
    public List<Reservation> findByBookid(int bookid);
	public List<Reservation> findByStatus(String status);
}
