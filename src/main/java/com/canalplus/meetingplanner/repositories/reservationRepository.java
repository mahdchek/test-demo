package com.canalplus.meetingplanner.repositories;

import com.canalplus.meetingplanner.entities.reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface reservationRepository extends JpaRepository<reservation, Integer> {
}
