# CentroTareas

## Descripción

Aplicación Android desarrollada en Kotlin con Jetpack Compose y Material 3
para practicar el uso de corrutinas sin bloquear el hilo principal.

Permite ejecutar una tarea con progreso y cancelación, contar números
primos en segundo plano y comparar cargas simuladas secuenciales y
paralelas. También incluye simulación de errores y reintento.

La aplicación utiliza arquitectura MVVM + Repository, StateFlow para
exponer los estados y viewModelScope para gestionar las corrutinas.
Todos los datos son ficticios y no requiere conexión a internet.

## Estructura de paquetes

com.ramirez.centrotareas/
├── MainActivity.kt
├── model/
│   └── Estados.kt
├── data/
│   ├── TareasRepository.kt
│   └── TareasRepositoryLocal.kt
├── viewmodel/
│   ├── TareasViewModel.kt
│   └── TareasViewModelFactory.kt
└── ui/
    ├── PantallaTareas.kt
    └── theme/
        ├── Color.kt
        ├── Theme.kt
        └── Type.kt

## Diagrama MVVM + Repository
    A[MainActivity] -->|Crea mediante Factory| B[TareasViewModel]
    A -->|Muestra| C[PantallaTareas]
    C -->|Eventos de botones| B
    B -->|Estados mediante StateFlow| C
    B -->|Llama funciones suspend| D[TareasRepository]
    E[TareasRepositoryLocal] -.->|Implementa| D
    E --> F[Cálculo de primos en Dispatchers.Default]
    E --> G[Cargas simuladas con delay]


## Capturas 

<img width="383" height="362" alt="image" src="https://github.com/user-attachments/assets/9d9645bc-a2a3-4bef-afe7-3e15ea5d4124" />
<img width="362" height="350" alt="image" src="https://github.com/user-attachments/assets/eba19cc9-f152-4765-92a3-ed734ab79224" />
<img width="361" height="236" alt="image" src="https://github.com/user-attachments/assets/ec125702-90ee-4c8b-9da1-799e6435122e" />
<img width="347" height="275" alt="image" src="https://github.com/user-attachments/assets/ac278668-4296-46c0-9c5f-185947be866c" />
<img width="325" height="246" alt="image" src="https://github.com/user-attachments/assets/98f15fb2-b572-40b2-8931-81b28670c14d" />
<img width="328" height="257" alt="image" src="https://github.com/user-attachments/assets/e49336cc-2cd1-4f92-be5f-3ad4506f6693" />

