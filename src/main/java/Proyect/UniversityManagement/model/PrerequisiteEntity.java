package Proyect.UniversityManagement.model;

import Proyect.UniversityManagement.enums.RequirementType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "correlatividad")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrerequisiteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_materia", nullable = false)
    private SubjectEntity materia;

    @ManyToOne
    @JoinColumn(name = "id_materia_correlativa", nullable = false)
    private SubjectEntity materiaCorrelativa;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_requisito", nullable = false)
    private RequirementType tipoRequisito;
}
