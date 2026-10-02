# Lab 04: Mi Carrito TECSUP - Lab04CarritoTecsup

**Desarrollado por:** [Tu Nombre y Apellidos]  
**Curso:** Desarrollo de Aplicaciones Móviles  
**Institución:** TECSUP  

---

## 📱 Descripción del Proyecto

**Mi Carrito TECSUP** es una aplicación desarrollada en Android usando **Jetpack Compose** y el patrón de diseño declarativo de Material 3. La aplicación permite gestionar un carrito de compras dinámico e interactivo:

- **Formulario compacto**: Permite ingresar el nombre del producto, precio unitario en soles (S/) y la cantidad deseada.
- **Lista desplegable (`LazyColumn`)**: Muestra los productos agregados mediante tarjetas individuales (`TarjetaProducto`) con la anatomía especificada (nombre, detalle de precio x cantidad, importe total e ícono de eliminación).
- **Estado Vacío**: Cuando no hay productos en el carrito, se muestra un contenedor centrado (`Box`) con el mensaje *"Tu carrito está vacío. Agrega tu primer producto"*.
- **Panel de Totales Fijo**: Muestra en tiempo real la cantidad de productos, el subtotal, el IGV (18%), los descuentos aplicables y el total general calculado automáticamente.
- **Confirmación de Borrado (Reto Opcional +1 pt)**: Diálogo de alerta (`AlertDialog`) para confirmar antes de eliminar un producto.
- **Descuento Automático Lab 02 (Reto Opcional +1 pt)**: Aplicación de descuento dinámico mediante `when` (5% si el total supera S/ 3,000.00 y 10% si supera S/ 5,000.00).

---

## 🖼️ Capturas de Pantalla

| Estado Vacío | Carrito con Productos |
| :---: | :---: |
| ![Estado Vacío](docs/estado_vacio.png) | ![Carrito con Productos](docs/carrito_productos.png) |

*(Nota: Reemplazar o colocar tus capturas en la carpeta `docs/` o adjuntar los enlaces de imagen).*

---

## ❓ Cuestionario / Respuestas Conceptuales

### a) ¿Por qué `mutableStateListOf` y no una `MutableList` normal?
Una `MutableList` normal en Kotlin es únicamente una colección mutable de datos sin capacidad de notificación. Cuando agregamos o eliminamos elementos en una `MutableList` tradicional, Jetpack Compose **no detecta que el estado de la pantalla ha cambiado**, por lo que la interfaz gráfica no se recompone ni se actualiza.

Por el contrario, `mutableStateListOf` crea una lista observable de Compose (`SnapshotStateList`). Al agregar o eliminar elementos mediante `.add()` o `.remove()`, Compose recibe automáticamente una señal de cambio de estado y ejecuta la **recomposición** de los componentes afectados (`LazyColumn`, mensajes de estado vacío y el panel de totales), garantizando que la UI refleje los datos actualizados de inmediato.

---

### b) ¿Por qué la lista se declara con `val` y aún así podemos agregarle elementos?
En Kotlin, la palabra clave `val` significa que la **referencia** de la variable es inmutable (no se puede reasignar `productos` a otro objeto distinto).

Sin embargo, la mutabilidad del **objeto interno** o contenido referenciado es independiente de la referencia. El objeto instanciado por `mutableStateListOf` posee métodos de modificación interna (`add()`, `remove()`, `clear()`). Por lo tanto, declarar la variable con `val` evita que la referencia sea sobrescrita accidentalmente, permitiendo modificar los elementos dentro de la lista de forma segura.

---

### c) ¿Qué hace `weight(1f)` en la `LazyColumn` (o en la `Box` de estado vacío)?
En un contenedor vertical `Column`, el modificador `Modifier.weight(1f)` le indica al sistema de diseño de Compose que ese componente debe **ocupar todo el espacio vertical disponible / sobrante** en la pantalla, luego de que los demás elementos de tamaño fijo (como el formulario superior y el panel de totales inferior) hayan tomado su espacio respectivo.

Esto asegura dos cosas fundamentales:
1. La lista `LazyColumn` se expande dinámicamente y permite hacer scroll interno si hay muchos elementos.
2. El panel de totales queda **siempre fijo en la parte inferior de la pantalla**, cumpliendo con los lineamientos de diseño evaluables.

---

## 🛠️ Estructura del Código

```text
com.ramirez.tecstore
├── MainActivity.kt                # Actividad principal y punto de entrada
├── model
│   └── Producto.kt               # Data class Producto (nombre, precio, cantidad)
└── ui
    ├── PantallaCarrito.kt        # Estado principal, formulario y panel de totales
    └── TarjetaProducto.kt        # Card individual para cada elemento de la lista
```

---

## 📌 Historial de Commits del Laboratorio

1. **COMMIT 1**: `"Proyecto inicial"`
2. **COMMIT 2**: `"Formulario agrega productos a lista observable"`
3. **COMMIT 3**: `"Avance: LazyColumn inicial"`
4. **COMMIT 4**: `"Agrega TarjetaProducto con boton eliminar"`
5. **COMMIT 5**: `"Agrega panel de totales y estado vacio con Box"`
6. **COMMIT 6**: `"README con capturas y respuestas conceptuales"`
