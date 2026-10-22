# LavaCampus 

Aplicación móvil para la gestión de lavandería universitaria en el campus. Construida con **Kotlin**, **Jetpack Compose + Material 3**, **Room Database**, **DataStore Preferences** y **Navigation Compose** bajo el patrón de arquitectura **MVVM**.

---

## Perfiles de Usuario

- **Estudiante:**
  - Solicita servicios de lavado (por Kg, prenda o unidad) y visualiza el total calculado congelando precios.
  - Sigue en tiempo real el avance de su ropa (`RECIBIDO` ➔ `EN_PROCESO` ➔ `LISTO` ➔ `ENTREGADO`).
  - Consulta el casillero asignado (`C-01` a `C-30`) cuando su pedido está `LISTO`.
  - Cancela pedidos en estado `RECIBIDO` y consulta tarifas/horarios de atención.
  
- **Administrador:**
  - Panel de control con métricas en tiempo real, ingresos de la semana y gráficos (`GraficoDona` por estado y `GraficoBarras` por servicio).
  - Gestiona pedidos: avanza estados, asigna casilleros disponibles y cobra antes de entregar.
  - Administra el catálogo de servicios (crear, editar, activar/desactivar) y consulta el directorio de estudiantes.

---

## 🏗️ Diagrama de Arquitectura MVVM + Repository

```
┌─────────────────────────────────────────────────────────┐
│                    VIEW (Compose UI)                    │
│   Eventos (clics, texto) ▲       │ collectAsState()     │
└──────────────────────────┼───────┼──────────────────────┘
                           │       │
┌──────────────────────────▼───────┴──────────────────────┐
│             VIEWMODEL (StateFlow + Reglas)              │
│   Llama métodos          ▲       │ Flow / StateFlow     │
└──────────────────────────┼───────┼──────────────────────┘
                           │       │
┌──────────────────────────▼───────┴──────────────────────┐
│               LavaRepository (Interface)                │
│   Implementado por:                                     │
│   LavaRepositoryRoom                                    │
└──────────────────────────┬──────────────────────────────┘
                           │
             ┌─────────────┴─────────────┐
             ▼                           ▼
┌───────────────────────────┐ ┌──────────────────────────┐
│      Room DAOs / SQLite   │ │    DataStore Preferences │
│      (lavacampus.db)      │ │        (ajustes)         │
└─────────────────────────–─┘ └──────────────────────────┘
```

---

## 📁 Estructura del Proyecto

```
com.ramirez.lavacampus
├── MainActivity.kt
├── LavaApp.kt
│
├── data/
│   ├── local/
│   │   ├── entity/ (Servicio, Usuario, Pedido, DetallePedido)
│   │   ├── dao/ (ServicioDao, UsuarioDao, PedidoDao, DetalleDao)
│   │   ├── relation/ (DetalleConServicio, PedidoConDetalle)
│   │   ├── LavaDatabase.kt
│   │   └── DatosIniciales.kt
│   ├── repository/
│   │   ├── LavaRepository.kt (Interface)
│   │   └── LavaRepositoryRoom.kt
│   └── prefs/
│       └── PreferenciasStore.kt
│
├── model/ (Rol, EstadoPedido, Unidad, LineaPedido, IngresoServicio, ClienteResumen, Casilleros, DatoGrafico)
│
├── viewmodel/ (LavaViewModelFactory, SesionViewModel, InicioViewModel, NuevoPedidoViewModel, MisPedidosViewModel, DetallePedidoViewModel, PanelViewModel, BandejaViewModel, GestionPedidoViewModel, ServiciosViewModel, ClientesViewModel)
│
└── ui/
    ├── theme/ (Color, Type, Theme)
    ├── components/ (AppTopBar, LavaButton, LavaTextField, EstadoPedidoChip, PedidoCard, StepperEstado, IconoServicio, SelectorCantidad, GraficoDona, GraficoBarras)
    ├── navigation/ (Rutas, NavLava)
    ├── estudiante/ (SplashScreen, LoginScreen, InicioScreen, NuevoPedidoScreen, DetallePedidoScreen, MisPedidosScreen, TarifasScreen, PerfilScreen)
    └── administrador/ (PanelScreen, BandejaScreen, GestionPedidoScreen, ServiciosScreen, FormularioServicioScreen, ClientesScreen, AjustesScreen)
```

---

## Preguntas de Reflexión 

1. **¿Por qué un pedido tiene detalles en otra tabla?**
   - *Respuesta:* Un pedido puede incluir múltiples servicios (ej. lavado por kg y planchado). Guardar cada línea como una fila separada en `detalles_pedido` evita duplicar información de cabecera y permite realizar consultas analíticas agrupadas con `GROUP BY` e `INNER JOIN`.
2. **¿Por qué guardar `precioUnitario` y `subtotal` en el detalle?**
   - *Respuesta:* Es una "foto" del costo en el instante de la compra. Si en el futuro la lavandería cambia los precios de sus servicios, los pedidos antiguos conservan lo que costaron en su momento sin alterar los ingresos históricos.
3. **¿Por qué usamos una interface para el Repositorio (`LavaRepository`)?**
   - *Respuesta:* Para desacoplar la interfaz de usuario y los ViewModels de la fuente de datos. Cuando la app se conecte a un Web Service o servicio en la nube, solo se creará una nueva implementación de la interface sin modificar ni un solo ViewModel ni pantalla Composable.

---

## 📸 Capturas de Pantalla

|<img width="372" height="822" alt="image" src="https://github.com/user-attachments/assets/d2f33be6-45af-4292-a87d-b06f5d98a01f" />
<img width="375" height="675" alt="image" src="https://github.com/user-attachments/assets/2f687bc4-b28e-4b93-a4d1-2756a3ee2432" />
<img width="362" height="522" alt="image" src="https://github.com/user-attachments/assets/53969ad9-b96a-4310-84fc-44843521ee27" />

<img width="375" height="791" alt="image" src="https://github.com/user-attachments/assets/e5fabec7-baa6-4f11-a29c-e5f766e21542" />
<img width="370" height="777" alt="image" src="https://github.com/user-attachments/assets/28c1966b-a5f8-4bd7-b4d9-13a918fe1348" />
<img width="365" height="687" alt="image" src="https://github.com/user-attachments/assets/9e495f96-2b1f-4094-9409-334f18a9880c" />

## Prompts 
1. Descuento por lavados 
   -"En mi app de lavandería universitaria basada en Compose y Room (LavaRepository -> LavaRepositoryRoom -> PedidoDao), implementa un descuento del 10% para      lavados mayores a 5 kg. Muestra este descuento de forma transparente en todas las pantallas donde se consulta el pedido: en NuevoPedidoScreen al armar el pedido, en DetallePedidoScreen para el estudiante y en GestionPedidoScreen para el administrador al cobrar. Asegúrate de congelar el monto del descuento al momento de guardar el pedido en la base de datos para que los pedidos antiguos no cambien si varían las tarifas futuras. Muestra solo las líneas y archivos modificados respetando MVVM."

2.  Calificacion de pedidos
   -En mi proyecto Android Kotlin con Compose y Room (DetallePedidoViewModel -> LavaRepository), crea un componente de estrellas @Composable fun ComponenteCalificacionEstrellas que reciba calificacionActual: Int?, editable: Boolean y onCalificar: (Int) -> Unit. Cuando el estudiante abra el detalle de un pedido en estado ENTREGADO: si no ha calificado, le permitirá seleccionar de 1 a 5 estrellas y presionar el botón 'Guardar Calificación' para enviar la valoración al ViewModel. Una vez guardado, las estrellas deben quedar estáticas (read-only) sin permitir re-evaluaciones. Asegúrate de usar íconos de Material Icons (Icons.Filled.Star e Icons.Outlined.Star).

3. Enfoque Reutilización de GraficoBarras
   -En mi proyecto Compose tengo un componente reutilizable GraficoBarras(datos: List<DatoGrafico>) que dibuja barras verticales proporcionales con etiquetas. En SesionViewModel, crea el StateFlow gastoMensual: StateFlow<List<DatoGrafico>> que observe el consumo por mes del usuario activo usando flatMapLatest sobre usuarioActual. Mapea los resultados a objetos DatoGrafico(etiqueta = mes, valor = total, color = TurquesaPrimary). Integra este gráfico dentro de una tarjeta en PerfilScreen bajo la sección 'Gasto mensual en lavandería'
