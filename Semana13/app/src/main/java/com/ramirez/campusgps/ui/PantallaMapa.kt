package com.ramirez.campusgps.ui

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.ramirez.campusgps.model.UbicacionUi
import com.ramirez.campusgps.R
import com.ramirez.campusgps.ui.theme.Negro
import com.ramirez.campusgps.util.formatearDistancia
import com.ramirez.campusgps.viewmodel.MapaViewModel

@Composable
fun PantallaMapa(
    vm: MapaViewModel,
    modifier: Modifier = Modifier
) {
    val estado by vm.estado.collectAsState()
    val lugares by vm.lugares.collectAsState()
    val seleccionado by vm.seleccionado.collectAsState()
    val masCercano by vm.masCercano.collectAsState()
    val categoriaSeleccionada by vm.categoriaSeleccionada.collectAsState()
    val radioMetros by vm.radioMetros.collectAsState()
    val lugaresDentroDelRadio by vm.lugaresDentroDelRadio.collectAsState()
    val contexto = LocalContext.current

    // Android muestra el dialogo; la pantalla comunica el resultado al ViewModel.
    val lanzador = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { resultado ->
        if (resultado[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
            resultado[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        ) {
            vm.buscar()
        } else {
            vm.permisoDenegado()
        }
    }

    fun alPulsar() {
        val tienePermisoPreciso = ContextCompat.checkSelfPermission(
            contexto,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val tienePermisoAproximado = ContextCompat.checkSelfPermission(
            contexto,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (tienePermisoPreciso || tienePermisoAproximado) {
            vm.buscar()
        } else {
            lanzador.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    val colores = MaterialTheme.colorScheme
    val buscando = estado is UbicacionUi.Buscando
    val tienePosicion = estado is UbicacionUi.Lista
    val formaBoton = RoundedCornerShape(14.dp)
    val degradadoAzul = Brush.horizontalGradient(
        listOf(colores.primary, lerp(colores.primary, Color.White, 0.15f))
    )
    val distanciaMasCercano = lugares.firstOrNull { it.lugar.id == masCercano?.id }?.metros

    // DISENO INTEGRADO: mapa superior con filtros flotantes y panel inferior.
    Scaffold(
        modifier = modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.safeDrawing,
        containerColor = colores.surface
    ) { espacioInterior ->
        BoxWithConstraints(Modifier.fillMaxSize().padding(espacioInterior)) {
            // El mapa se extiende bajo las esquinas redondeadas del panel.
            MapaUbicacion(
                estado = estado,
                lugares = lugares,
                seleccionado = seleccionado,
                masCercano = masCercano,
                radioMetros = radioMetros,
                modifier = Modifier.fillMaxWidth().height(maxHeight * 0.36f + 24.dp)
            )

            FiltroCategorias(
                categorias = vm.categorias,
                seleccionada = categoriaSeleccionada,
                onSeleccionar = { vm.filtrarPorCategoria(it) },
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(maxHeight * 0.64f),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                color = colores.surface,
                shadowElevation = 6.dp
            ) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 8.dp, bottom = 4.dp)
                            .width(36.dp)
                            .height(4.dp)
                            .background(colores.outlineVariant, RoundedCornerShape(2.dp))
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            modifier = Modifier.size(36.dp),
                            shape = RoundedCornerShape(11.dp),
                            color = Negro,
                            contentColor = colores.primary
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    painterResource(R.drawable.ic_ubicacion),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Text(
                            text = "Cerca de ti",
                            modifier = Modifier.weight(1f),
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        SelectorRadio(
                            radios = vm.radiosDisponibles,
                            seleccionado = radioMetros,
                            onSeleccionar = { vm.seleccionarRadio(it) }
                        )
                    }

                    ListaLugares(
                        lugares = lugares,
                        seleccionado = seleccionado,
                        onClick = { vm.seleccionar(it) },
                        lugaresDentroDelRadio = lugaresDentroDelRadio,
                        modifier = Modifier.fillMaxWidth().weight(1f)
                    )

                    // El pie cambia segun el estado y conserva las acciones de permisos.
                    when (val actual = estado) {
                        UbicacionUi.Inicial -> Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painterResource(R.drawable.ic_ubicacion),
                                contentDescription = null,
                                tint = colores.onSurfaceVariant,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                "Pulsa el botón para obtener tu ubicación",
                                style = MaterialTheme.typography.bodySmall,
                                color = colores.onSurfaceVariant,
                                modifier = Modifier.weight(1f, fill = false)
                            )
                        }
                        UbicacionUi.Buscando -> Row(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CircularProgressIndicator(modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
                            Text("Buscando ubicación…", style = MaterialTheme.typography.bodyMedium)
                        }
                        is UbicacionUi.Lista -> Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            listOf(
                                "Latitud" to actual.posicion.latitud,
                                "Longitud" to actual.posicion.longitud
                            ).forEach { (etiqueta, valor) ->
                                Surface(
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(10.dp),
                                    color = colores.surfaceVariant
                                ) {
                                    Column(Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                                        Text(etiqueta, style = MaterialTheme.typography.labelSmall,
                                            color = colores.onSurfaceVariant)
                                        Text(valor.toString(), style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.SemiBold)
                                    }
                                }
                            }
                        }
                        is UbicacionUi.Error -> MensajeError(
                            error = actual,
                            onAbrirAjustes = {
                                contexto.startActivity(
                                    Intent(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                        Uri.fromParts("package", contexto.packageName, null)
                                    )
                                )
                            },
                            onReintentar = { alPulsar() }
                        )
                    }

                    Button(
                        onClick = { alPulsar() },
                        enabled = !buscando,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 48.dp)
                            .shadow(if (!tienePosicion && !buscando) 4.dp else 0.dp, formaBoton)
                            .background(
                                if (!tienePosicion && !buscando) degradadoAzul else Brush.horizontalGradient(
                                    listOf(colores.primaryContainer, colores.primaryContainer)
                                ),
                                formaBoton
                            ),
                        shape = formaBoton,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = if (tienePosicion) colores.primary else colores.onPrimary,
                            disabledContainerColor = colores.surfaceVariant,
                            disabledContentColor = colores.onSurfaceVariant
                        )
                    ) {
                        Icon(painterResource(R.drawable.ic_navegar), contentDescription = null,
                            modifier = Modifier.size(18.dp))
                        Text("Obtener mi ubicación", modifier = Modifier.padding(start = 8.dp),
                            style = MaterialTheme.typography.labelLarge)
                    }

                    Button(
                        onClick = { masCercano?.let { vm.seleccionar(it.id) } },
                        enabled = masCercano != null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 48.dp)
                            .shadow(if (masCercano != null) 4.dp else 0.dp, formaBoton)
                            .background(degradadoAzul, formaBoton),
                        shape = formaBoton,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = colores.onPrimary,
                            disabledContainerColor = colores.surfaceVariant,
                            disabledContentColor = colores.onSurfaceVariant
                        )
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Ir al más cercano", style = MaterialTheme.typography.labelLarge)
                            val lugar = masCercano
                            val metros = distanciaMasCercano
                            if (lugar != null && metros != null) {
                                Text(
                                    "Más cercano: ${lugar.nombre} (${formatearDistancia(metros)})",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontSize = 11.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                    Box(Modifier.height(4.dp))
                }
            }
        }
    }
}
