# Prompts propuestos para CampusGPS

## Filtrado por categoría

Implementa el filtrado por categoría en CampusGPS con Kotlin, Jetpack Compose y Material 3, respetando la arquitectura MVVM + Repository existente. Incorpora chips para Todas, Estudio, Comida, Eventos y Servicios. Mantén la categoría seleccionada en el ViewModel y deriva mediante StateFlow la lista visible que compartirán el mapa y la lista de lugares. Conserva los datos originales y sus distancias al cambiar de filtro. Calcula el lugar más cercano a partir de los lugares visibles y limpia la selección cuando el lugar seleccionado quede oculto. Los Composables deben recibir datos y callbacks. Conserva el manejo de permisos y errores.

## Radio de cercanía

Agrega un selector de radio de 100, 200 y 500 metros a CampusGPS. Guarda el radio seleccionado en el ViewModel y deriva los identificadores de los lugares cuya distancia calculada sea menor o igual al radio. Excluye de esa clasificación los lugares con distancia desconocida. Dibuja un Circle de Maps Compose alrededor de la posición disponible y muestra una indicación en las filas de los lugares que estén dentro del radio. Integra esta función con el filtro de categoría y actualiza sus resultados al cambiar el radio o la ubicación. Mantén los cálculos fuera de los Composables y utiliza las dependencias existentes.

## Mapa en modo oscuro

Adapta el mapa de CampusGPS al tema oscuro del dispositivo utilizando MapStyleOptions y un recurso JSON local. Configura colores legibles para calles, agua, superficies y etiquetas. Aplica el estilo mediante MapProperties y restaura el estilo normal al volver al tema claro. Conserva los marcadores, los colores del lugar más cercano, el círculo de cercanía y las animaciones de cámara. Mantén esta personalización en la capa de presentación y comprueba la compatibilidad con Maps Compose 6.12.0.

