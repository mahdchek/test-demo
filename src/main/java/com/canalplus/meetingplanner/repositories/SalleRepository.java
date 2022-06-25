package com.canalplus.meetingplanner.repositories;

import com.canalplus.meetingplanner.entities.Salle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalleRepository extends JpaRepository<Salle, Integer> {

}
