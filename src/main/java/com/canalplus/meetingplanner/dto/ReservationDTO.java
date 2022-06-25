package com.canalplus.meetingplanner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {

    String nomSalle;
    LocalDateTime startDate;
    LocalDateTime endDate;
    Integer nbPersonnes;
    String statusReservation;
    String errorMessage;


}
