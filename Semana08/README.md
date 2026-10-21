# LavaCampus 🧺

Aplicación móvil para la gestión de lavandería universitaria en el campus. Construida con **Kotlin**, **Jetpack Compose + Material 3**, **Room Database**, **DataStore Preferences** y **Navigation Compose** bajo el patrón de arquitectura **MVVM + Repository Pattern**.

---

## 👥 Perfiles de Usuario

- **Estudiante (Cliente):**
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

## ❓ Preguntas de Reflexión ("Para pensar")

1. **¿Por qué un pedido tiene detalles en otra tabla?**
   - *Respuesta:* Un pedido puede incluir múltiples servicios (ej. lavado por kg y planchado). Guardar cada línea como una fila separada en `detalles_pedido` evita duplicar información de cabecera y permite realizar consultas analíticas agrupadas con `GROUP BY` e `INNER JOIN`.
2. **¿Por qué guardar `precioUnitario` y `subtotal` en el detalle?**
   - *Respuesta:* Es una "foto" del costo en el instante de la compra. Si en el futuro la lavandería cambia los precios de sus servicios, los pedidos antiguos conservan lo que costaron en su momento sin alterar los ingresos históricos.
3. **¿Por qué usamos una interface para el Repositorio (`LavaRepository`)?**
   - *Respuesta:* Para desacoplar la interfaz de usuario y los ViewModels de la fuente de datos. Cuando la app se conecte a un Web Service o servicio en la nube, solo se creará una nueva implementación de la interface sin modificar ni un solo ViewModel ni pantalla Composable.

---

## 📸 Capturas de Pantalla

| Perfil Estudiante | Perfil Administrador |
| :---: | :---: |
| *Inicio con Seguimiento de Ropa* | *Panel con Gráficos y Métricas* |
| *Nuevo Pedido y Selección de Kilos* | *Bandeja y Filtro por Estados* |
| *Detalle con Casillero Asignado* | *Gestión de Casilleros C-01 a C-30* |
