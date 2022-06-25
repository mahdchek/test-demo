package com.canalplus.meetingplanner.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ReunionDto {

    LocalDateTime startDate;
    LocalDateTime endDate;
    Integer nbPersonnes;
    String type;
}


