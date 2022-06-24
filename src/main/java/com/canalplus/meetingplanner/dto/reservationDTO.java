package com.canalplus.meetingplanner.dto;

import com.canalplus.meetingplanner.entities.salle;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class reservationDTO {
    salle Salle;
    Integer nbrPlace;
    LocalDateTime startDate;
    LocalDateTime endDate;
    String type;
    Integer nbrwebcamextern;
    Integer nbrtableauextern;
    Integer nbrpieuvreextern;
    Integer nbrecranextern;
}
