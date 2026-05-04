# Requisitos Funcionales — SAAS-Restaurant-Management

---

## Módulo de Autenticación y Usuarios

**RF01** — El sistema debe permitir el registro de un nuevo restaurante con sus datos básicos.

**RF02** — El sistema debe permitir iniciar sesión con email y contraseña.

**RF03** — El sistema debe permitir al administrador crear empleados con un rol específico.

**RF04** — El sistema debe poder realizar un ABM de empleados del restaurante.

**RF05** — El sistema debe validar que no se supere el límite de usuarios según el plan contratado.

**RF06** — El sistema debe permitir filtrar empleados por rol.

---

## Módulo de Planes y Suscripciones

**RF07** — El sistema debe mostrar los planes de suscripción disponibles sin necesidad de autenticación.

**RF08** — El sistema debe permitir al administrador contratar o cambiar de plan.

**RF09** — El sistema debe gestionar el ciclo de vida de una suscripción (pendiente, activa, vencida o cancelada).

**RF10** — El sistema debe poder registrar pagos de suscripciones.

**RF11** — El sistema debe procesar las notificaciones enviadas por Mercado Pago.

**RF12** — El sistema debe desactivar automáticamente las suscripciones vencidas.

**RF13** — El sistema debe permitir al administrador ver el historial de suscripciones de su restaurante.

**RF14** — El sistema debe permitir filtrar suscripciones por estado.

**RF15** — El sistema debe permitir al administrador ver el historial de pagos de su restaurante.

**RF16** — El sistema debe permitir filtrar pagos por estado y rango de fechas.

---

## Módulo de Restaurants

**RF17** — El sistema debe permitir al administrador editar los datos de su restaurante.

**RF18** — El sistema debe aislar los datos de cada restaurante para que no se mezclen entre sí.

---

## Módulo de Menú

**RF19** — El sistema debe permitir al administrador crear, editar y eliminar categorías del menú.

**RF20** — El sistema debe permitir al administrador crear, editar y eliminar productos del menú.

**RF21** — El sistema debe permitir al administrador cambiar la disponibilidad de un producto.

**RF22** — El sistema debe listar los productos filtrados por categoría.

**RF23** — El sistema debe permitir buscar productos por nombre.

**RF24** — El sistema debe permitir filtrar productos por rango de precio.

**RF25** — El sistema debe permitir listar solo los productos disponibles.

---

## Módulo de Mesas

**RF26** — El sistema debe permitir al administrador crear, editar y eliminar mesas.

**RF27** — El sistema debe permitir cambiar el estado de una mesa.

**RF28** — El sistema debe validar que no se supere el límite de mesas según el plan contratado.

**RF29** — El sistema debe listar las mesas disponibles según capacidad y horario.

**RF30** — El sistema debe permitir filtrar mesas por ubicación.

**RF31** — El sistema debe permitir filtrar mesas por estado.

---

## Módulo de Pedidos

**RF32** — El sistema debe permitir al mozo crear un pedido asociado a una mesa.

**RF33** — El sistema debe permitir agregar y quitar productos de un pedido.

**RF34** — El sistema debe calcular el total del pedido automáticamente.

**RF35** — El sistema debe permitir cambiar el estado del pedido a lo largo de su preparación y servicio.

**RF36** — El sistema debe listar los pedidos activos del restaurante.

**RF37** — El sistema debe listar los pedidos realizados por un mozo en particular.

**RF38** — El sistema debe permitir filtrar pedidos por rango de fechas.

**RF39** — El sistema debe permitir filtrar pedidos por estado.

**RF40** — El sistema debe permitir listar los pedidos de una mesa específica.

**RF41** — El sistema debe permitir buscar un pedido por su número o ID.

---

## Módulo de Reservas

**RF42** — El sistema debe permitir a un cliente crear una reserva sin necesidad de registrarse.

**RF43** — El sistema debe permitir al administrador confirmar, cancelar o marcar como cumplida una reserva.

**RF44** — El sistema debe consultar la disponibilidad de mesas para una fecha y horario determinados.

**RF45** — El sistema debe asignar una o varias mesas a una misma reserva.

**RF46** — El sistema debe permitir filtrar reservas por fecha.

**RF47** — El sistema debe permitir filtrar reservas por estado.

**RF48** — El sistema debe permitir buscar reservas por nombre del cliente.

---

## Módulo de Reportes

**RF49** — El sistema debe generar un reporte de ventas diarias.

**RF50** — El sistema debe generar un reporte de ventas semanales.

**RF51** — El sistema debe generar un reporte de ventas mensuales.

**RF52** — El sistema debe listar los productos más vendidos en un período.

**RF53** — El sistema debe listar las mesas más ocupadas en un período.

**RF54** — El sistema debe generar un reporte de reservas en un período.

**RF55** — El sistema debe generar un reporte de ingresos por método de pago.

**RF56** — El sistema debe listar los empleados con más pedidos atendidos en un período.


