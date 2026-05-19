package Proyect.UniversityManagement.model;

import Proyect.UniversityManagement.enums.DedicationType;
import Proyect.UniversityManagement.enums.ProfessorCategory;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "docente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_persona", nullable = false)
    private PersonEntity persona;

    @Column(name = "numero_docente", unique = true, nullable = false)
    private String numeroDocente;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProfessorCategory categoria;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DedicationType dedicacion;

    @Column(name = "fecha_alta_docente")
    private LocalDate fechaAltaDocente;

    @Column(nullable = false)
    private Boolean activo;

    @OneToMany(mappedBy = "docente")
    private List<ProfessorAssignmentEntity> asignaciones = new ArrayList<>();
}
