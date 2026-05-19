package Proyect.UniversityManagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "administrativo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdministrativeStaffEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_persona", nullable = false)
    private PersonEntity persona;

    @Column(name = "legajo_admin", unique = true, nullable = false)
    private String legajoAdmin;

    @Column(nullable = false)
    private String area;

    @Column(nullable = false)
    private String cargo;

    @Column(name = "fecha_alta")
    private LocalDate fechaAlta;

    @Column(nullable = false)
    private Boolean activo;
}
