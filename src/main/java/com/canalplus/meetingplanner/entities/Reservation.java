package com.canalplus.meetingplanner.entities;

import com.canalplus.meetingplanner.enums.ReunionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    @Enumerated(EnumType.STRING)
    private ReunionType typeReunion;
    private Integer nbWebCamExternes;
    private Integer nbTableauExternes;
    private Integer nbPieuvreExternes;
    private Integer nbEcranExternes;

    @ManyToOne
    @JoinColumn(name = "id_salle")
    private Salle salle;

}
