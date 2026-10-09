PROMPTS:

1. Implementación del calendario dinámico

Prompt utilizado:

Implementa un calendario dinámico que muestre los próximos 5 días hábiles a partir de la fecha actual, excluyendo sábados y domingos. Utiliza LocalDate para generar las fechas reales y permite navegar entre semanas mediante flechas. El mes y el año deben actualizarse automáticamente.

Respuesta resumida de la IA:

Se implementaron funciones para generar los días hábiles, controlar la semana mostrada y formatear las fechas. También se agregó la navegación entre semanas.

Correcciones realizadas:

* Se reemplazaron las fechas fijas por fechas generadas dinámicamente.
* Se corrigió el uso de LocalDate para manejar fechas reales.
* Se ajustaron las flechas para avanzar y retroceder entre semanas.

2. Actualización de horarios disponibles

Prompt utilizado:

Modifica los horarios disponibles para que se actualicen cuando el usuario cambie la fecha. Los horarios reservados deben bloquearse según el médico y la fecha seleccionados. Además, al cambiar de fecha, se debe limpiar la hora seleccionada anteriormente.

Respuesta resumida de la IA:

Se implementó un filtro que consulta las citas registradas en el repositorio y excluye los horarios ocupados para el médico y la fecha seleccionados.

Correcciones realizadas:

* Se ajustó la comparación entre las fechas seleccionadas y las fechas guardadas.
* Se corrigió la extracción de la hora inicial de los intervalos reservados.
* Se agregó la limpieza de la hora seleccionada al cambiar de fecha.

3. Formato de fecha y confirmación de la cita

Prompt utilizado:

Actualiza la fecha que se muestra en la pantalla de confirmación para que aparezca completa en español, incluyendo el día de la semana, el número del día, el mes y el año. Guarda la fecha y la hora seleccionadas antes de navegar a la siguiente pantalla.

Respuesta resumida de la IA:

Se creó una función para formatear la fecha completa en español y se actualizó el proceso de selección para guardar la fecha y la hora antes de mostrar la pantalla de confirmación.

Correcciones realizadas:

* Se reemplazó el texto de fecha fijo por la fecha seleccionada dinámicamente.
* Se ajustó el guardado de la fecha y la hora en el repositorio.

PREGUNTAS:
1. El Repositorio es un object porque comparte los mismos datos entre todas las pantallas. Si cada pantalla tuviera su propia lista, las citas no se compartirían.

2. Utilicé estados de Compose para que la búsqueda y los horarios se actualicen automáticamente cuando cambia el texto o la fecha seleccionada.

3. Con navigate() normal, al presionar Atrás regreso a la pantalla anterior. Con popUpTo se eliminan pantallas anteriores para evitar regresar a ellas después de agendar una cita.

4. Corregí el diseño, me habia dado el basico usando colores que no van con el diseño de la app

5. Usaría NavigationDrawer para aplicaciones con muchas opciones y NavigationBar para aplicaciones con pocas secciones principales en la parte inferior.



