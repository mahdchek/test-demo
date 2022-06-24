package com.canalplus.meetingplanner.services;

import com.canalplus.meetingplanner.dto.reservationDTO;
import com.canalplus.meetingplanner.entities.reservation;
import com.canalplus.meetingplanner.entities.salle;
import com.canalplus.meetingplanner.repositories.reservationRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class reservationService {

    private final reservationRepository reservationRepository;
    @Value("${nbrwebcamextern}")
    private Integer nbrwebcamextern;
    @Value("${nbrtableauextern}")
    private Integer nbrtableauextern;
    @Value("${nbrpieuvreextern}")
    private Integer nbrpieuvreextern;
    @Value("${nbrecranextern}")
    private Integer nbrecranextern;

    public reservationService(com.canalplus.meetingplanner.repositories.reservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<reservation> getAllreservation() {
        return reservationRepository.findAll();
    }

    public void add(reservationDTO reservationDTO) {
        reservation newReservation = new reservation();
        newReservation.setDatedebut(reservationDTO.getStartDate());
        newReservation.setDatefin(reservationDTO.getEndDate());
        newReservation.setType(reservationDTO.getType());
        newReservation.setSalle(reservationDTO.getSalle());
        newReservation.setNbrecranextern(reservationDTO.getNbrecranextern());
        newReservation.setNbrpieuvreextern(reservationDTO.getNbrpieuvreextern());
        newReservation.setNbrtableauextern(reservationDTO.getNbrtableauextern());
        newReservation.setNbrwebcamextern(reservationDTO.getNbrwebcamextern());
        try {
            reservationRepository.save(newReservation);
        } catch (Exception e) {
            System.err.println("error" + e.getMessage());
        }

    }

    public Map<String, Integer> equipementLibre(List<reservation> listReservation) {

        Map<String, Integer> equipementLibreMap = new HashMap<>();
        equipementLibreMap.put("tab", 0);
        equipementLibreMap.put("ecran", 0);
        equipementLibreMap.put("pieuvre", 0);
        equipementLibreMap.put("cam", 0);
        for (reservation r : listReservation
        ) {
            if (r.getNbrtableauextern() != null)
                equipementLibreMap.put("tab", equipementLibreMap.get("tab") + r.getNbrtableauextern());
            if (r.getNbrecranextern() != null)
                equipementLibreMap.put("ecran", equipementLibreMap.get("ecran") + r.getNbrecranextern());
            if (r.getNbrpieuvreextern() != null)
                equipementLibreMap.put("pieuvre", equipementLibreMap.get("pieuvre") + r.getNbrpieuvreextern());
            if (r.getNbrwebcamextern() != null)
                equipementLibreMap.put("cam", equipementLibreMap.get("cam") + r.getNbrwebcamextern());
        }
        equipementLibreMap.put("tab", nbrtableauextern - equipementLibreMap.get("tab"));
        equipementLibreMap.put("ecran", nbrecranextern - equipementLibreMap.get("ecran"));
        equipementLibreMap.put("pieuvre", nbrpieuvreextern - equipementLibreMap.get("pieuvre"));
        equipementLibreMap.put("cam", nbrwebcamextern - equipementLibreMap.get("cam"));

        return equipementLibreMap;
    }
}
