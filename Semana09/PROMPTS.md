# Registro de asistencia de IA - Fase 2

Rama de trabajo: `mejora-ia-sem9`, creada a petición del usuario desde el cierre de la Fase 1.

Esta implementación se realiza con Codex. La guía propone Gemini en Android Studio; este registro describe la herramienta y conversación reales, sin atribuir respuestas a Gemini.

## Mejoras acordadas

1. Eliminar una publicación: API + Repository + ViewModel + View.
2. Buscar por título entre las publicaciones descargadas: ViewModel + View.
3. Paginación de 20 en 20 con `_start`, `_limit` y botón Cargar más: API + Repository + ViewModel + View.

La paginación reemplaza la propuesta inicial de agregar un encabezado HTTP.

| Prompt que escribí | Qué respondió la IA (Codex) | Qué cambié o descarté | Por qué |
| --- | --- | --- | --- |
| «crea la rama mejora-ia-sem9 y trabajemos ahi las 3 mejoras» | Creó la rama y propuso eliminar, búsqueda por título y encabezado Accept. Preparó primero la eliminación con DELETE y Response&lt;Unit&gt;. | Se conserva MVVM. El Repository revisa el código HTTP y devuelve Result&lt;Unit&gt;; el ViewModel expone el estado y la pantalla solo envía eventos. Se cancela la petición al salir del detalle. | Response no lanza automáticamente una excepción por códigos HTTP fallidos; comprobar isSuccessful impide anunciar una eliminación que falló. La cancelación no se debe convertir en error de red. |
| «quiero q en vez de agregar el encabezado sea q cargue de 20 en 20» | Cambió el plan a eliminar, búsqueda y paginación de 20 en 20. | Se descartó el interceptor de encabezado y se implementó la consulta con _start y _limit. | El usuario eligió una mejora que permite controlar cuántas publicaciones se descargan. |
| «has el codigo de las 3 meejoras de una vez y ya luego lo dividimos por commits» | Completó las tres mejoras juntas, manteniendo las capas. Añadió pruebas del ViewModel para comprobar su interacción. | La búsqueda se aplica sobre una copia de las publicaciones descargadas, no sobre el resultado de la búsqueda anterior. El cursor se calcula con las páginas originales; el error al cargar más se mantiene separado del estado de la lista. | Evita perder filas al limpiar la búsqueda, saltar publicaciones después de eliminar y borrar datos visibles cuando falla una página adicional. No se crean commits hasta la revisión. |

## Revisión de la primera mejora

- La respuesta DELETE se revisa en `PostRepositoryRemote`, sin importar Retrofit en el ViewModel.
- Una respuesta HTTP exitosa puede no tener cuerpo; no se usa `body()!!`.
- Un fallo HTTP produce Result.failure y permite reintentar desde el detalle.
- Se respeta CancellationException y se evitan envíos duplicados mientras la petición está activa.
- Solo después del éxito se retira la publicación de la lista local.
- JSONPlaceholder simula DELETE: si se recarga desde el servidor, la publicación puede reaparecer.
- Compilación, lint y pruebas unitarias aprobados: respuesta exitosa vacía, error HTTP y cancelación. El usuario autorizó crear y subir los tres commits después de preparar el conjunto.

## Revisión de búsqueda y paginación

- La búsqueda filtra por título sin peticiones, ignora mayúsculas y recorta espacios externos. Se vuelve a aplicar al descargar otra página.
- La API recibe `_start`, `_limit` y `userId` opcional. El Repository sigue devolviendo Result.
- El ViewModel conserva los datos originales descargados y expone la lista visible. La pantalla solo observa estados y envía eventos.
- Cambiar de usuario cancela la petición anterior y reinicia el cursor. Una respuesta anterior no sustituye el filtro actual.
- Un fallo al cargar más conserva la lista y permite reintentar el mismo inicio. Se bloquean peticiones simultáneas de la misma página.
- El cursor avanza por la cantidad recibida del servidor, no por las filas que deja la búsqueda ni por la cantidad posterior a eliminar.
- Una página de menos de 20 elementos indica el final. Si el total es múltiplo de 20, se hace una consulta vacía adicional para detectarlo.
- Compilación y lint aprobados. Las 12 pruebas unitarias pasan: una prueba base, tres de eliminación y ocho del ViewModel.
- La prueba automatizada usa un Repository controlado para provocar errores y respuestas demoradas sin depender de la red.
- En el emulador se comprobó además el servicio real: carga inicial de 20, segunda página hasta 40, búsqueda por título, DELETE exitoso y eliminación local hasta 39, y cambio a Usuario 2 con 10 resultados.
- Tras la instrucción «sube los 3 commits», se separó el trabajo en eliminación, búsqueda y paginación. Se compilaron y probaron los estados intermedios, se verificó que el código final coincidiera con el conjunto aprobado y se conservaron los resultados y decisiones en este registro.

## Rediseño posterior

| Prompt que escribí | Qué respondió la IA (Codex) | Qué cambié o descarté | Por qué |
| --- | --- | --- | --- |
| «ASEGURATE DE CAMBIARLE EL TEMA DE LA APP A ESTOS COLORES Y USARLOS EN TODA LA APP […] CREA LOS ICONOS DE MENU Y SIN CONEXION COMO COMPSABLES […] EL TEXTO SEA GRANDE» y cinco referencias visuales | Aplicó un tema coral y gris claro, tipografía sans serif grande, tarjetas y formulario blancos, avatares y comentarios en burbujas. Dibujó iconos reutilizables con Canvas. | Se eliminaron los colores de plantilla y dinámicos. El menú inferior reemplaza al botón flotante. Se conservan búsqueda, Cargar más y eliminación, integrados con la nueva paleta; no se recrean las barras de desplazamiento de las referencias. | Mantener la identidad visual en todas las pantallas sin perder las funciones verificadas ni cambiar las capas de datos y ViewModel. |

Se revisaron las cinco pantallas en el emulador y se actualizaron sus capturas reales. La creación sigue devolviendo id 101, y el modo avión muestra el icono de desconexión y Reintentar. Compilación, lint y 12 pruebas unitarias aprobados. El usuario autorizó registrar y subir el rediseño en un único commit con la instrucción «SUBE UN COMMIT DE ESTO».
