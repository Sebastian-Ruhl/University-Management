package Proyect.UniversityManagement.model;

import Proyect.UniversityManagement.enums.AcademicCondition;
import Proyect.UniversityManagement.enums.EnrollmentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inscripcion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_alumno", nullable = false)
    private StudentEntity alumno;

    @ManyToOne
    @JoinColumn(name = "id_comision", nullable = false)
    private CourseSectionEntity comision;

    @Column(name = "fecha_inscripcion")
    private LocalDateTime fechaInscripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_cursada", nullable = false)
    private EnrollmentStatus estadoCursada;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AcademicCondition condicion;

    @Column(name = "porcentaje_asistencia")
    private Double porcentajeAsistencia;

    @Column(name = "nota_final_cursada")
    private Double notaFinalCursada;
}
