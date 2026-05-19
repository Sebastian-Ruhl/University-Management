package Proyect.UniversityManagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clase")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassSessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_comision", nullable = false)
    private CourseSectionEntity comision;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(name = "tema_dictado")
    private String temaDictado;

    @OneToMany(mappedBy = "clase")
    private List<AttendanceEntity> asistencias = new ArrayList<>();
}
