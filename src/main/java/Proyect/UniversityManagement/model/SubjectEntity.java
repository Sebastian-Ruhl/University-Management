package Proyect.UniversityManagement.model;

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

    @Column(unique = true, nullable = false)
    private String codigo;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Integer anio;

    @Column(nullable = false)
    private Integer cuatrimestre;

    @Column(name = "carga_horaria_semanal")
    private Integer cargaHorariaSemanal;

    @OneToMany(mappedBy = "materia")
    private List<CourseSectionEntity> comisiones = new ArrayList<>();

    @OneToMany(mappedBy = "materia")
    private List<PrerequisiteEntity> correlatividades = new ArrayList<>();

    @OneToMany(mappedBy = "materiaCorrelativa")
    private List<PrerequisiteEntity> requeridaPor = new ArrayList<>();

    @OneToMany(mappedBy = "materia")
    private List<ExamGradeEntity> notasExamen = new ArrayList<>();
}
