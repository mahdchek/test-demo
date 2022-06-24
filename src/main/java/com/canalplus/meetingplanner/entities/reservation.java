package com.canalplus.meetingplanner.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime datedebut;
    private LocalDateTime datefin;
    private String type;
    private Integer nbrwebcamextern;
    private Integer nbrtableauextern;
    private Integer nbrpieuvreextern;
    private Integer nbrecranextern;

    @ManyToOne
    @JoinColumn(name = "idSalle")
    private salle salle;

}
