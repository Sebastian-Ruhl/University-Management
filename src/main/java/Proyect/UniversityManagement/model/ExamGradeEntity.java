package Proyect.UniversityManagement.model;

import Proyect.UniversityManagement.enums.ExamType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "nota_examen")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExamGradeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_alumno", nullable = false)
    private StudentEntity alumno;

    @ManyToOne
    @JoinColumn(name = "id_materia", nullable = false)
    private SubjectEntity materia;

    @ManyToOne
    @JoinColumn(name = "id_docente_titular")
    private ProfessorEntity docenteTitular;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_examen", nullable = false)
    private ExamType tipoExamen;

    @Column(name = "valor_numerico")
    private Double valorNumerico;

    @Column(name = "fecha_examen")
    private LocalDate fechaExamen;
}
