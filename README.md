# SAAS-Restaurant-Management

Sistema SaaS multi-tenant para la gestión integral de restaurantes. API RESTful desarrollada con **Spring Boot** como Trabajo Práctico Final de **Programación III** (Comisión 4, UTN).

---

## Tabla de Contenidos

- [Descripción del Proyecto](#descripción-del-proyecto)
- [Arquitectura](#arquitectura)
- [Stack Tecnológico](#stack-tecnológico)
- [Modelo de Datos](#modelo-de-datos)
- [Planes de Suscripción](#planes-de-suscripción)
- [Endpoints de la API](#endpoints-de-la-api)
- [Seguridad](#seguridad)
- [Integración con Mercado Pago](#integración-con-mercado-pago)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Cómo Ejecutar el Proyecto](#cómo-ejecutar-el-proyecto)
- [Integrantes](#integrantes)

---

## Descripción del Proyecto

Sistema SaaS que permite a múltiples restaurantes registrarse y gestionar sus operaciones diarias a través de una API RESTful. Cada restaurante opera sus datos de forma aislada (multi-tenant) y accede a funcionalidades según el plan de suscripción que contrate.

### Funcionalidades principales

- **Gestión de usuarios y roles**: administrador, mozo, cocinero y cajero, cada uno con permisos específicos.
- **Gestión del menú**: categorías y productos con control de disponibilidad.
- **Gestión de mesas**: control de estado y límites según el plan contratado.
- **Gestión de pedidos**: creación, cambio de estados (abierto, en preparación, listo, entregado, pagado) y cálculo automático del total.
- **Gestión de reservas**: clientes pueden reservar mesas sin registrarse.
- **Suscripciones y pagos**: planes con diferentes límites y features, integración con Mercado Pago.
- **Reportes**: ventas diarias, semanales, mensuales, productos más vendidos y más.

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
- Documentación completa via Swagger / OpenAPI

---

## Stack Tecnológico

| Tecnología | Versión | Uso |
|---|---|---|
| Java | 17+ | Lenguaje de programación |
| Spring Boot | 3.x | Framework principal |
| Spring Web | — | Controladores REST (`@RestController`) |
| Spring Data JPA | — | Persistencia y mapeo ORM |
| Spring Security | — | Autenticación y autorización |
| MySQL | 8.x | Base de datos relacional |
| JWT (jjwt) | 0.12.x | Tokens de autenticación |
| Swagger / OpenAPI | springdoc-openapi 2.x | Documentación interactiva |
| Mercado Pago SDK | SDK Java | Procesamiento de pagos |
| Maven | — | Gestión de dependencias y build |
| JUnit 5 | — | Pruebas unitarias (diferenciador) |

---

## Modelo de Datos

El sistema cuenta con **13 entidades** distribuidas en 6 módulos:

### Módulo de Suscripciones y Pagos
- **Plan** — Catálogo de planes con precio, límites y features
- **Suscripcion** — Vincula un restaurante con un plan (estados: pendiente, activa, vencida, cancelada)
- **Pago** — Registro de pagos con integración Mercado Pago
- **NotificacionWebhook** — Registro de webhooks entrantes de MP

### Módulo de Restaurants
- **Restaurant** — Entidad raíz multi-tenant con datos del negocio

### Módulo de Usuarios
- **Usuario** — Empleados con rol (ADMIN, MOZO, COCINERO, CAJERO)

### Módulo de Menú
- **Categoria** — Agrupación de productos (entradas, platos principales, etc.)
- **Producto** — Items del menú con precio y disponibilidad

### Módulo de Operaciones
- **Mesa** — Mesas con número, capacidad, ubicación y estado
- **Pedido** — Pedidos con estados y total automático
- **DetallePedido** — Items individuales dentro de un pedido

### Módulo de Reservas
- **Reserva** — Reservas de clientes con datos de contacto
- **ReservaMesa** — Tabla intermedia N:M entre reservas y mesas

### Diagrama de relaciones

```
Plan ───1:N─── Suscripcion ───1:N─── Pago ───1:N─── NotificacionWebhook
                    ↑
Restaurant ───1:N──┘
    │
    ├──1:N─── Usuario
    ├──1:N─── Categoria ───1:N─── Producto
    ├──1:N─── Mesa ──────────N:M────── Reserva
    │                              │
    ├──1:N─── Pedido              ReservaMesa
    │          │
    │          ├──1:N─── DetallePedido ───N:1─── Producto
    │
    └──1:N─── Reserva
```

---

## Planes de Suscripción

| Plan | Precio | Mesas | Usuarios | Funcionalidades |
|---|---|---|---|---|
| GRATIS | $0/mes | Hasta 5 | 2 | Menú, mesas, pedidos básicos |
| BASICO | $10/mes | Hasta 15 | 5 | + Reservas, reportes básicos |
| PREMIUM | $25/mes | Hasta 50 | 15 | + Reportes avanzados, todos los roles |
| ENTERPRISE | $50/mes | Ilimitado | Ilimitado | + Multi-sucursal |

Los límites se validan en la capa de servicio al crear/editar mesas y usuarios.

---

## Endpoints de la API

### Auth (público)
| Método | Endpoint | Descripción |
|---|---|---|
| POST | `/api/auth/register` | Registrar nuevo restaurante |
| POST | `/api/auth/login` | Iniciar sesión (devuelve JWT) |

### Planes
| Método | Endpoint | Roles |
|---|---|---|
| GET | `/api/planes` | Público |
| GET | `/api/planes/{id}` | Público |
| POST | `/api/planes` | ADMIN |
| PUT | `/api/planes/{id}` | ADMIN |
| DELETE | `/api/planes/{id}` | ADMIN |

### Suscripciones
| Método | Endpoint | Roles |
|---|---|---|
| GET | `/api/suscripciones` | ADMIN |
| POST | `/api/suscripciones` | ADMIN |
| GET | `/api/suscripciones/actual` | ADMIN |

### Pagos
| Método | Endpoint | Roles |
|---|---|---|
| POST | `/api/pagos/crear-preferencia` | ADMIN |
| POST | `/api/webhooks/mercadopago` | Público (webhook) |
| GET | `/api/pagos` | ADMIN |
| GET | `/api/pagos/{id}` | ADMIN |

### Restaurants
| Método | Endpoint | Roles |
|---|---|---|
| GET | `/api/restaurants` | ADMIN |
| PUT | `/api/restaurants/{id}` | ADMIN |
| GET | `/api/restaurants/{id}` | ADMIN |

### Usuarios
| Método | Endpoint | Roles |
|---|---|---|
| GET | `/api/usuarios` | ADMIN |
| POST | `/api/usuarios` | ADMIN |
| PUT | `/api/usuarios/{id}` | ADMIN |
| DELETE | `/api/usuarios/{id}` | ADMIN |

### Categorías
| Método | Endpoint | Roles |
|---|---|---|
| GET | `/api/categorias` | Todos |
| POST | `/api/categorias` | ADMIN |
| PUT | `/api/categorias/{id}` | ADMIN |
| DELETE | `/api/categorias/{id}` | ADMIN |

### Productos
| Método | Endpoint | Roles |
|---|---|---|
| GET | `/api/productos` | Todos |
| GET | `/api/productos/{id}` | Todos |
| POST | `/api/productos` | ADMIN |
| PUT | `/api/productos/{id}` | ADMIN |
| DELETE | `/api/productos/{id}` | ADMIN |
| GET | `/api/productos/categoria/{categoriaId}` | Todos |
| PUT | `/api/productos/{id}/disponibilidad` | ADMIN |

### Mesas
| Método | Endpoint | Roles |
|---|---|---|
| GET | `/api/mesas` | Todos |
| POST | `/api/mesas` | ADMIN |
| PUT | `/api/mesas/{id}` | ADMIN |
| DELETE | `/api/mesas/{id}` | ADMIN |
| PATCH | `/api/mesas/{id}/estado` | MOZO, ADMIN |

### Pedidos
| Método | Endpoint | Roles |
|---|---|---|
| GET | `/api/pedidos` | Todos |
| POST | `/api/pedidos` | MOZO, ADMIN |
| GET | `/api/pedidos/{id}` | Todos |
| PUT | `/api/pedidos/{id}/estado` | MOZO, ADMIN |
| GET | `/api/pedidos/activos` | MOZO, COCINERO |
| GET | `/api/pedidos/mozo/{usuarioId}` | ADMIN, MOZO |

### Reservas
| Método | Endpoint | Roles |
|---|---|---|
| GET | `/api/reservas` | ADMIN |
| POST | `/api/reservas` | Público (clientes) |
| PUT | `/api/reservas/{id}` | ADMIN |
| DELETE | `/api/reservas/{id}` | ADMIN |
| GET | `/api/reservas/disponibilidad` | Público |

### Reportes
| Método | Endpoint | Roles |
|---|---|---|
| GET | `/api/reportes/ventas-diarias` | ADMIN |
| GET | `/api/reportes/ventas-semanales` | ADMIN |
| GET | `/api/reportes/ventas-mensuales` | ADMIN |
| GET | `/api/reportes/productos-mas-vendidos` | ADMIN |
| GET | `/api/reportes/mesas-mas-ocupadas` | ADMIN |
| GET | `/api/reportes/reservas` | ADMIN |
| GET | `/api/reportes/ingresos-por-metodo` | ADMIN |
| GET | `/api/reportes/empleados-destacados` | ADMIN |

---

## Seguridad

- **JWT (JSON Web Token)**: generado en login con expiración de 24 horas (configurable)
- **Roles**: ADMIN, MOZO, COCINERO, CAJERO
- **Protección de endpoints**: mediante `@PreAuthorize` en los controladores
- **Contraseñas**: encriptadas con BCrypt (Spring Security)
- **CORS**: configurable para permitir peticiones del frontend en desarrollo

### Endpoints públicos vs. privados

| Tipo | Ejemplos |
|---|---|
| Públicos | `POST /api/auth/register`, `POST /api/auth/login`, `GET /api/planes`, `POST /api/reservas` |
| Requieren JWT | `GET /api/pedidos`, `POST /api/productos`, etc. |
| Requieren rol específico | `POST /api/usuarios` (solo ADMIN), `POST /api/pedidos` (MOZO o ADMIN) |

---

## Integración con Mercado Pago

Flujo de suscripción paga:

```
1. Admin selecciona un plan (ej: PREMIUM)
2. Backend crea Suscripcion (estado: PENDIENTE) + Pago (estado: PENDIENTE)
3. Backend genera una Preference en Mercado Pago con los datos del plan
4. Backend devuelve el preference_id y el init_point al frontend
5. Frontend redirige al checkout de Mercado Pago
6. Cliente completa el pago en el entorno de MP
7. MP envía un webhook a POST /api/webhooks/mercadopago
8. Backend procesa el webhook:
   a. Guarda la notificación en NotificacionWebhook
   b. Actualiza el Pago con payment_id y mp_status
   c. Si mp_status = "approved", la Suscripcion pasa a ACTIVA
9. Tarea programada (@Scheduled) verifica suscripciones próximas a vencer
   y las desactiva automáticamente
```

---

## Estructura del Proyecto

```
SAAS-Restaurant-Management/
├── pom.xml
├── README.md
├── Requisitos.md
├── .gitignore
├── src/
│   └── main/
│       ├── java/com/restaurant/
│       │   ├── RestaurantApplication.java
│       │   ├── config/
│       │   │   ├── SecurityConfig.java
│       │   │   ├── SwaggerConfig.java
│       │   │   └── CorsConfig.java
│       │   ├── auth/
│       │   │   ├── AuthController.java
│       │   │   ├── AuthService.java
│       │   │   ├── JwtUtil.java
│       │   │   ├── JwtFilter.java
│       │   │   └── dto/ (LoginRequest, RegisterRequest, TokenResponse)
│       │   ├── entity/
│       │   │   ├── Plan.java
│       │   │   ├── Suscripcion.java
│       │   │   ├── Pago.java
│       │   │   ├── NotificacionWebhook.java
│       │   │   ├── Restaurant.java
│       │   │   ├── Usuario.java
│       │   │   ├── Categoria.java
│       │   │   ├── Producto.java
│       │   │   ├── Mesa.java
│       │   │   ├── Pedido.java
│       │   │   ├── DetallePedido.java
│       │   │   ├── Reserva.java
│       │   │   └── ReservaMesa.java
│       │   ├── repository/
│       │   ├── service/
│       │   ├── controller/
│       │   ├── dto/
│       │   │   ├── request/
│       │   │   └── response/
│       │   ├── mapper/
│       │   ├── exception/
│       │   │   ├── GlobalExceptionHandler.java
│       │   │   ├── ResourceNotFoundException.java
│       │   │   ├── BusinessException.java
│       │   │   └── PlanLimitExceededException.java
│       │   └── scheduler/
│       │       └── SuscripcionScheduler.java
│       └── resources/
│           ├── application.properties
│           └── data.sql
└── target/
```

---

## Cómo Ejecutar el Proyecto

### Prerrequisitos

- Java 17+ instalado
- MySQL 8.x instalado y corriendo
- Maven (o usar el Maven Wrapper incluido)

### Pasos

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/Sebastian-Ruhl/SAAS-Restaurant-Management.git
   cd SAAS-Restaurant-Management
   ```

2. Crear la base de datos en MySQL:
   ```sql
   CREATE DATABASE saas_restaurant;
   ```

3. Configurar las credenciales en `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/saas_restaurant
   spring.datasource.username=root
   spring.datasource.password=tu_contraseña
   ```

4. Ejecutar la aplicación:
   ```bash
   ./mvnw spring-boot:run
   ```

5. Acceder a Swagger UI:
   ```
   http://localhost:8080/swagger-ui.html
   ```

---

## Integrantes

| Nombre | Rol en el proyecto |
|---|---|
| **Sebastian Ruhl** | |
| **Francisco Macchiavello** | |
| **Mariano Lumbreras** | |
| **Santino Cataldo** | |

---

## Licencia

Proyecto académico — Universidad Tecnológica Nacional
