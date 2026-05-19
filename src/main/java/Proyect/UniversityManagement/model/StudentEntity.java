package Proyect.UniversityManagement.model;

import Proyect.UniversityManagement.enums.StudentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "alumno")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_persona", unique = true, nullable = false)
    private PersonEntity persona;

    @Column(unique = true, nullable = false)
    private String legajo;

    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_alumno", nullable = false)
    private StudentStatus estadoAlumno;

    @OneToMany(mappedBy = "alumno")
    private List<EnrollmentEntity> inscripciones = new ArrayList<>();

    @OneToMany(mappedBy = "alumno")
    private List<ExamGradeEntity> notasExamen = new ArrayList<>();
}
