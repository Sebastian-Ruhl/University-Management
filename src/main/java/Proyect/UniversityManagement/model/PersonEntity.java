package Proyect.UniversityManagement.model;

import Proyect.UniversityManagement.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "persona")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String dni;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "email_personal", unique = true)
    private String emailPersonal;

    private String telefono;

    private String domicilio;

    @Enumerated(EnumType.STRING)
    private Gender sexo;

    private String nacionalidad;

    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL)
    private UserAccountEntity cuentaUsuario;

    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL)
    private StudentEntity alumno;

    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL)
    private ProfessorEntity docente;

    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL)
    private AdministrativeStaffEntity administrativo;
}
