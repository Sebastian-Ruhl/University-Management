package Proyect.UniversityManagement.model;

import Proyect.UniversityManagement.enums.AccountStatus;
import Proyect.UniversityManagement.enums.SystemRol;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cuenta_usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_persona", unique = true, nullable = false)
    private PersonEntity persona;

    @Column(unique = true, nullable = false)
    private String emailLogin;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol_sistema", nullable = false)
    private SystemRol rolSistema;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus estado;
}
