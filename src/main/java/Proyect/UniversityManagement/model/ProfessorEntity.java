package Proyect.UniversityManagement.model;

import Proyect.UniversityManagement.enums.ProfessorCategory;
import jakarta.persistence.*;
import lombok.*;

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

    @Column(nullable = false)
    private Boolean activo;

    @OneToMany(mappedBy = "docente")
    private List<ProfessorAssignmentEntity> asignaciones = new ArrayList<>();
}
