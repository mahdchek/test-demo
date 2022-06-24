package com.canalplus.meetingplanner.controllers;

import com.canalplus.meetingplanner.dto.reservationDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.canalplus.meetingplanner.services.reservationService;

@RestController
public class reservationController {
    private final reservationService reservationService;

    public reservationController(reservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("reserver")
    public String reserver(@RequestBody reservationDTO reservationDTO) {
        reservationService.add(reservationDTO);

        return "Votre salle est bien réservée";
    }
}
