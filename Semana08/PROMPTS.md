# Prompts utilizados (Fase 2)

## Mejora 1: Descuento por volumen
**Texto enviado a Gemini:** "Tengo una app Kotlin con Jetpack Compose, Material 3, MVVM, Room y un LavaRepository (interface). Implementa un descuento del 10% automático cuando un servicio por kilogramo (KG) supere los 5 kg. Muestra el descuento en el resumen del pedido y guárdalo en la entidad Pedido."
**Qué generó la IA:** Agregó el campo `descuento` en la entidad `Pedido`, actualizó la transacción en `LavaRepositoryRoom` para calcular `descuentoCalculado` y `totalNeto`, expuso los valores en `NuevoPedidoViewModel` y actualizó la interfaz gráfica en `NuevoPedidoScreen`, `DetallePedidoScreen` y `GestionPedidoScreen`.
**Qué tuve que corregir yo:** Ajustar la versión de la base de datos Room a v2 con `fallbackToDestructiveMigration(true)` y asegurar el correcto formato visual de la moneda.
**¿Respeta MVVM?:** Sí, la regla de negocio del descuento reside en el Repository y ViewModel, mientras la View se limita a observar los StateFlows.

## Mejora 2: Calificación del pedido
**Texto enviado a Gemini:** "Agrega una calificación de 1 a 5 estrellas para los pedidos en estado ENTREGADO. El estudiante califica desde el detalle del pedido y el panel del administrador muestra el promedio de satisfacción general."
**Qué generó la IA:** Agregó la columna `calificacion` en `Pedido`, las consultas de actualización y cálculo `AVG()` en `PedidoDao`, las funciones en `LavaRepository`, el StateFlow `promedioCalificacion` en `PanelViewModel` y el componente interactivo de estrellas en `DetallePedidoScreen`.
**Qué tuve que corregir yo:** Incrementar la versión de la base de datos a v3 y dar formato a un decimal (`%.1f / 5.0`).
**¿Respeta MVVM?:** Sí, la interacción del usuario dispara llamadas al ViewModel y la consulta del promedio responde de forma reactiva con `Flow`.

## Mejora 3: Gráfico de gasto mensual
**Texto enviado a Gemini:** "Crea un gráfico de líneas en Canvas dentro del Perfil del estudiante que muestre el gasto acumulado por mes en lavandería obtenido desde Room."
**Qué generó la IA:** La consulta SQL en `PedidoDao` agrupada por mes (`SUBSTR(fechaRegistro, 6, 2)`), la transformación a `DatoGrafico` en `SesionViewModel`, el componente `GraficoLineas` dibujado con `Canvas` y la integración en `PerfilScreen`.
**Qué tuve que corregir yo:** Mapear los números de mes ('01', '02', etc.) a nombres legibles en español ('Ene', 'Feb', etc.) y asegurar datos por defecto cuando no hay pedidos pagados.
**¿Respeta MVVM?:** Sí, la vista dibuja la gráfica basándose en la lista de `DatoGrafico` expuesta por `StateFlow` sin tocar directamente la capa de datos.

## Reflexión
- **¿Qué mejoró la IA y qué no?** La IA agilizó enormemente la maquetación de componentes Compose, consultas Room y algoritmos para gráficos en Canvas.
- **¿Qué error cometió?** No incrementó la versión del esquema de Room (`@Database`) al modificar campos de la entidad `Pedido`.
- **¿Qué aprendí sobre MVVM, Room y reglas de negocio?** Aprendí a mantener limpia la arquitectura desacoplando las reglas de cálculo en el ViewModel y exponiendo estados mediante StateFlow para una UI reactiva e intuitiva.
