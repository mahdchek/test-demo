package com.canalplus.meetingplanner.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class searchSalle {
    LocalDateTime startDate;
    LocalDateTime endDate;
    Integer nbrPlace;
    String type;
}


