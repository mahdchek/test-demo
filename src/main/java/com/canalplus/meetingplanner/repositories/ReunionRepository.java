package com.canalplus.meetingplanner.repositories;

import com.canalplus.meetingplanner.entities.Reunion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReunionRepository extends JpaRepository<Reunion, Integer> {


    Reunion findByType(String type);
}
