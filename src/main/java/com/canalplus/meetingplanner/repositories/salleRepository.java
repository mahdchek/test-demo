package com.canalplus.meetingplanner.repositories;

import com.canalplus.meetingplanner.entities.salle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface salleRepository extends JpaRepository<salle, Integer> {

}
