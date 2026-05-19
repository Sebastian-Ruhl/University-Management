package Proyect.UniversityManagement.model;

import Proyect.UniversityManagement.enums.SectionStatus;
import Proyect.UniversityManagement.enums.Shift;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comision")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseSectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_materia", nullable = false)
    private SubjectEntity materia;

    @Column(nullable = false)
    private String nombre;

    @Column(name = "anio_lectivo", nullable = false)
    private Integer anioLectivo;

    @Column(nullable = false)
    private Integer cuatrimestre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Shift turno;

    @Column(name = "cupo_maximo")
    private Integer cupoMaximo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SectionStatus estado;

    @OneToMany(mappedBy = "comision")
    private List<EnrollmentEntity> inscripciones = new ArrayList<>();

    @OneToMany(mappedBy = "comision")
    private List<ProfessorAssignmentEntity> asignacionesDocente = new ArrayList<>();
}
