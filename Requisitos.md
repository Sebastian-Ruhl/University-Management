# Requisitos Funcionales — University Management (SYSACAD)

Este documento detalla los Requisitos Funcionales (RF) de la plataforma de Gestión Académica Universitaria (**SYSACAD**). El sistema está diseñado bajo una arquitectura de API RESTful con control de acceso basado en roles para Alumnos, Docentes, Administrativos y Administradores de Sistema.

---

## 🔐 Módulo de Autenticación y Cuentas de Usuario

**RF01** — El sistema debe permitir el registro de nuevos usuarios en la plataforma.

**RF02** — El sistema debe permitir a los usuarios iniciar sesión utilizando sus credenciales (`emailLogin` y contraseña).

**RF03** — El sistema debe generar y retornar un token de acceso seguro (JWT) válido tras un inicio de sesión exitoso.

**RF04** — El sistema debe restringir el acceso a los recursos y endpoints basándose en los roles del sistema: `ALUMNO`, `DOCENTE`, `ADMINISTRATIVO`, `SYSADMIN`.

**RF05** — El sistema debe gestionar el ciclo de vida y estado de las cuentas de usuario (`ACTIVA`, `BLOQUEADA`, `INACTIVA`).

---

## 👤 Módulo de Personas (Gestión de Identidades)

**RF06** — El sistema debe permitir al administrador realizar el ABM (Alta, Baja, Modificación) de personas físicas.

**RF07** — El sistema debe garantizar que el DNI y el correo electrónico personal de una persona sean únicos en todo el sistema.

**RF08** — El sistema debe permitir listar el total de personas físicas registradas para fines de administración.

**RF09** — El sistema debe permitir la consulta detallada de los datos personales de una persona por medio de su ID único.

---

## 🎓 Módulo de Alumnos (Gestión Académica)

**RF10** — El sistema debe permitir la asignación de un perfil de alumno (`StudentEntity`) a una persona física previamente registrada.

**RF11** — El sistema debe generar y asignar un número de Legajo único e inmutable para cada alumno al momento de crear su perfil.

**RF12** — El sistema debe registrar la fecha de ingreso a la universidad para cada alumno.

**RF13** — El sistema debe permitir gestionar y actualizar el estado académico del alumno (`ACTIVO`, `EGRESADO`, `BAJA_TEMPORARIA`, `BAJA_DEFINITIVA`).

**RF14** — El sistema debe permitir al alumno y a los administradores consultar el historial académico (materias cursadas, calificaciones y promedio general).

---

## 👨‍🏫 Módulo de Docentes (Gestión de Profesores)

**RF15** — El sistema debe permitir la asignación de un perfil de docente (`ProfessorEntity`) a una persona física previamente registrada.

**RF16** — El sistema debe generar y asignar un número de docente único para cada profesor.

**RF17** — El sistema debe permitir categorizar al docente según su escalafón universitario (`AYUDANTE_ALUMNO`, `AYUDANTE_DIPLOMADO`, `JTP`, `ADJUNTO`, `ASOCIADO`, `TITULAR`).

**RF18** — El sistema debe permitir cambiar el estado de actividad de un docente (activo/inactivo) dentro de la institución.

---

## 📚 Módulo de Materias y Correlatividades

**RF19** — El sistema debe permitir al administrador realizar el ABM (Alta, Baja, Modificación) de materias curriculares.

**RF20** — El sistema debe almacenar para cada materia su código único, nombre descriptivo, año correspondiente de la carrera, cuatrimestre de dictado y la carga horaria semanal.

**RF21** — El sistema debe permitir al administrador definir reglas de correlatividades (prerrequisitos) entre materias curriculares.

**RF22** — El sistema debe soportar dos tipos de requisitos para las correlativas: que la materia previa deba estar **APROBADA** o simplemente **REGULARIZADA**.

**RF23** — El sistema debe permitir listar todas las materias y buscar una materia por su identificador.

---

## 🏫 Módulo de Comisiones (Oferta Académica)

**RF24** — El sistema debe permitir al administrador realizar la apertura de comisiones para el dictado de materias en un año lectivo y cuatrimestre determinados.

**RF25** — El sistema debe registrar para cada comisión su nombre distintivo, turno asignado (`MAÑANA`, `TARDE`, `NOCHE`) y cupo máximo de alumnos permitidos.

**RF26** — El sistema debe gestionar el estado del ciclo de vida de cada comisión (`PLANIFICADA`, `ABIERTA_INSCRIPCION`, `EN_CURSO`, `CERRADA`).

**RF27** — El sistema debe permitir la asignación de uno o más docentes al dictado de una comisión.

**RF28** — El sistema debe registrar el rol del docente en dicha comisión (`TITULAR`, `AYUDANTE_PRIMERA`, `AYUDANTE_SEGUNDA`, `JTP`) junto con la fecha en que se realizó la asignación.

---

## 📝 Módulo de Inscripciones (Cursadas)

**RF29** — El sistema debe permitir a los alumnos inscribirse a comisiones habilitadas y abiertas para la inscripción (`ABIERTA_INSCRIPCION`).

**RF30** — El sistema debe validar que el alumno cumpla de manera estricta con todas las correlativas requeridas (aprobadas o regularizadas) antes de confirmar una inscripción.

**RF31** — El sistema debe validar que la comisión disponga de cupo libre antes de autorizar la inscripción del alumno.

**RF32** — El sistema debe registrar la fecha y hora exacta en la que se efectúa la inscripción del alumno.

**RF33** — El sistema debe inicializar de forma automática el estado de la cursada del alumno como `CURSANDO` al inscribirse.

**RF34** — El sistema debe permitir al alumno o al administrador dar de baja (desinscribirse) una cursada activa.

**RF35** — El sistema debe permitir al administrador cambiar el estado de la cursada a `REGULAR`, `LIBRE`, `APROBADA`, `DESAPROBADA` o `ABANDONO`, y registrar la nota final de cursada si correspondiera.

---

## 🏆 Módulo de Exámenes y Notas (Calificaciones)

**RF36** — El sistema debe permitir a los docentes o administradores registrar las calificaciones de las evaluaciones rendidas por los alumnos en una materia.

**RF37** — El sistema debe registrar para cada calificación: el alumno evaluado, la materia, el docente titular que firma el acta, la fecha del examen, el tipo de evaluación y la nota numérica obtenida.

**RF38** — El sistema debe admitir diversos tipos de calificaciones/exámenes: `PARCIAL_1`, `PARCIAL_2`, `RECUPERATORIO_1`, `RECUPERATORIO_2`, `FLOTANTE`, `FINAL`, `EQUIVALENCIA` o `NIVELATORIO`.

**RF39** — El sistema debe permitir a los alumnos consultar de forma privada todas sus calificaciones registradas a lo largo de su carrera.

**RF40** — El sistema debe permitir a los docentes o administradores editar y actualizar las calificaciones registradas en caso de error administrativo.
