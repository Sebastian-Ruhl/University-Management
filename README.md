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

Sistema que permite gestionar las operaciones académicas de una universidad a través de una API RESTful. Administra personas, alumnos, docentes, materias, comisiones, inscripciones, exámenes y correlatividades.

### Funcionalidades principales

- **Gestión de personas**: alumnos y docentes con datos personales y académicos.
- **Gestión de materias**: ABM de materias, correlatividades y requisitos.
- **Gestión de comisiones**: apertura de cursadas por cuatrimestre, asignación de docentes.
- **Gestión de inscripciones**: inscripción de alumnos a comisiones con validación de correlatividades.
- **Gestión de exámenes y notas**: carga de notas de parciales, finales y recuperatorios.
- **Autenticación y roles**: acceso segmentado por rol (ALUMNO, DOCENTE, ADMINISTRATIVO, SYSADMIN).

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

El sistema cuenta con **10 entidades** distribuidas en 4 módulos:

### Módulo de Personas
- **PersonEntity** — Entidad base con datos personales (dni, nombre, apellido, email, teléfono)
- **StudentEntity** — Perfil de alumno (legajo, fecha de ingreso, estado)
- **ProfessorEntity** — Perfil de docente (número de docente, categoría)
- **UserAccountEntity** — Credenciales de acceso (email de login, contraseña, rol, estado)

### Módulo Académico
- **SubjectEntity** — Materias (código, nombre, año, cuatrimestre, carga horaria)
- **PrerequisiteEntity** — Correlatividades entre materias (tipo de requisito)

### Módulo de Cursada
- **CourseSectionEntity** — Comisiones abiertas en un cuatrimestre (turno, cupo, estado)
- **ProfessorAssignmentEntity** — Asignación de docentes a comisiones (rol, fecha)

### Módulo de Alumnos
- **EnrollmentEntity** — Inscripción de un alumno a una comisión (estado, nota final)
- **ExamGradeEntity** — Notas de exámenes (tipo, valor numérico, fecha)

### Diagrama de relaciones

```
PersonEntity ───1:1─── UserAccountEntity
    │
    ├──1:1─── StudentEntity
    │              │
    │              ├──1:N─── EnrollmentEntity ───1:N─── CourseSectionEntity
    │              │                                        │
    │              │                              ProfessorAssignmentEntity
    │              │                                        ↑
    │              │                                  ProfessorEntity
    │              │
    │              └──1:N─── ExamGradeEntity ───N:1─── SubjectEntity
    │
    └──1:1─── ProfessorEntity
                   │
                   └──1:N─── ProfessorAssignmentEntity

SubjectEntity ───1:N─── PrerequisiteEntity (materia)
SubjectEntity ───1:N─── PrerequisiteEntity (materia_correlativa)
SubjectEntity ───1:N─── CourseSectionEntity
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

### Alumnos
| Método | Endpoint | Roles |
|---|---|---|
| GET | `/api/students` | ADMIN, STAFF |
| GET | `/api/students/{id}` | ADMIN, ALUMNO |
| PUT | `/api/students/{id}` | ADMIN |
| GET | `/api/students/{id}/academic-history` | ADMIN, ALUMNO |

### Docentes
| Método | Endpoint | Roles |
|---|---|---|
| GET | `/api/professors` | ADMIN, STAFF |
| GET | `/api/professors/{id}` | ADMIN, DOCENTE |
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
| POST | `/api/enrollments` | ALUMNO, ADMIN |
| GET | `/api/enrollments/student/{studentId}` | ALUMNO, ADMIN |
| DELETE | `/api/enrollments/{id}` | ALUMNO, ADMIN |

### Exámenes y Notas
| Método | Endpoint | Roles |
|---|---|---|
| GET | `/api/grades/student/{studentId}` | ALUMNO, ADMIN |
| POST | `/api/grades` | DOCENTE, ADMIN |
| PUT | `/api/grades/{id}` | DOCENTE, ADMIN |

---

## Seguridad

- **JWT (JSON Web Token)**: generado en login con expiración de 24 horas
- **Roles**: ALUMNO, DOCENTE, ADMINISTRATIVO, SYSADMIN
- **Protección de endpoints**: mediante `@PreAuthorize` en los controladores
- **Contraseñas**: encriptadas con BCrypt (Spring Security)
- **CORS**: configurable para permitir peticiones del frontend en desarrollo

### Endpoints públicos vs. privados

| Tipo | Ejemplos |
|---|---|
| Públicos | `POST /api/auth/register`, `POST /api/auth/login` |
| Requieren JWT | `GET /api/students`, `POST /api/enrollments`, etc. |
| Requieren rol específico | `POST /api/subjects` (solo ADMIN), `POST /api/grades` (DOCENTE o ADMIN) |

---

## Estructura del Proyecto

```
University-Management/
├── pom.xml
├── README.md
├── Requisitos.md
├── sysacad_modelo_v2.html
├── .gitignore
├── src/
│   ├── main/
│   │   ├── java/Proyect/UniversityManagement/
│   │   │   ├── UniversityManagementApplication.java
│   │   │   ├── controller/
│   │   │   │   ├── AuthController.java
│   │   │   │   └── UserController.java
│   │   │   ├── service/
│   │   │   │   ├── PersonService.java
│   │   │   │   ├── UserAccountService.java
│   │   │   │   ├── StudentService.java
│   │   │   │   ├── ProfessorService.java
│   │   │   │   ├── SubjectService.java
│   │   │   │   ├── PrerequisiteService.java
│   │   │   │   ├── CourseSectionService.java
│   │   │   │   ├── ProfessorAssignmentService.java
│   │   │   │   ├── EnrollmentService.java
│   │   │   │   └── ExamGradeService.java
│   │   │   ├── repository/
│   │   │   │   ├── PersonRepository.java
│   │   │   │   ├── UserAccountRepository.java
│   │   │   │   ├── StudentRepository.java
│   │   │   │   ├── ProfessorRepository.java
│   │   │   │   ├── SubjectRepository.java
│   │   │   │   ├── PrerequisiteRepository.java
│   │   │   │   ├── CourseSectionRepository.java
│   │   │   │   ├── ProfessorAssignmentRepository.java
│   │   │   │   ├── EnrollmentRepository.java
│   │   │   │   └── ExamGradeRepository.java
│   │   │   ├── model/
│   │   │   │   ├── PersonEntity.java
│   │   │   │   ├── UserAccountEntity.java
│   │   │   │   ├── StudentEntity.java
│   │   │   │   ├── ProfessorEntity.java
│   │   │   │   ├── SubjectEntity.java
│   │   │   │   ├── PrerequisiteEntity.java
│   │   │   │   ├── CourseSectionEntity.java
│   │   │   │   ├── ProfessorAssignmentEntity.java
│   │   │   │   ├── EnrollmentEntity.java
│   │   │   │   └── ExamGradeEntity.java
│   │   │   ├── enums/
│   │   │   │   ├── AccountStatus.java
│   │   │   │   ├── EnrollmentStatus.java
│   │   │   │   ├── ExamType.java
│   │   │   │   ├── ProfessorCategory.java
│   │   │   │   ├── RequirementType.java
│   │   │   │   ├── SectionRole.java
│   │   │   │   ├── SectionStatus.java
│   │   │   │   ├── Shift.java
│   │   │   │   ├── StudentStatus.java
│   │   │   │   └── SystemRol.java
│   │   │   ├── dto/
│   │   │   │   ├── request/
│   │   │   │   │   ├── AssignProfessorDTO.java
│   │   │   │   │   ├── CreatePersonDTO.java
│   │   │   │   │   ├── CreateSubjectDTO.java
│   │   │   │   │   ├── EnrollmentRequestDTO.java
│   │   │   │   │   ├── LoginRequestDTO.java
│   │   │   │   │   ├── OpenSectionDTO.java
│   │   │   │   │   ├── RegisterRequestDTO.java
│   │   │   │   │   └── SubmitGradeDTO.java
│   │   │   │   └── response/
│   │   │   │       ├── AcademicHistoryDTO.java
│   │   │   │       ├── EnrollmentResponseDTO.java
│   │   │   │       ├── GradeResponseDTO.java
│   │   │   │       ├── OpenSectionResponseDTO.java
│   │   │   │       ├── PersonResponseDTO.java
│   │   │   │       ├── ProfessorResponseDTO.java
│   │   │   │       ├── StudentProfileDTO.java
│   │   │   │       ├── SubjectDetailDTO.java
│   │   │   │       └── TokenResponseDTO.java
│   │   │   ├── mapper/
│   │   │   │   ├── EnrollmentMapper.java
│   │   │   │   ├── StudentMapper.java
│   │   │   │   └── SubjectMapper.java
│   │   │   └── exception/
│   │   │       ├── BusinessException.java
│   │   │       ├── DuplicateEntityException.java
│   │   │       ├── GlobalExceptionHandler.java
│   │   │       └── ResourceNotFoundException.java
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
