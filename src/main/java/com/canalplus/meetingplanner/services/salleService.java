package com.canalplus.meetingplanner.services;

import com.canalplus.meetingplanner.dto.salleDispo;
import com.canalplus.meetingplanner.entities.reservation;
import com.canalplus.meetingplanner.entities.salle;
import com.canalplus.meetingplanner.repositories.salleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class salleService {
    private final salleRepository salleRepository;
    private final reservationService reservationService;

    public salleService(salleRepository salleRepository, reservationService reservationService) {
        this.salleRepository = salleRepository;
        this.reservationService = reservationService;
    }


    public List<salle> getAllsalles() {
        return salleRepository.findAll();
    }

    public salleDispo getFreesalles(LocalDateTime startDate, LocalDateTime endDate, Integer nbrPlace, String type) {
        //if (startDate.getHour() < 8 | endDate.getHour() > 20)

        List<reservation> listReservation = reservationService.getAllreservation();
        List<reservation> newlistReservation = listReservation.stream()
                .filter(reservation -> (startDate.isBefore(reservation.getDatefin().plusHours(1)) && endDate.isAfter(reservation.getDatedebut())))
                .collect(Collectors.toList());

        Map<String, Integer> equipementLibre = reservationService.equipementLibre(newlistReservation);

        List<salle> listSalle = salleRepository.findAll().stream()
                .filter(salle -> (salle.getNbrplace() * 0.7) >= nbrPlace).collect(Collectors.toList());
        boolean exist, premierElement = true;
        salleDispo meilleurSalle = new salleDispo();
        for (salle s : listSalle
        ) {
            if (premierElement) {
                meilleurSalle.setSalle(s);
                premierElement = false;
            }
            exist = false;
            for (reservation r : newlistReservation
            ) {
                if (s.getId().equals(r.getSalle().getId())) exist = true;

            }
            if (!exist) {
                meilleurSalle = meilleurSalle(meilleurSalle, s, type, nbrPlace, equipementLibre);
            }
        }

        return meilleurSalle;
    }

    public salleDispo meilleurSalle(salleDispo salle1, salle salle2, String type, Integer nbrPlace, Map<String, Integer> equipementlibre) {

        try {
            Integer nbrcamext = 0;
            Integer nbrpieuvreext = 0;
            Integer nbrecranext = 0;
            Integer nbrtabext = 0;
            switch (type) {
                case "VC":

                    if ((salle1.getSalle().getNbrplace() - nbrPlace >= salle2.getNbrplace() - nbrPlace) &&
                            salle1.getSalle().getNbrpieuvre() + salle1.getSalle().getNbrecran() + salle1.getSalle().getNbrwebcam()
                                    <= salle2.getNbrpieuvre() + salle2.getNbrecran() + salle2.getNbrwebcam()) {
                        salle1.setSalle(salle2);
                        if (salle1.getSalle().getNbrwebcam() == 0 && equipementlibre.get("cam") != 0) nbrcamext = 1;
                        if (salle1.getSalle().getNbrpieuvre() == 0 && equipementlibre.get("pieuvre") != 0)
                            nbrpieuvreext = 1;
                        if (salle1.getSalle().getNbrecran() == 0 && equipementlibre.get("ecran") != 0) nbrecranext = 1;

                        salle1.setNbrecranextern(nbrecranext);
                        salle1.setNbrpieuvreextern(nbrpieuvreext);
                        salle1.setNbrwebcamextern(nbrcamext);
                    }
                    break;
                case "SPEC":

                    if ((salle1.getSalle().getNbrplace() - nbrPlace >= salle2.getNbrplace() - nbrPlace) &&
                            salle2.getNbrtableau() != 0) {
                        salle1.setSalle(salle2);
                        if (salle1.getSalle().getNbrtableau() == 0 && equipementlibre.get("tab") != 0) nbrtabext = 1;
                        salle1.setNbrtableauextern(nbrtabext);
                    }
                    break;
                case "RS":
                    nbrPlace = 3;
                    if ((salle1.getSalle().getNbrplace() - nbrPlace >= salle2.getNbrplace() - nbrPlace)) {
                        salle1.setSalle(salle2);
                    }
                    break;
                case "RC":

                    if ((salle1.getSalle().getNbrplace() - nbrPlace >= salle2.getNbrplace() - nbrPlace) &&
                            salle1.getSalle().getNbrpieuvre() + salle1.getSalle().getNbrecran() + salle1.getSalle().getNbrtableau()
                                    <= salle2.getNbrpieuvre() + salle2.getNbrecran() + salle2.getNbrtableau()) {
                        salle1.setSalle(salle2);
                        if (salle1.getSalle().getNbrtableau() == 0 && equipementlibre.get("tab") != 0) nbrtabext = 1;
                        if (salle1.getSalle().getNbrpieuvre() == 0 && equipementlibre.get("pieuvre") != 0)
                            nbrpieuvreext = 1;
                        if (salle1.getSalle().getNbrecran() == 0 && equipementlibre.get("ecran") != 0) nbrecranext = 1;

                        salle1.setNbrecranextern(nbrecranext);
                        salle1.setNbrpieuvreextern(nbrpieuvreext);
                        salle1.setNbrtableauextern(nbrtabext);
                    }
                    break;
                default:

                    break;
            }

        } catch (Exception e) {
            System.err.println("error" + e.getMessage());

        }
        return salle1;
    }


}
