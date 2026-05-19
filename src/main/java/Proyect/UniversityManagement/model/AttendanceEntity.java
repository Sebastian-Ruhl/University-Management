package Proyect.UniversityManagement.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "asistencia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_clase", nullable = false)
    private ClassSessionEntity clase;

    @ManyToOne
    @JoinColumn(name = "id_alumno", nullable = false)
    private StudentEntity alumno;

    @Column(nullable = false)
    private Boolean presente;

    private String observaciones;
}
