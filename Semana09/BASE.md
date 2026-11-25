# Base de MuroCampus - Semana 09

Preparación previa a los pasos del PDF, creada porque no se entregó el proyecto base.
El paquete de este proyecto es `com.ramirez.murocampus`.

## Tres commits de preparación

1. Proyecto Android con Compose, tema azul y pantalla inicial vacía con barra MuroCampus.
2. Componentes reutilizables, ejemplo de Usuario (modelo, API, cliente, Repository, ViewModel y vista), UiState y mensajes de error.
3. Factory de ViewModel y esqueletos con TODO para el laboratorio.

## Qué viene preparado

- `MainActivity.kt`: pantalla inicial; posteriormente coordinará la navegación.
- `ui/Componentes.kt`: TarjetaPost(userId, titulo, texto, onClick), Cargando y ErrorConReintento.
- `model/Modelos.kt`: Usuario resuelto; modelos de publicaciones pendientes.
- `data/remote/UsuarioApi.kt` y `UsuarioRemote.kt`: ejemplo real con Retrofit.
- `data/UsuarioRepository.kt`: devuelve Result y respeta la cancelación.
- `viewmodel/UsuariosViewModel.kt`, `UiState.kt` y `ui/EjemploUsuarios.kt`: ejemplo de estados observable.
- `data/Errores.kt`: traduce fallas de red a mensajes sin importar Retrofit en el ViewModel.
- `viewmodel/PostsViewModelFactory.kt`: factory que recibe el tipo y una función de creación, sin depender de clases todavía inexistentes.
- `ui/theme/`: tema y tipografía.

El ejemplo de usuarios no se muestra en el inicio para mantener la pantalla vacía solicitada.
Para probarlo temporalmente, en MainActivity se crea la factory en la raíz de composición:

```kotlin
val factory = remember {
    PostsViewModelFactory(UsuariosViewModel::class.java) {
        UsuariosViewModel(UsuarioRepositoryRemote(UsuarioRemote.api))
    }
}
val vm: UsuariosViewModel = viewModel(factory = factory)
EjemploUsuarios(vm)
```

Se requieren los imports de `remember`, `androidx.lifecycle.viewmodel.compose.viewModel`
y las clases del ejemplo. La pantalla recibe solo el ViewModel; no crea clientes de red.

## Pendiente para los pasos del PDF

- Paso 1: revisar permiso de internet y dependencias, sincronizar y comprobar conectividad.
- Paso 2: Post, Comentario y NuevoPost en Modelos.kt.
- Pasos 3-4: PostApi y completar RetrofitClient.
- Paso 5: PostRepository y su implementación remota.
- Pasos 6-9: PostsViewModel y pantallas de lista, detalle, filtro y creación.
- Paso 10: pruebas manuales, capturas y README del laboratorio.

Los archivos [A] contienen únicamente el paquete y comentarios: aún no implementan esas funciones.
Los archivos [G] contienen el punto de partida y los TODO.

## Ajuste necesario respecto a la guía

La guía solicita un ejemplo de Usuario ya conectado a Retrofit, pero añade sus dependencias recién en el paso 1.
Esta base incorpora el permiso INTERNET, plugin de serialización y dependencias necesarias para que ese ejemplo compile.
El paso 1 servirá para revisarlos y comprobarlos. Las versiones de Android y Compose se conservaron de la plantilla local.
La factory usa una función de creación para poder compilar antes de que exista PostsViewModel.

## Compilar

Abrir esta carpeta en Android Studio, sincronizar Gradle y ejecutar `app`.
También se puede ejecutar `gradlew.bat :app:assembleDebug` con un JDK compatible.
`local.properties`, cachés y configuración local del IDE quedan fuera de Git.

La base no implementa las funciones de publicaciones ni las mejoras de la fase 2.
