package com.ramirez.campusgps.ui

import androidx.compose.runtime.Composable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberUpdatedMarkerState
import com.ramirez.campusgps.model.LugarConDistancia
import com.ramirez.campusgps.model.Lugar
import com.ramirez.campusgps.model.UbicacionUi
import com.ramirez.campusgps.R

@Composable
fun MapaUbicacion(
    estado: UbicacionUi,
    lugares: List<LugarConDistancia>,
    seleccionado: Int?,
    masCercano: Lugar?,
    radioMetros: Int,
    modifier: Modifier = Modifier
) {
    val camara = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(-12.0464, -77.0428), 12f)
    }
    var mapaCargado by remember { mutableStateOf(false) }
    val colorRadio = MaterialTheme.colorScheme.primary

    // INICIO MEJORA 3 - MAPA EN MODO OSCURO.
    val contexto = LocalContext.current
    // Detecta si Android tiene activado el tema oscuro.
    val temaOscuro = isSystemInDarkTheme()

    // Carga los colores de res/raw/mapa_oscuro.json cuando cambia el tema.
    // null restaura el estilo normal al volver al tema claro.
    val estiloMapa = remember(contexto, temaOscuro) {
        if (temaOscuro) {
            MapStyleOptions.loadRawResourceStyle(contexto, R.raw.mapa_oscuro)
        } else {
            null
        }
    }
    // Prepara las propiedades que se entregan al componente GoogleMap.
    val propiedadesMapa = remember(estiloMapa) {
        MapProperties(mapStyleOptions = estiloMapa)
    }
    // FIN DE LA CONFIGURACION DEL MAPA OSCURO.

    // Espera a que el mapa este listo antes de animar la camara.
    LaunchedEffect(estado, mapaCargado) {
        if (mapaCargado && estado is UbicacionUi.Lista) {
            val posicion = LatLng(estado.posicion.latitud, estado.posicion.longitud)
            camara.animate(CameraUpdateFactory.newLatLngZoom(posicion, 16f))
        }
    }

    LaunchedEffect(seleccionado, mapaCargado) {
        if (mapaCargado) {
            val lugar = lugares.firstOrNull { it.lugar.id == seleccionado }?.lugar
            if (lugar != null) {
                camara.animate(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(lugar.latitud, lugar.longitud),
                        17f
                    )
                )
            }
        }
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = camara,
        properties = propiedadesMapa, // Aplica el estilo preparado para el tema actual.
        onMapLoaded = { mapaCargado = true }
    ) {
        if (estado is UbicacionUi.Lista) {
            // El SDK recibe el radio en metros y dibuja el circulo desde la posicion del usuario.
            Circle(
                center = LatLng(estado.posicion.latitud, estado.posicion.longitud),
                radius = radioMetros.toDouble(),
                strokeColor = colorRadio,
                strokeWidth = 3f,
                fillColor = colorRadio.copy(alpha = 0.15f)
            )
            Marker(
                state = rememberUpdatedMarkerState(
                    position = LatLng(estado.posicion.latitud, estado.posicion.longitud)
                ),
                title = "Estás aquí"
            )
        }

        lugares.forEach { lugarConDistancia ->
            val lugar = lugarConDistancia.lugar
            key(lugar.id) {
                val marcador = rememberUpdatedMarkerState(
                    position = LatLng(lugar.latitud, lugar.longitud)
                )
                Marker(
                    state = marcador,
                    title = lugar.nombre,
                    snippet = lugar.categoria,
                    icon = BitmapDescriptorFactory.defaultMarker(
                        if (lugar.id == masCercano?.id) {
                            BitmapDescriptorFactory.HUE_GREEN
                        } else {
                            BitmapDescriptorFactory.HUE_AZURE
                        }
                    )
                )

                LaunchedEffect(seleccionado) {
                    if (seleccionado == lugar.id) {
                        marcador.showInfoWindow()
                    } else {
                        marcador.hideInfoWindow()
                    }
                }
            }
        }

    }
}
