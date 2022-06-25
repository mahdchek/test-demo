package com.canalplus.meetingplanner.services;

import com.canalplus.meetingplanner.dto.ReservationDTO;
import com.canalplus.meetingplanner.dto.ReunionDto;
import com.canalplus.meetingplanner.entities.Reservation;
import com.canalplus.meetingplanner.entities.Reunion;
import com.canalplus.meetingplanner.entities.Salle;
import com.canalplus.meetingplanner.enums.ReunionType;
import com.canalplus.meetingplanner.exceptions.ReservationException;
import com.canalplus.meetingplanner.repositories.EquipementRepository;
import com.canalplus.meetingplanner.repositories.ReservationRepository;
import com.canalplus.meetingplanner.repositories.ReunionRepository;
import com.canalplus.meetingplanner.repositories.SalleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final EquipementRepository equipementRepository;
    private final SalleRepository salleRepository;
    private final ReunionRepository reunionRepository;

    public ReservationService(ReservationRepository reservationRepository, EquipementRepository equipementRepository, SalleRepository salleRepository, ReunionRepository reunionRepository) {
        this.reservationRepository = reservationRepository;
        this.equipementRepository = equipementRepository;
        this.salleRepository = salleRepository;
        this.reunionRepository = reunionRepository;
    }

    public List<Salle> checkEquipementSalle(List<Salle> sallesDispo, String reunionType) {

        List<Salle> sallesEquipes = new ArrayList<>();

        for (Salle salle : sallesDispo) {
            Reunion reunion = reunionRepository.findByType(reunionType);
            if ((reunion.getNbEcran() <= salle.getNbEcran()) &&
                    (reunion.getNbPieuvre() <= salle.getNbPieuvre()) &&
                    (reunion.getNbTableau() <= salle.getNbTableau()) &&
                    (reunion.getNbWebcam() <= salle.getNbWebcam()))
                sallesEquipes.add(salle);
        }
        return sallesEquipes;
    }

    public List<Salle> checkEquipementSalleAvecEquipementsExternes(List<Salle> sallesDispo, String reunionType,
                                                                   int nbTableaux, int nbPieuvres, int nbEcrans, int nbWebcams) {

        List<Salle> sallesEquipes = new ArrayList<>();

        for (Salle salle : sallesDispo) {
            Reunion reunion = reunionRepository.findByType(reunionType);
            if ((reunion.getNbEcran() <= salle.getNbEcran() + nbEcrans) &&
                    (reunion.getNbPieuvre() <= salle.getNbPieuvre() + nbPieuvres) &&
                    (reunion.getNbTableau() <= salle.getNbTableau() + nbTableaux) &&
                    (reunion.getNbWebcam() <= salle.getNbWebcam() + nbWebcams))
                sallesEquipes.add(salle);
        }
        return sallesEquipes;
    }

    public Salle chercherMeilleureSalle(List<Salle> sallesDispo) {
        Salle meilleureSalle = null;
        for (Salle salle : sallesDispo) {
            if (meilleureSalle == null) meilleureSalle = salle;
            if (salle.getNbPlace() < meilleureSalle.getNbPlace()) meilleureSalle = salle;
        }
        return meilleureSalle;
    }

    public ReservationDTO reserver(ReunionDto reunionDto) throws ReservationException {

        if (reunionDto.getStartDate().getHour() > 19 || reunionDto.getStartDate().getHour() < 8)
            throw new ReservationException("Impossible de réserver dans ces horaires");

        if (ReunionType.RS == ReunionType.valueOf(reunionDto.getType())){
            reunionDto.setNbPersonnes(3);
        }

        List<Reservation> reservationList = reservationRepository.findAllByDateDebutAndDateFin(reunionDto.getStartDate(), reunionDto.getEndDate().plusHours(1L));

        int nbWebCamDispo = equipementRepository.findById(1).get().getNbWebcam() - reservationList.stream()
                .map(Reservation::getNbWebCamExternes)
                .reduce(0, Integer::sum);

        int nbTableauxDispo = equipementRepository.findById(1).get().getNbTableau() - reservationList.stream()
                .map(Reservation::getNbTableauExternes)
                .reduce(0, Integer::sum);

        int nbPieuvresDispo = equipementRepository.findById(1).get().getNbPieuvre() - reservationList.stream()
                .map(Reservation::getNbPieuvreExternes)
                .reduce(0, Integer::sum);

        int nbEcransDispo = equipementRepository.findById(1).get().getNbEcran() - reservationList.stream()
                .map(Reservation::getNbEcranExternes)
                .reduce(0, Integer::sum);


        List<Salle> sallesOccupees = reservationList.stream()
                .map(Reservation::getSalle)
                .collect(Collectors.toList());

        List<Salle> sallesDispos = salleRepository.findAll().stream()
                .filter(salle -> !sallesOccupees.contains(salle))
                .collect(Collectors.toList());

        sallesDispos = sallesDispos.stream()
                .filter(salle -> salle.getNbPlace() * 0.7 >= reunionDto.getNbPersonnes())
                .collect(Collectors.toList());

        Reservation reservation = Reservation.builder()
                .dateDebut(reunionDto.getStartDate())
                .dateFin(reunionDto.getEndDate().plusHours(1L))
                .typeReunion(ReunionType.valueOf(reunionDto.getType()))
                .build();

        List<Salle> sallesEquipees = checkEquipementSalle(sallesDispos, reunionDto.getType());


        Salle meilleureSalle = null;

        if (sallesEquipees.size() == 0) {
            sallesEquipees = checkEquipementSalleAvecEquipementsExternes(sallesDispos, reunionDto.getType(),
                    nbTableauxDispo, nbPieuvresDispo, nbEcransDispo, nbWebCamDispo);
            if (sallesEquipees.size() > 0) {
                meilleureSalle = chercherMeilleureSalle(sallesEquipees);
                Reunion reunion = reunionRepository.findByType(reunionDto.getType());
                reservation.setNbPieuvreExternes(reunion.getNbPieuvre() - meilleureSalle.getNbPieuvre());
                reservation.setNbTableauExternes(reunion.getNbTableau() - meilleureSalle.getNbTableau());
                reservation.setNbEcranExternes(reunion.getNbEcran() - meilleureSalle.getNbEcran());
                reservation.setNbWebCamExternes(reunion.getNbWebcam() - meilleureSalle.getNbWebcam());
                reservation.setSalle(meilleureSalle);
            }
        } else {
            meilleureSalle = chercherMeilleureSalle(sallesEquipees);
            reservation.setNbPieuvreExternes(0);
            reservation.setNbTableauExternes(0);
            reservation.setNbEcranExternes(0);
            reservation.setNbWebCamExternes(0);
            reservation.setSalle(meilleureSalle);
        }

        if (meilleureSalle == null) throw new ReservationException("Aucune salle ne répond à vos critères");


        reservationRepository.save(reservation);


        return ReservationDTO.builder()
                .nomSalle(meilleureSalle.getName())
                .startDate(reservation.getDateDebut())
                .endDate(reservation.getDateFin().minusHours(1L))
                .nbPersonnes(reunionDto.getNbPersonnes())
                .build();
    }
}
