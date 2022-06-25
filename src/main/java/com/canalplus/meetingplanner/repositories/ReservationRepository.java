package com.canalplus.meetingplanner.repositories;

import com.canalplus.meetingplanner.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {


    List<Reservation> findAllByDateDebutAndDateFin(LocalDateTime startReunion, LocalDateTime endReunion);
}
