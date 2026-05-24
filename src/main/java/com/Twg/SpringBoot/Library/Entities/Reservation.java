package com.Twg.SpringBoot.Library.Entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userid;

    @Column(name = "book_id", nullable = false)
    private Integer bookid;

    @Column(name = "issue_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date issueDate;

    @Column(name = "return_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date returnDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReservationStatus status = ReservationStatus.ACTIVE;

    // enum to match the database ENUM type
    public enum ReservationStatus {
        ACTIVE,
        RETURNED
    }
    
	public Reservation() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getBookid() {
		return bookid;
	}

	public void setBookid(Integer bookid) {
		this.bookid = bookid;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public ReservationStatus getStatus() {
		return status;
	}

	public void setStatus(ReservationStatus status) {
		this.status = status;
	}

	public Reservation(Integer id, Integer userid, Integer bookid, Date issueDate, Date returnDate,
			ReservationStatus status) {
		super();
		this.id = id;
		this.userid = userid;
		this.bookid = bookid;
		this.issueDate = issueDate;
		this.returnDate = returnDate;
		this.status = status;
	}

	@Override
	public String toString() {
		return "Reservation [id=" + id + ", userid=" + userid + ", bookid=" + bookid + ", issueDate=" + issueDate
				+ ", returnDate=" + returnDate + ", status=" + status + "]";
	}
    
	
}
