package Proyect.UniversityManagement.model;

import Proyect.UniversityManagement.enums.SectionRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "asignacion_docente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorAssignmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_docente", nullable = false)
    private ProfessorEntity docente;

    @ManyToOne
    @JoinColumn(name = "id_comision", nullable = false)
    private CourseSectionEntity comision;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol_en_comision", nullable = false)
    private SectionRole rolEnComision;

    @Column(name = "fecha_asignacion")
    private LocalDate fechaAsignacion;
}
