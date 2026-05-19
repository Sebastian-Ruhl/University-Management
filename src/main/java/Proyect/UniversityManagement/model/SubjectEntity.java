package Proyect.UniversityManagement.model;

import Proyect.UniversityManagement.enums.TermType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "materia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_carrera", nullable = false)
    private DegreeEntity carrera;

    @Column(nullable = false)
    private String codigo;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Integer anio;

    @Column(nullable = false)
    private Integer cuatrimestre;

    @Column(name = "carga_horaria_semanal")
    private Integer cargaHorariaSemanal;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_dictado", nullable = false)
    private TermType tipoDictado;

    @Column(nullable = false)
    private Boolean optativa;

    @OneToMany(mappedBy = "materia")
    private List<CourseSectionEntity> comisiones = new ArrayList<>();

    @OneToMany(mappedBy = "materia")
    private List<PrerequisiteEntity> correlatividades = new ArrayList<>();

    @OneToMany(mappedBy = "materiaCorrelativa")
    private List<PrerequisiteEntity> requeridaPor = new ArrayList<>();

    @OneToMany(mappedBy = "materia")
    private List<ExamGradeEntity> notasExamen = new ArrayList<>();
}
