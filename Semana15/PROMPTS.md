# Registro de asistencia de IA - Fase 2

Rama: `mejora-ia-sem15`.

La asistencia registrada aquí corresponde a Codex en esta conversación. No se utilizó Gemini en Android Studio y no se atribuyen respuestas a esa herramienta. Los prompts se reproducen tal como se recibieron; cada fila explica la parte del encargo aplicada a una mejora.

Selección: ante «elige las mejoras mas faciles», la IA propuso texto expandible, dos canales y receptor del cargador. El usuario autorizó implementarlas con «ya crea los 3 y luego ya te digo para q subas los 3 commit». Los commits se mantienen locales hasta una nueva indicación.

| Prompt que escribí | Qué respondió la IA (Codex) | Qué cambié o descarté | Por qué |
| --- | --- | --- | --- |
| «ya crea los 3 y luego ya te digo para q subas los 3 commit» - texto expandible | Añadir `NotificationCompat.BigTextStyle().bigText(aviso.texto)` al Notificador. | Se conserva el texto corto original de la fase 1; una prueba usa un mensaje ficticio largo para comprobar que se publica completo. | La presentación corresponde al Notificador y no requiere cambiar el ViewModel, el historial ni el contenido de los avisos existentes. |

## Verificación

- Texto expandible: prueba instrumentada de `Notification.EXTRA_BIG_TEXT` con un mensaje largo y saltos de línea.
