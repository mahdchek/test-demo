package com.canalplus.meetingplanner.controllers;

import com.canalplus.meetingplanner.dto.searchSalle;
import com.canalplus.meetingplanner.dto.salleDispo;
import com.canalplus.meetingplanner.entities.salle;
import com.canalplus.meetingplanner.services.salleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class salleController {
    private final salleService salleService;

    public salleController(com.canalplus.meetingplanner.services.salleService salleService) {
        this.salleService = salleService;
    }

    @GetMapping("/salles")
    public ResponseEntity<List<salle>> getAllSalles() {
        return ResponseEntity.ok(salleService.getAllsalles());
    }


    @PostMapping("/sallesLibres")
    public ResponseEntity<salleDispo> getFreeSalles(@RequestBody searchSalle body) {
        return ResponseEntity.ok(salleService.getFreesalles(body.getStartDate(), body.getEndDate(), body.getNbrPlace(), body.getType()));
    }
}
