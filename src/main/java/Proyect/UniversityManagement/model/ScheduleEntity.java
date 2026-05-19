package Proyect.UniversityManagement.model;

import Proyect.UniversityManagement.enums.DayOfWeek;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "horario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "dia_semana", nullable = false)
    private DayOfWeek diaSemana;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    @Column(name = "nombre_turno")
    private String nombreTurno;

    @ManyToMany(mappedBy = "horarios")
    private List<CourseSectionEntity> comisiones = new ArrayList<>();
}
