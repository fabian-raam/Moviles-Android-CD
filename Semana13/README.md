# CampusGPS

## 1. Autor

Fabián Ramírez

## 2. Descripción

CampusGPS es una aplicación Android desarrollada con Kotlin, Jetpack Compose y Material 3, siguiendo la arquitectura MVVM + Repository.

Permite obtener la ubicación del dispositivo y mostrarla en Google Maps junto con cinco lugares ficticios del campus: biblioteca, cafetería, laboratorio, auditorio y estacionamiento.

La aplicación calcula las distancias y ordena los lugares del más cercano al más lejano. También permite seleccionar un lugar para centrar el mapa en él y resalta en verde el más cercano.

Incluye manejo de errores por permiso denegado, ubicación desactivada y ausencia de Google Play Services.

## 3. Árbol de paquetes

Ubicación: `app/src/main/java/com/ramirez/campusgps/`

com.ramirez.campusgps/
├── MainActivity.kt
├── model/
│   ├── Posicion.kt
│   ├── Lugar.kt
│   ├── LugarConDistancia.kt
│   ├── UbicacionUi.kt
│   ├── TipoError.kt
│   └── UbicacionNoDisponibleException.kt
├── data/
│   ├── UbicacionRepository.kt
│   ├── UbicacionRepositoryGoogle.kt
│   ├── LugaresRepository.kt
│   └── LugaresRepositoryLocal.kt
├── util/
│   ├── Distancia.kt
│   └── Formato.kt
├── viewmodel/
│   ├── MapaViewModel.kt
│   └── CampusViewModelFactory.kt
└── ui/
    ├── PantallaMapa.kt
    ├── MapaUbicacion.kt
    ├── ListaLugares.kt
    ├── MensajeError.kt
    └── theme/
        ├── Color.kt
        ├── Theme.kt
        └── Type.kt

## 4. Diagrama MVVM + Repository.

PantallaMapa
    │
    │ Envía acciones: buscar ubicación, seleccionar lugar
    ▼
MapaViewModel
    │
    │ Pide datos mediante las interfaces
    ▼
UbicacionRepository          LugaresRepository
    │                           │
    ▼                           ▼
UbicacionRepositoryGoogle    LugaresRepositoryLocal
    │                           │
    ▼                           ▼
Google Play Services        Cinco lugares ficticios


## 5. Capturas

  -<img width="356" height="660" alt="image" src="https://github.com/user-attachments/assets/5b87872f-d874-41f9-9736-a7d7d300ebeb" />

  -<img width="380" height="763" alt="image" src="https://github.com/user-attachments/assets/90d53710-093c-43a3-a29b-3bce813cb166" />

  -<img width="362" height="245" alt="image" src="https://github.com/user-attachments/assets/6d90388d-c505-4773-b499-73e2944c1ce4" />

  -<img width="368" height="747" alt="image" src="https://github.com/user-attachments/assets/16c58bd4-17c5-4200-a6c1-74ed8b68d52e" />



