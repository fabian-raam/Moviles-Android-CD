# Registro de asistencia de IA - Fase 2

Rama: `mejora-ia-sem15`.

La asistencia registrada aquí corresponde a Codex en esta conversación. No se utilizó Gemini en Android Studio y no se atribuyen respuestas a esa herramienta. Los prompts se reproducen tal como se recibieron; cada fila explica la parte del encargo aplicada a una mejora.

Selección: ante «elige las mejoras mas faciles», la IA propuso texto expandible, dos canales y receptor del cargador. El usuario autorizó implementarlas con «ya crea los 3 y luego ya te digo para q subas los 3 commit». Los commits se mantienen locales hasta una nueva indicación.

| Prompt que escribí | Qué respondió la IA (Codex) | Qué cambié o descarté | Por qué |
| --- | --- | --- | --- |
| «ya crea los 3 y luego ya te digo para q subas los 3 commit» - texto expandible | Añadir `NotificationCompat.BigTextStyle().bigText(aviso.texto)` al Notificador. | Se conserva el texto corto original de la fase 1; una prueba usa un mensaje ficticio largo para comprobar que se publica completo. | La presentación corresponde al Notificador y no requiere cambiar el ViewModel, el historial ni el contenido de los avisos existentes. |
| «ya crea los 3 y luego ya te digo para q subas los 3 commit» - dos canales | Crear Avisos con importancia predeterminada y Urgentes con importancia alta; enviar los recordatorios al segundo. | Se elige por `Origen.RECORDATORIO`, sin comparar títulos ni pasar clases de Android al ViewModel. Se añade prioridad alta para API 24 y 25. | El origen ya expresa qué tipo de aviso decidió el ViewModel. La asignación del canal y su importancia pertenecen al Notificador. |
| «ya crea los 3 y luego ya te digo para q subas los 3 commit» - receptor del cargador | Añadir un receptor de conexión/desconexión, un switch, estado en el ViewModel y origen Cargador. | El receptor no notifica: solo entrega un booleano. La pantalla lo registra con `RECEIVER_NOT_EXPORTED` y lo retira en su propio `onDispose`. Se distinguen los switches con descripciones accesibles y se adapta la suite existente. | Evita introducir `Context` o `BroadcastReceiver` en el ViewModel, conserva la separación MVVM y permite activar cada escucha por separado. Esta mejora cumple el requisito de modificar el ViewModel. |

## Verificación

- Texto expandible: prueba instrumentada de `Notification.EXTRA_BIG_TEXT` con un mensaje largo y saltos de línea.
- Dos canales: prueba de creación de Urgentes con importancia alta y de los canales usados por una notificación de botón y un recordatorio. La aparición emergente depende también de los ajustes de Android y del usuario.
- Cargador: prueba instrumentada de conexión, desconexión, recreación de Activity, switch apagado y origen/canal de la notificación; la simulación de batería se restaura al terminar.
- Durante la regresión se corrigió el aislamiento entre pruebas: las notificaciones previas del cargador podían satisfacer una espera basada solo en IDs. Ahora se limpian las notificaciones de la app al iniciar el escenario y se espera también el título correcto; la prueba del cargador retira sus avisos al terminar.
- Resultado final en API 37: compilación y Lint correctos; `OK (4 tests)` en la suite de las mejoras y la regresión de fase 1. Evidencia: [fase2-mejoras.txt](docs/pruebas/fase2-mejoras.txt).
