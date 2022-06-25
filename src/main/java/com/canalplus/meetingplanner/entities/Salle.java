package com.canalplus.meetingplanner.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Salle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer nbPlace;
    private Integer nbWebcam;
    private Integer nbTableau;
    private Integer nbPieuvre;
    private Integer nbEcran;
    @OneToMany(mappedBy = "salle")
    private List<Reservation> reservationList;


}
