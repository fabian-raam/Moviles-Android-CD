PROMPT:

Analiza primero TODO mi proyecto Android actual antes de realizar cualquier modificación.

Estoy trabajando en un laboratorio Android desarrollado con:

- Kotlin
- Jetpack Compose
- Material 3
- Navigation Compose

IMPORTANTE:
El proyecto está restaurado a su versión ORIGINAL, antes de las mejoras realizadas con IA.

Actualmente existen estas pantallas:

- HomeScreen.kt
- ListScreen.kt
- DetailScreen.kt
- ProfileScreen.kt

Y la navegación utiliza:

- Screen.kt
- AppNavigation.kt
- MainActivity.kt

Debes implementar TODA la mejora visual descrita en este prompt desde cero.

NO quiero correcciones parciales.
NO asumas que los cambios anteriores siguen existiendo.
NO reconstruyas el proyecto desde cero.
Trabaja sobre los archivos existentes y conserva la navegación funcional actual.

Antes de modificar código:

1. Revisa MainActivity.kt.
2. Revisa Screen.kt.
3. Revisa AppNavigation.kt.
4. Revisa HomeScreen.kt.
5. Revisa ListScreen.kt.
6. Revisa DetailScreen.kt.
7. Revisa ProfileScreen.kt.
8. Revisa ui.theme.
9. Revisa app/build.gradle.kts.
10. Comprende cómo funciona actualmente Navigation Compose.
11. Comprende cómo DetailScreen recibe itemId.

========================================================
OBJETIVO GENERAL
========================================================

Transforma la aplicación actual en un Portal Académico.

El flujo final debe ser EXACTAMENTE:

LoginScreen
      ↓
HomeScreen
   ├───────────────┐
   ↓               ↓
ListScreen     ProfileScreen
   ↓
DetailScreen

Además:

HomeScreen -> Cerrar Sesión -> LoginScreen
ProfileScreen -> Cerrar Sesión -> LoginScreen

No agregues ninguna otra pantalla.

========================================================
DATOS DEL USUARIO PRINCIPAL
========================================================

Utiliza:

Nombre:
Fabian Ramirez

Carrera:
Ingeniería de Software

Correo:
fabian.ramirez@tecsup.edu.pe

Teléfono:
+51 987 654 321

Ciclo:
VI Ciclo

Código:
2024-0001

Facultad:
Ingeniería y Tecnología

NO utilices Juan León.

========================================================
REGLAS TÉCNICAS
========================================================

Utiliza:

- Kotlin
- Jetpack Compose
- Material 3
- Navigation Compose
- Column
- Row
- Box
- LazyColumn cuando corresponda
- Card
- Scaffold
- TopAppBar
- OutlinedTextField
- Button
- Text
- Icon
- IconButton
- Spacer
- HorizontalDivider
- Brush
- BorderStroke
- Modifier
- Alignment
- Arrangement

Puedes utilizar listas y listas anidadas si ayudan a organizar los datos.

Usa LazyColumn específicamente para el Directorio de Alumnos.

No fuerces LazyColumn en pantallas que no lo necesitan.

NO utilices XML.

NO agregues:

- Firebase
- Room
- Retrofit
- API REST
- base de datos
- ViewModel
- Repository
- Hilt
- BottomNavigation
- NavigationDrawer
- FloatingActionButton
- buscadores
- filtros
- menús adicionales
- animaciones
- funcionalidades no solicitadas

No agregues dependencias externas innecesarias.

========================================================
1. LOGINSCREEN
========================================================

Crea:

screens/LoginScreen.kt

Debe ser la PRIMERA pantalla de la aplicación.

Debe parecer un Portal Académico sencillo.

--------------------------------------------------------
FONDO
--------------------------------------------------------

Toda la pantalla debe tener un degradado vertical MUY SUAVE.

Parte superior:
lila/morado extremadamente claro.

Parte inferior:
blanco con un ligero tono rosado/lila.

NO utilices un fondo morado intenso.

--------------------------------------------------------
CARD CENTRAL
--------------------------------------------------------

Coloca UNA Card centrada horizontalmente y aproximadamente en el centro vertical de la pantalla.

Características:

- fondo gris/lila extremadamente claro;
- esquinas redondeadas;
- sombra/elevación suave;
- ancho amplio;
- margen visible a ambos lados;
- padding interno.

No debe ocupar todo el ancho.

--------------------------------------------------------
TÍTULO LOGIN
--------------------------------------------------------

Dentro de la Card:

Portal Académico

Debe estar:

- centrado;
- morado;
- negrita;
- tamaño mayor al resto.

Debajo:

Accede a tu cuenta

Debe estar:

- centrado;
- pequeño;
- gris oscuro.

--------------------------------------------------------
CORREO
--------------------------------------------------------

Agrega un OutlinedTextField:

Correo Institucional

Características:

- ocupa todo el ancho disponible;
- icono de correo a la izquierda;
- esquinas redondeadas.

Configura:

KeyboardOptions(
    keyboardType = KeyboardType.Email
)

IMPORTANTE:

No basta con cambiar el teclado.

VALIDA realmente que el texto tenga formato de correo electrónico.

Puedes utilizar:

Patterns.EMAIL_ADDRESS

El correo es inválido si:

- está vacío;
- no coincide con un formato válido de email.

Si es inválido:

isError = true

y debajo mostrar:

Correo inválido

No permitas navegar al Home con correo inválido.

NO exijas un dominio específico.

--------------------------------------------------------
CONTRASEÑA
--------------------------------------------------------

Agrega otro OutlinedTextField:

Contraseña

Debe tener:

- icono de candado a la izquierda;
- icono de ojo a la derecha;
- PasswordVisualTransformation;
- opción para mostrar/ocultar contraseña.

No agregues reglas complejas.

Solo comprueba que no esté vacía antes de iniciar sesión.

--------------------------------------------------------
BOTÓN LOGIN
--------------------------------------------------------

Debajo:

INICIAR SESIÓN

Debe:

- ocupar todo el ancho interno;
- fondo morado;
- texto blanco;
- esquinas redondeadas.

Si:

correo válido && contraseña no vacía

entonces navegar a:

Screen.Home.route

No implementes autenticación real.

No uses Firebase.

Al entrar al Home, evita que Back regrese al Login utilizando correctamente popUpTo.

--------------------------------------------------------
TEXTO INFERIOR
--------------------------------------------------------

Debajo:

¿Olvidaste tu contraseña?

Centrado.

Pequeño.

No debe realizar ninguna acción.

NO crees otra pantalla.

========================================================
2. HOMESCREEN
========================================================

Modifica HomeScreen.kt existente.

NO crees otro HomeScreen.

Debe utilizar un degradado vertical:

parte superior:
morado;

zona media:
lila;

zona inferior:
lila extremadamente claro/casi blanco.

--------------------------------------------------------
BIENVENIDA
--------------------------------------------------------

En la zona superior/media superior:

Bienvenido,
Fabian Ramirez

IMPORTANTE:

DEBE ESTAR CENTRADO HORIZONTALMENTE.

NO debe quedar alineado a la izquierda.

Utiliza un contenedor con:

Modifier.fillMaxWidth()

y:

textAlign = TextAlign.Center

Características:

- blanco;
- negrita;
- tamaño grande;
- dos líneas.

Debajo:

¿Qué deseas gestionar hoy?

También debe:

- utilizar fillMaxWidth();
- estar centrado horizontalmente;
- utilizar TextAlign.Center;
- ser más pequeño;
- color blanco/lila claro.

Mantén ambos textos aproximadamente a la altura mostrada en el diseño.

--------------------------------------------------------
TARJETAS HOME
--------------------------------------------------------

Debajo coloca EXACTAMENTE dos Cards.

PRIMERA:

Directorio de Alumnos
Ver y gestionar estudiantes

Debe tener:

- fondo blanco;
- esquinas redondeadas;
- sombra suave;
- icono de alumnos a la izquierda;
- pequeño contenedor lila detrás del icono;
- textos a la derecha.

Toda la Card debe navegar a:

Screen.List.route

SEGUNDA:

Mi Perfil Académico
Datos personales y progreso

Mismo diseño.

Icono de perfil/persona.

Toda la Card debe navegar a:

Screen.Profile.route

No agregues más Cards.

--------------------------------------------------------
CERRAR SESIÓN HOME
--------------------------------------------------------

En la parte inferior:

icono + Cerrar Sesión Segura

Color rojo/rosado suave.

Centrado horizontalmente.

Debe quedar cerca del borde inferior respetando el padding del sistema.

Al pulsarlo:
regresar a LoginScreen y limpiar correctamente el back stack.

========================================================
3. LISTSCREEN
========================================================

Modifica ListScreen.kt existente.

Debe representar:

Directorio de Alumnos

Conserva:

- Scaffold
- TopAppBar
- NavController
- botón volver
- itemId
- navegación hacia DetailScreen

--------------------------------------------------------
TOP APP BAR
--------------------------------------------------------

La barra superior NO debe ser completamente blanca.

Debe tener un fondo lila/morado muy claro.

Utiliza aproximadamente:

Color(0xFFE8D8FF)

Título:

Directorio de Alumnos

Debe tener color morado oscuro, aproximadamente:

Color(0xFF4F378B)

La flecha de volver debe mantener coherencia con ese encabezado.

--------------------------------------------------------
LISTA
--------------------------------------------------------

Utiliza LazyColumn.

Debe contener EXACTAMENTE:

1.
Fabian Ramirez
Ingeniería de Software

2.
María García
Arquitectura

3.
Carlos Pérez
Medicina

4.
Ana López
Derecho

5.
Luis Ramírez
Administración

Cada alumno debe estar dentro de una Card.

Estructura:

Row
├── Avatar
├── Column
│   ├── Nombre
│   └── Carrera
└── Flecha derecha

--------------------------------------------------------
CARDS DE ALUMNOS
--------------------------------------------------------

Las Cards NO deben ser blanco puro.

Utiliza un gris/lila claro aproximadamente:

Color(0xFFE4E0E6)

Deben ser ligeramente más oscuras que el fondo general.

Características:

- casi todo el ancho;
- pequeños márgenes laterales;
- esquinas redondeadas;
- separación vertical;
- sombra/borde muy discreto.

Nombre:
oscuro y negrita.

Carrera:
morado, aproximadamente:

Color(0xFF6750A4)

Flecha:
gris claro.

--------------------------------------------------------
AVATAR
--------------------------------------------------------

NO agregues:

- Coil
- URLs
- imágenes de Internet
- dependencias nuevas

Si no hay imágenes locales, utiliza círculos con iniciales.

--------------------------------------------------------
NAVEGACIÓN
--------------------------------------------------------

Toda la Card debe ser clickeable.

Conserva:

navController.navigate(
    Screen.Detail.createRoute(index + 1)
)

NO elimines itemId.

========================================================
4. DETAILSCREEN
========================================================

Modifica DetailScreen.kt existente.

Debe representar:

Expediente Académico

Conserva:

- Scaffold
- TopAppBar
- NavController
- itemId: Int
- botón volver

--------------------------------------------------------
TOP APP BAR
--------------------------------------------------------

Título:

Expediente Académico

Fondo claro.

Flecha de volver.

--------------------------------------------------------
CABECERA DEL EXPEDIENTE
--------------------------------------------------------

IMPORTANTE:

NO UTILICES UN COLOR MORADO SÓLIDO.

NO hagas:

background(Color(0xFF6750A4))

La cabecera debe utilizar EXACTAMENTE EL MISMO CONCEPTO DE DEGRADADO que la cabecera de ProfileScreen.

Quiero que DetailScreen y ProfileScreen tengan cabeceras visualmente coherentes.

Utiliza:

Brush.horizontalGradient(...)

La cabecera debe comenzar a la izquierda con morado y terminar a la derecha con un tono rosado/gris cálido.

Puedes utilizar aproximadamente:

val headerGradient = Brush.horizontalGradient(
    colors = listOf(
        Color(0xFF6750A4),
        Color(0xFF9A7FA8),
        Color(0xFFC5B7B9)
    )
)

IMPORTANTE:

Si ProfileScreen utiliza un degradado que visualmente ya coincide con el diseño, REUTILIZA EXACTAMENTE ESOS MISMOS COLORES en DetailScreen.

NO inventes un segundo degradado diferente.

Las dos cabeceras:

DetailScreen
ProfileScreen

deben verse como parte de la misma aplicación.

La cabecera de DetailScreen debe ocupar todo el ancho.

Debe tener una altura similar a la cabecera mostrada en el diseño.

--------------------------------------------------------
AVATAR DETAIL
--------------------------------------------------------

El avatar debe superponerse visualmente entre:

CABECERA CON DEGRADADO
y
CONTENIDO INFERIOR

Debe ser circular y grande.

Si no hay imagen:

mostrar FR

No agregues dependencias para imágenes.

--------------------------------------------------------
DATOS PRINCIPALES
--------------------------------------------------------

Debajo:

Fabian Ramirez

Ingeniería de Software

Ambos centrados.

Fabian Ramirez:

- negrita;
- oscuro;
- tamaño mayor.

Ingeniería de Software:

- morado;
- tamaño menor.

--------------------------------------------------------
CARD DE INFORMACIÓN
--------------------------------------------------------

Debajo crea UNA Card.

Debe tener:

- fondo lila/gris muy claro;
- esquinas redondeadas;
- padding;
- borde fino visible.

Utiliza aproximadamente:

containerColor = Color(0xFFF4EFF6)

y:

border = BorderStroke(
    1.dp,
    Color(0xFFE0D8E8)
)

NO utilices borde negro.

NO dejes la Card sin borde.

--------------------------------------------------------
INFORMACIÓN
--------------------------------------------------------

Muestra EXACTAMENTE:

Código
2024-0001

Correo
fabian.ramirez@example.com

Facultad
Ingeniería y Tecnología

Cada dato puede utilizar:

Row
├── Icon
└── Column
    ├── etiqueta
    └── valor

NO muestres:

(ID: 1)

NO muestres itemId visualmente.

itemId debe seguir existiendo únicamente para conservar la navegación del laboratorio.

Después:

HorizontalDivider

Luego:

Biografía

Estudiante destacado con interés en desarrollo Android.

No agregues nada más.

========================================================
5. PROFILESCREEN
========================================================

Modifica ProfileScreen.kt existente.

Debe representar:

Configuración de Perfil

Conserva NavController.

--------------------------------------------------------
TOP APP BAR
--------------------------------------------------------

Flecha de volver.

Título:

Configuración de Perfil

Fondo claro.

--------------------------------------------------------
CABECERA PROFILE
--------------------------------------------------------

Debajo:

cabecera de ancho completo.

Utiliza Brush.horizontalGradient.

El degradado debe ir:

MORADO
↓ horizontalmente
ROSADO/GRIS CÁLIDO

Utiliza el mismo degradado que DetailScreen.

Por ejemplo:

val headerGradient = Brush.horizontalGradient(
    colors = listOf(
        Color(0xFF6750A4),
        Color(0xFF9A7FA8),
        Color(0xFFC5B7B9)
    )
)

Si durante la implementación encuentras una combinación que se aproxima mejor al diseño, puedes ajustarla ligeramente.

PERO:

DetailScreen y ProfileScreen DEBEN utilizar exactamente el mismo degradado.

En el centro:

avatar circular.

Debajo:

Fabian Ramirez

Texto blanco y destacado.

--------------------------------------------------------
INFORMACIÓN PERSONAL
--------------------------------------------------------

Después:

INFORMACIÓN PERSONAL

Debajo:

Nombre Completo
Fabian Ramirez

Correo
fabian.ramirez@tecsup.edu.pe

Teléfono
+51 987 654 321

Utiliza Row para cada dato:

[Icono] [Column con etiqueta + valor]

Mantén el diseño limpio.

NO coloques cada dato en una Card individual.

--------------------------------------------------------
ACADÉMICO
--------------------------------------------------------

Después:

ACADÉMICO

Muestra:

Carrera
Ingeniería de Software

Ciclo Actual
VI Ciclo

Utiliza el mismo estilo.

========================================================
POSICIÓN DE CERRAR SESIÓN EN PROFILESCREEN
========================================================

ESTA INSTRUCCIÓN ES CRÍTICA.

En implementaciones anteriores el botón "Cerrar Sesión" quedó demasiado arriba.

NO quiero que aparezca inmediatamente después de:

Ciclo Actual
VI Ciclo

NO quiero simplemente un Spacer pequeño.

NO quiero que su posición dependa de cuánto contenido exista encima.

El botón debe quedar ANCLADO VISUALMENTE CERCA DE LA PARTE INFERIOR DE LA PANTALLA.

Para garantizarlo, estructura el contenido principal de ProfileScreen utilizando un Box que ocupe TODO el espacio disponible.

Ejemplo conceptual:

Scaffold(...) { innerPadding ->

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ) {

            // Cabecera
            // Información personal
            // Académico

        }

        Button(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 24.dp
                ),
            ...
        ) {
            ...
        }
    }
}

IMPORTANTE:

La idea estructural es:

┌─────────────────────────────┐
│ Configuración de Perfil     │
├─────────────────────────────┤
│                             │
│ CABECERA                    │
│                             │
│ INFORMACIÓN PERSONAL        │
│ ...                         │
│                             │
│ ACADÉMICO                   │
│ ...                         │
│                             │
│                             │
│       ESPACIO LIBRE         │
│                             │
│                             │
│ ┌─────────────────────────┐ │
│ │      Cerrar Sesión      │ │
│ └─────────────────────────┘ │
│                             │
└─────────────────────────────┘

El botón debe permanecer abajo aunque haya poco contenido.

NO uses solamente:

Spacer(height = ...)

para intentar empujarlo.

NO utilices un padding superior gigante.

NO uses offset() con números arbitrarios.

Utiliza alineación del layout:

Alignment.BottomCenter

para garantizar su posición.

--------------------------------------------------------
BOTÓN CERRAR SESIÓN
--------------------------------------------------------

Debe ser ancho.

Fondo:

rosado extremadamente claro.

Contenido:

rojo/rosado.

Debe contener:

icono de salida + Cerrar Sesión

Debe tener esquinas redondeadas.

Al pulsarlo:

volver a LoginScreen y limpiar correctamente el back stack.

========================================================
6. SCREEN.KT
========================================================

Revisa el código existente.

NO lo reescribas innecesariamente.

Agrega únicamente:

Login

Mantén:

Home
List
Detail
Profile

Mantén la ruta Detail con itemId.

========================================================
7. APPNAVIGATION.KT
========================================================

Integra LoginScreen.

La pantalla inicial debe ser Login.

Debe quedar conceptualmente:

NavHost(
    navController = navController,
    startDestination = Screen.Login.route
)

Mantén:

Login -> Home
Home -> List
Home -> Profile
List -> Detail

No agregues otras rutas.

========================================================
8. MAINACTIVITY.KT
========================================================

Si actualmente MainActivity ya ejecuta:

AppNavigation()

dentro del Theme:

NO LO MODIFIQUES.

========================================================
9. PALETA
========================================================

Utiliza como referencia:

Morado principal:
#6750A4

Morado oscuro:
#4F378B

Lila claro:
#E8D8FF

Fondo claro:
#F7F2FA

Cards gris/lila:
#E4E0E6

Card expediente:
#F4EFF6

Borde expediente:
#E0D8E8

Logout:
rosado/rojo suave.

Para las cabeceras de DetailScreen y ProfileScreen:

NO usar un color sólido.

AMBAS deben utilizar el MISMO:

Brush.horizontalGradient

aproximadamente:

Color(0xFF6750A4)
→ Color(0xFF9A7FA8)
→ Color(0xFFC5B7B9)

========================================================
10. RESPONSIVIDAD
========================================================

Utiliza:

fillMaxSize()
fillMaxWidth()
padding()
weight()
Alignment
Arrangement

No utilices posiciones absolutas innecesarias.

No utilices offsets arbitrarios para arreglar posiciones.

El botón inferior de ProfileScreen debe resolverse mediante estructura de layout, específicamente alineándolo abajo.

========================================================
11. REVISIÓN FINAL OBLIGATORIA
========================================================

Antes de terminar verifica visualmente y en código TODOS estos puntos:

LOGIN:
[ ] Card central.
[ ] Correo con KeyboardType.Email.
[ ] Correo validado realmente.
[ ] Contraseña ocultable.
[ ] Login navega al Home.

HOME:
[ ] Bienvenido, Fabian Ramirez.
[ ] Bienvenida CENTRADA.
[ ] Pregunta CENTRADA.
[ ] Dos Cards.
[ ] Logout abajo.

DIRECTORIO:
[ ] TopAppBar lila.
[ ] Título morado.
[ ] LazyColumn.
[ ] Exactamente 5 alumnos.
[ ] Cards gris/lila.
[ ] Navegación al detalle.

EXPEDIENTE:
[ ] Título Expediente Académico.
[ ] Cabecera con DEGRADADO.
[ ] NO existe cabecera morada sólida.
[ ] Degradado morado -> rosado/gris cálido.
[ ] MISMO degradado que ProfileScreen.
[ ] Avatar circular.
[ ] Fabian Ramirez.
[ ] Ingeniería de Software.
[ ] Card lila/gris.
[ ] Card con borde fino.
[ ] Código 2024-0001.
[ ] NO aparece "(ID: 1)".
[ ] Correo.
[ ] Facultad.
[ ] Biografía.

PROFILE:
[ ] Cabecera con degradado.
[ ] MISMO degradado que DetailScreen.
[ ] Información personal.
[ ] Académico.
[ ] Botón Cerrar Sesión ALINEADO EN LA PARTE INFERIOR.
[ ] Botón utiliza Alignment.BottomCenter.
[ ] Botón NO aparece inmediatamente después de VI Ciclo.

========================================================
12. RESTRICCIONES FINALES
========================================================

NO hagas un rediseño libre.

NO agregues componentes no solicitados.

NO agregues nuevas pantallas aparte de LoginScreen.

NO cambies Fabian Ramirez.

NO agregues fotografías remotas.

NO agregues librerías de imágenes.

NO cambies el package.

NO cambies la arquitectura.

NO reemplaces Navigation Compose.

NO elimines itemId.

NO muestres itemId en la interfaz.

NO alinees la bienvenida a la izquierda.

NO dejes el encabezado del Directorio blanco.

NO dejes la Card del Expediente sin borde.

NO utilices morado sólido en la cabecera de Expediente Académico.

NO utilices degradados diferentes en Expediente y Perfil.

NO coloques Cerrar Sesión de ProfileScreen inmediatamente debajo de los datos académicos.

NO soluciones la posición de Cerrar Sesión mediante offsets o paddings superiores gigantes.

Al finalizar:

1. Revisa todos los imports.
2. Elimina imports no utilizados.
3. Corrige referencias sin resolver.
4. Verifica que Navigation Compose siga funcionando.
5. Verifica que itemId siga funcionando.
6. Verifica que el proyecto compile.
7. No modifiques otras partes del proyecto que no sean necesarias para este diseño.
