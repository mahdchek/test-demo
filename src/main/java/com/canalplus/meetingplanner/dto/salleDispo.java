package com.canalplus.meetingplanner.dto;

import com.canalplus.meetingplanner.entities.salle;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class salleDispo {
    salle salle;
    Integer nbrwebcamextern;
    Integer nbrtableauextern;
    Integer nbrpieuvreextern;
    Integer nbrecranextern;
}
