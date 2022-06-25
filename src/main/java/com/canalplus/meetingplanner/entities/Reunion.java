package com.canalplus.meetingplanner.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Reunion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String type;
    private Integer nbWebcam;
    private Integer nbTableau;
    private Integer nbPieuvre;
    private Integer nbEcran;
}
