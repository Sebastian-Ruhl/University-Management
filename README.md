# University Management — SYSACAD

Sistema de Gestión Académica universitaria. API RESTful desarrollada con **Spring Boot** como Trabajo Práctico Final de **Programación III** (Comisión 4, UTN).

---

## Tabla de Contenidos

- [Descripción del Proyecto](#descripción-del-proyecto)
- [Arquitectura](#arquitectura)
- [Stack Tecnológico](#stack-tecnológico)
- [Modelo de Datos](#modelo-de-datos)
- [Endpoints de la API](#endpoints-de-la-api)
- [Seguridad](#seguridad)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Cómo Ejecutar el Proyecto](#cómo-ejecutar-el-proyecto)
- [Integrantes](#integrantes)

---

## Descripción del Proyecto

Sistema que permite gestionar integralmente las operaciones académicas de una universidad a través de una API RESTful. Administra estudiantes, profesores, materias, comisiones, inscripciones, asistencias, exámenes y horarios.

### Funcionalidades principales

- **Gestión de personas**: estudiantes, profesores y personal administrativo con datos personales y académicos.
- **Gestión de carreras y materias**: ABM de planes de estudio, correlatividades y requisitos.
- **Gestión de comisiones**: apertura de cursadas por cuatrimestre, asignación de profesores y horarios.
- **Gestión de inscripciones**: inscripción de estudiantes a comisiones con validación de correlatividades.
- **Gestión de asistencias**: registro de asistencia por clase, control de condición académica.
- **Gestión de exámenes y notas**: carga de notas de parciales, finales y recuperatorios.
- **Autenticación y roles**: acceso segmentado por rol (ADMIN, STUDENT, PROFESSOR, STAFF).

---

## Arquitectura

```
Cliente (Postman / Frontend / Swagger UI)
       ↕  HTTP / JSON (JWT en header)
┌──────────────────────────────────┐
│       API REST (Spring Boot)     │
│                                  │
│  ┌───────────┐  ┌─────────────┐  │
│  │ Controller │→│  DTOs       │  │  Capa de presentación
│  └─────┬─────┘  └─────────────┘  │
│        ↓                         │
│  ┌───────────┐                   │
│  │  Service  │  Lógica de negocio│  Capa de negocio
│  └─────┬─────┘                   │
│        ↓                         │
│  ┌───────────┐  ┌─────────────┐  │
│  │ Repository│→│  JPA / Hbn  │  │  Capa de persistencia
│  └─────┬─────┘  └─────────────┘  │
└────────┼─────────────────────────┘
         ↓ JDBC
   ┌──────────┐
   │  MySQL   │
   └──────────┘
```

### Principios de diseño

- Separación estricta en capas (Controller → Service → Repository)
- DTOs de entrada y salida — nunca se exponen entidades JPA directamente
- Manejo centralizado de excepciones con `@RestControllerAdvice`
- Validaciones con `@Valid` aplicadas sobre los DTOs de entrada
- Documentación via Swagger / OpenAPI

---

## Stack Tecnológico

| Tecnología | Versión | Uso |
|---|---|---|
| Java | 26 | Lenguaje de programación |
| Spring Boot | 4.0.6 | Framework principal |
| Spring Web MVC | — | Controladores REST (`@RestController`) |
| Spring Data JPA | — | Persistencia y mapeo ORM |
| Spring Security | — | Autenticación y autorización |
| MySQL | 8.x | Base de datos relacional |
| JWT (jjwt) | — | Tokens de autenticación |
| Swagger / OpenAPI | springdoc-openapi | Documentación interactiva |
| Maven | — | Gestión de dependencias y build |
| Lombok | — | Reducción de boilerplate |
| JUnit 5 | — | Pruebas unitarias |

---

## Modelo de Datos

El sistema cuenta con **15 entidades** distribuidas en 5 módulos:

### Módulo de Personas
- **PersonEntity** — Entidad base con datos personales (nombre, documento, contacto)
- **StudentEntity** — Datos específicos del alumno (legajo, estado, condición académica)
- **ProfessorEntity** — Datos específicos del profesor (categoría, dedicación)
- **AdministrativeStaffEntity** — Datos del personal administrativo
- **UserAccountEntity** — Credenciales de acceso (email, contraseña, rol)

### Módulo Académico
- **DegreeEntity** — Carreras universitarias
- **SubjectEntity** — Materias dentro de una carrera
- **PrerequisiteEntity** — Correlatividades entre materias

### Módulo de Cursada
- **CourseSectionEntity** — Comisiones abiertas en un cuatrimestre
- **ScheduleEntity** — Horarios semanales de una comisión
- **ClassSessionEntity** — Clases individuales dictadas
- **ProfessorAssignmentEntity** — Asignación de profesores a comisiones

### Módulo de Alumnos
- **EnrollmentEntity** — Inscripción de un alumno a una comisión
- **AttendanceEntity** — Asistencia a clases
- **ExamGradeEntity** — Notas de exámenes

### Diagrama de relaciones

```
PersonEntity ───1:1─── StudentEntity
    │                    │
    ├──1:1─── ProfessorEntity
    │                    │
    ├──1:1─── AdministrativeStaffEntity
    │
    └──1:1─── UserAccountEntity

DegreeEntity ───1:N─── SubjectEntity
                          │
PrerequisiteEntity ──────┘

SubjectEntity ───1:N─── CourseSectionEntity ───1:N─── ScheduleEntity
                              │
                              ├──1:N─── ClassSessionEntity ───1:N─── AttendanceEntity
                              │
                              ├──1:N─── ProfessorAssignmentEntity
                              │               ↑
                              │     ProfessorEntity
                              │
                              └──1:N─── EnrollmentEntity ───1:N─── ExamGradeEntity
                                            ↑
                                      StudentEntity
```

---

## Endpoints de la API

### Auth (público)
| Método | Endpoint | Descripción |
|---|---|---|
| POST | `/api/auth/register` | Registrar nuevo usuario |
| POST | `/api/auth/login` | Iniciar sesión (devuelve JWT) |

### Personas
| Método | Endpoint | Roles |
|---|---|---|
| GET | `/api/persons` | ADMIN, STAFF |
| GET | `/api/persons/{id}` | ADMIN, STAFF |
| POST | `/api/persons` | ADMIN |
| PUT | `/api/persons/{id}` | ADMIN |
| DELETE | `/api/persons/{id}` | ADMIN |

### Estudiantes
| Método | Endpoint | Roles |
|---|---|---|
| GET | `/api/students` | ADMIN, STAFF |
| GET | `/api/students/{id}` | ADMIN, STUDENT |
| PUT | `/api/students/{id}` | ADMIN |
| GET | `/api/students/{id}/academic-history` | ADMIN, STUDENT |

### Profesores
| Método | Endpoint | Roles |
|---|---|---|
| GET | `/api/professors` | ADMIN, STAFF |
| GET | `/api/professors/{id}` | ADMIN, PROFESSOR |
| POST | `/api/professors/{id}/assignments` | ADMIN |

### Materias
| Método | Endpoint | Roles |
|---|---|---|
| GET | `/api/subjects` | Todos |
| GET | `/api/subjects/{id}` | Todos |
| POST | `/api/subjects` | ADMIN |
| PUT | `/api/subjects/{id}` | ADMIN |
| DELETE | `/api/subjects/{id}` | ADMIN |

### Comisiones
| Método | Endpoint | Roles |
|---|---|---|
| GET | `/api/sections` | Todos |
| GET | `/api/sections/{id}` | Todos |
| POST | `/api/sections` | ADMIN |
| PUT | `/api/sections/{id}` | ADMIN |
| PATCH | `/api/sections/{id}/status` | ADMIN |

### Inscripciones
| Método | Endpoint | Roles |
|---|---|---|
| GET | `/api/enrollments` | ADMIN, STAFF |
| POST | `/api/enrollments` | STUDENT, ADMIN |
| GET | `/api/enrollments/student/{studentId}` | STUDENT, ADMIN |
| DELETE | `/api/enrollments/{id}` | STUDENT, ADMIN |

### Asistencias
| Método | Endpoint | Roles |
|---|---|---|
| GET | `/api/attendances/session/{sessionId}` | PROFESSOR, ADMIN |
| POST | `/api/attendances` | PROFESSOR, ADMIN |
| GET | `/api/attendances/student/{studentId}` | STUDENT, ADMIN |

### Exámenes y Notas
| Método | Endpoint | Roles |
|---|---|---|
| GET | `/api/grades/student/{studentId}` | STUDENT, ADMIN |
| POST | `/api/grades` | PROFESSOR, ADMIN |
| PUT | `/api/grades/{id}` | PROFESSOR, ADMIN |

---

## Seguridad

- **JWT (JSON Web Token)**: generado en login con expiración de 24 horas
- **Roles**: ADMIN, STUDENT, PROFESSOR, STAFF
- **Protección de endpoints**: mediante `@PreAuthorize` en los controladores
- **Contraseñas**: encriptadas con BCrypt (Spring Security)
- **CORS**: configurable para permitir peticiones del frontend en desarrollo

### Endpoints públicos vs. privados

| Tipo | Ejemplos |
|---|---|
| Públicos | `POST /api/auth/register`, `POST /api/auth/login` |
| Requieren JWT | `GET /api/students`, `POST /api/enrollments`, etc. |
| Requieren rol específico | `POST /api/subjects` (solo ADMIN), `POST /api/grades` (PROFESSOR o ADMIN) |

---

## Estructura del Proyecto

```
University-Management/
├── pom.xml
├── README.md
├── Requisitos.md
├── .gitignore
├── src/
│   ├── main/
│   │   ├── java/Proyect/UniversityManagement/
│   │   │   ├── UniversityManagementApplication.java
│   │   │   ├── controller/
│   │   │   │   ├── AuthController.java
│   │   │   │   └── UserController.java
│   │   │   ├── service/
│   │   │   │   ├── StudentService.java
│   │   │   │   ├── ProfessorService.java
│   │   │   │   ├── SubjectService.java
│   │   │   │   ├── CourseSectionService.java
│   │   │   │   ├── EnrollmentService.java
│   │   │   │   ├── AttendanceService.java
│   │   │   │   ├── ExamGradeService.java
│   │   │   │   ├── ScheduleService.java
│   │   │   │   ├── ClassSessionService.java
│   │   │   │   ├── DegreeService.java
│   │   │   │   ├── PrerequisiteService.java
│   │   │   │   ├── ProfessorAssignmentService.java
│   │   │   │   ├── PersonService.java
│   │   │   │   ├── AdministrativeStaffService.java
│   │   │   │   └── UserAccountService.java
│   │   │   ├── repository/
│   │   │   │   ├── StudentRepository.java
│   │   │   │   ├── ProfessorRepository.java
│   │   │   │   ├── SubjectRepository.java
│   │   │   │   ├── CourseSectionRepository.java
│   │   │   │   ├── EnrollmentRepository.java
│   │   │   │   ├── AttendanceRepository.java
│   │   │   │   ├── ExamGradeRepository.java
│   │   │   │   ├── ScheduleRepository.java
│   │   │   │   ├── ClassSessionRepository.java
│   │   │   │   ├── DegreeRepository.java
│   │   │   │   ├── PrerequisiteRepository.java
│   │   │   │   ├── ProfessorAssignmentRepository.java
│   │   │   │   ├── PersonRepository.java
│   │   │   │   ├── AdministrativeStaffRepository.java
│   │   │   │   └── UserAccountRepository.java
│   │   │   ├── model/
│   │   │   │   ├── PersonEntity.java
│   │   │   │   ├── StudentEntity.java
│   │   │   │   ├── ProfessorEntity.java
│   │   │   │   ├── AdministrativeStaffEntity.java
│   │   │   │   ├── UserAccountEntity.java
│   │   │   │   ├── DegreeEntity.java
│   │   │   │   ├── SubjectEntity.java
│   │   │   │   ├── PrerequisiteEntity.java
│   │   │   │   ├── CourseSectionEntity.java
│   │   │   │   ├── ScheduleEntity.java
│   │   │   │   ├── ClassSessionEntity.java
│   │   │   │   ├── ProfessorAssignmentEntity.java
│   │   │   │   ├── EnrollmentEntity.java
│   │   │   │   ├── AttendanceEntity.java
│   │   │   │   └── ExamGradeEntity.java
│   │   │   ├── enums/
│   │   │   │   ├── AcademicCondition.java
│   │   │   │   ├── AccountStatus.java
│   │   │   │   ├── DayOfWeek.java
│   │   │   │   ├── DedicationType.java
│   │   │   │   ├── EnrollmentStatus.java
│   │   │   │   ├── ExamType.java
│   │   │   │   ├── Gender.java
│   │   │   │   ├── ProfessorCategory.java
│   │   │   │   ├── RequirementType.java
│   │   │   │   ├── SectionRole.java
│   │   │   │   ├── SectionStatus.java
│   │   │   │   ├── Shift.java
│   │   │   │   ├── StudentStatus.java
│   │   │   │   ├── SystemRol.java
│   │   │   │   └── TermType.java
│   │   │   ├── dto/
│   │   │   │   ├── request/
│   │   │   │   │   ├── CreatePersonDTO.java
│   │   │   │   │   ├── EnrollmentRequestDTO.java
│   │   │   │   │   ├── LoginRequestDTO.java
│   │   │   │   │   ├── OpenSectionDTO.java
│   │   │   │   │   └── SubmitGradeDTO.java
│   │   │   │   └── response/
│   │   │   │       ├── AcademicHistoryDTO.java
│   │   │   │       ├── OpenSectionResponseDTO.java
│   │   │   │       ├── PersonResponseDTO.java
│   │   │   │       ├── StudentProfileDTO.java
│   │   │   │       ├── SubjectDetailDTO.java
│   │   │   │       └── TokenResponseDTO.java
│   │   │   ├── mapper/
│   │   │   │   ├── StudentMapper.java
│   │   │   │   ├── SubjectMapper.java
│   │   │   │   └── EnrollmentMapper.java
│   │   │   └── exception/
│   │   │       ├── GlobalExceptionHandler.java
│   │   │       ├── ResourceNotFoundException.java
│   │   │       ├── BusinessException.java
│   │   │       └── DuplicateEntityException.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/Proyect/UniversityManagement/
│           └── RestaurantManagementApplicationTests.java
└── target/
```

---

## Cómo Ejecutar el Proyecto

### Prerrequisitos

- Java 26 instalado
- MySQL 8.x instalado y corriendo
- Maven (o usar el Maven Wrapper incluido: `mvnw.cmd`)

### Pasos

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/Sebastian-Ruhl/University-Management.git
   cd University-Management
   ```

2. Crear la base de datos en MySQL:
   ```sql
   CREATE DATABASE university_db;
   ```

3. Configurar las credenciales en `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/university_db?useSSL=false&serverTimezone=America/Argentina/Buenos_Aires&allowPublicKeyRetrieval=true
   spring.datasource.username=root
   spring.datasource.password=tu_contraseña
   ```

4. Ejecutar la aplicación:
   ```bash
   ./mvnw spring-boot:run
   ```

5. Acceder a Swagger UI (cuando esté configurado):
   ```
   http://localhost:8080/swagger-ui.html
   ```

---

## Integrantes

| Nombre |
|---|
| **Sebastian Ruhl** |
| **Francisco Macchiavello** |
| **Mariano Lumbreras** |
| **Santino Cataldo** |

---

## Licencia

Proyecto académico — Universidad Tecnológica Nacional
