package com.canalplus.meetingplanner.repositories;

import com.canalplus.meetingplanner.entities.Equipement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipementRepository extends JpaRepository<Equipement, Integer> {
}
