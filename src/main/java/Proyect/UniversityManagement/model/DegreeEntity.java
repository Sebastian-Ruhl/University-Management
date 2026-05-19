package Proyect.UniversityManagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carrera")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DegreeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String codigo;

    @Column(nullable = false)
    private String nombre;

    @Column(name = "plan_estudio")
    private String planEstudio;

    private String resolucion;

    @Column(name = "duracion_anios")
    private Integer duracionAnios;

    @Column(nullable = false)
    private Boolean activa;

    @OneToMany(mappedBy = "carrera")
    private List<SubjectEntity> materias = new ArrayList<>();
}
