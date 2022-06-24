package com.canalplus.meetingplanner.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class salle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer nbrplace;
    private Integer nbrwebcam;
    private Integer nbrtableau;
    private Integer nbrpieuvre;
    private Integer nbrecran;


}
