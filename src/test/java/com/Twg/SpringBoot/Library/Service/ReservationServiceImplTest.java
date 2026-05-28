package com.Twg.SpringBoot.Library.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.Twg.SpringBoot.Library.Entities.Reservation;
import com.Twg.SpringBoot.Library.Entities.Reservation.ReservationStatus;
import com.Twg.SpringBoot.Library.ExceptionHandler.ResourceNotFoundException;
import com.Twg.SpringBoot.Library.Repository.ReservationRepository;

@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;
    //Expected User
    private Reservation reservationSample;

    @BeforeEach
    void setUp() {
        // Build clear mock data before each individual test runs
        reservationSample = new Reservation();
        reservationSample.setId(501);
        reservationSample.setUserid(10);
        reservationSample.setBookid(7);
        reservationSample.setIssueDate(Date.valueOf("2026-05-28"));
        reservationSample.setReturnDate(Date.valueOf("2026-06-04"));
        reservationSample.setStatus(ReservationStatus.ACTIVE);
    }

    

    @Test
    @DisplayName("saveReservation() - Should successfully save and return reservation details")
    void saveReservation_Success() 
    {
        // Arrange
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservationSample);

        // Actual User
        Reservation savedReservation = reservationService.saveReservation(new Reservation());

        // Assert
        assertNotNull(savedReservation);
        assertEquals(reservationSample.getId(),savedReservation.getId());
        assertEquals(reservationSample.getUserid(),savedReservation.getUserid());
        assertEquals(reservationSample.getUserid(),savedReservation.getUserid());
        assertThat(reservationSample.getStatus()).isEqualTo(savedReservation.getStatus());
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    @DisplayName("getReservationsByUserId() - Should return list when user has active reservations")
    void getReservationsByUserId_Success() {
        // Arrange
        when(reservationRepository.findByUserid(reservationSample.getUserid())).thenReturn(Arrays.asList(reservationSample));

        // Act
        List<Reservation> reservationsfoundByUserId = reservationService.getReservationsByUserId(reservationSample.getUserid());

        // Assert
        assertThat(reservationsfoundByUserId).isNotEmpty();
        assertThat(reservationsfoundByUserId).hasSize(1);
        assertEquals(reservationSample.getUserid(),reservationsfoundByUserId.get(0).getUserid());
        verify(reservationRepository, times(1)).findByUserid(10);
    }
    
    @Test
    @DisplayName("getReservationsByUserId() - Should throw ResourceNotFoundException when result list is empty")
    void getReservationsByUserId_ThrowsResourceNotFoundException() {
        // Arrange
        when(reservationRepository.findByUserid(99)).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            reservationService.getReservationsByUserId(99);
        });
    }
    
    @Test
    @DisplayName("getAllReservations() - Should return complete list of reservations when table is populated")
    void getAllReservations_Success() 
    {
        // Arrange
        when(reservationRepository.findAll()).thenReturn(Arrays.asList(reservationSample));

        // Act
        List<Reservation> reservations = reservationService.getAllReservations();

        // Assert
        assertThat(reservations).isNotEmpty();
        assertThat(reservations).hasSize(1);
        verify(reservationRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("getAllReservations() - Should throw ResourceNotFoundException when zero reservations are registered")
    void getAllReservations_ThrowsResourceNotFoundException() {
        // Arrange
        when(reservationRepository.findAll()).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            reservationService.getAllReservations();
        });
    }
    
    @Test
    @DisplayName("getReservationById() - Should locate and extract specific reservation object")
    void getReservationById_Success() {
        // Arrange
        when(reservationRepository.findById(reservationSample.getId())).thenReturn(Optional.of(reservationSample));

        // Act
        Reservation reservationFoundById = reservationService.getReservationById(reservationSample.getId());

        // Assert
        assertNotNull(reservationFoundById);
        assertEquals(reservationSample.getId(),reservationFoundById.getId());
    }

    @Test
    @DisplayName("getReservationById() - Should throw ResourceNotFoundException when targeted ID doesn't exist")
    void getReservationById_ThrowsResourceNotFoundException() {
        // Arrange
        when(reservationRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> 
        {
            reservationService.getReservationById(999);
        });
    }
    
    @Test
    @DisplayName("updateReservation() - Should successfully run database save operation on updates")
    void updateReservation_Success() 
    {
        // Arrange
        when(reservationRepository.save(reservationSample)).thenReturn(reservationSample);

        // Act
        Reservation updatedReservation= reservationService.updateReservation(reservationSample);

        // Assert
        assertNotNull(updatedReservation);
        verify(reservationRepository, times(1)).save(reservationSample);
    }

    

   
}
