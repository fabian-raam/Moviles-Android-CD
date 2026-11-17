package com.ramirez.campusgps.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberUpdatedMarkerState
import com.ramirez.campusgps.model.LugarConDistancia
import com.ramirez.campusgps.model.UbicacionUi

@Composable
fun MapaUbicacion(
    estado: UbicacionUi,
    lugares: List<LugarConDistancia>,
    seleccionado: Int?,
    modifier: Modifier = Modifier
) {
    val camara = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(-12.0464, -77.0428), 12f)
    }
    var mapaCargado by remember { mutableStateOf(false) }

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
        onMapLoaded = { mapaCargado = true }
    ) {
        if (estado is UbicacionUi.Lista) {
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
                        BitmapDescriptorFactory.HUE_AZURE
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
