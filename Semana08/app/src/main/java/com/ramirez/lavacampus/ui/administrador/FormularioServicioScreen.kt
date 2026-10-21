package com.ramirez.lavacampus.ui.administrador

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramirez.lavacampus.data.local.entity.Servicio
import com.ramirez.lavacampus.model.Unidad
import com.ramirez.lavacampus.ui.components.AppTopBar
import com.ramirez.lavacampus.ui.components.LavaButton
import com.ramirez.lavacampus.ui.components.LavaTextField
import com.ramirez.lavacampus.viewmodel.ServiciosViewModel

@Composable
fun FormularioServicioScreen(
    servicioId: Int,
    vm: ServiciosViewModel,
    onVolver: () -> Unit
) {
    val listaServicios by vm.servicios.collectAsState()
    val error by vm.error.collectAsState()

    val servicioEditar = listaServicios.find { it.id == servicioId }

    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var unidad by remember { mutableStateOf(Unidad.KG.name) }
    var precioTexto by remember { mutableStateOf("") }
    var horasEntrega by remember { mutableIntStateOf(24) }
    var activo by remember { mutableStateOf(true) }
    var icono by remember { mutableStateOf("wash") }

    LaunchedEffect(servicioEditar) {
        if (servicioEditar != null) {
            nombre = servicioEditar.nombre
            descripcion = servicioEditar.descripcion
            unidad = servicioEditar.unidad
            precioTexto = servicioEditar.precio.toString()
            horasEntrega = servicioEditar.horasEntrega
            activo = servicioEditar.activo
            icono = servicioEditar.icono
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                titulo = if (servicioId == 0) "Nuevo Servicio" else "Editar Servicio",
                onVolver = onVolver
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            LavaTextField(
                valor = nombre,
                onValorChange = { nombre = it; vm.limpiarError() },
                label = "Nombre del servicio"
            )

            LavaTextField(
                valor = descripcion,
                onValorChange = { descripcion = it },
                label = "Descripción"
            )

            // Selector de Unidad
            Text("Se cobra por:", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(Unidad.KG, Unidad.PRENDA, Unidad.UNIDAD).forEach { u ->
                    FilterChip(
                        selected = unidad == u.name,
                        onClick = { unidad = u.name },
                        label = { Text(u.etiqueta.replaceFirstChar { it.uppercase() }) }
                    )
                }
            }

            LavaTextField(
                valor = precioTexto,
                onValorChange = { precioTexto = it },
                label = "Precio (S/)",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            // Selector de Horas de Entrega
            Text("Tiempo de entrega:", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(24, 48, 72).forEach { h ->
                    FilterChip(
                        selected = horasEntrega == h,
                        onClick = { horasEntrega = h },
                        label = { Text("$h horas") }
                    )
                }
            }

            // Selector de Ícono
            Text("Ícono:", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("wash" to "Lavadora", "iron" to "Plancha", "shirt" to "Camisa").forEach { (ic, label) ->
                    FilterChip(
                        selected = icono == ic,
                        onClick = { icono = ic },
                        label = { Text(label) }
                    )
                }
            }

            // Switch Activo
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Servicio activo (visible para alumnos)")
                    Switch(checked = activo, onCheckedChange = { activo = it })
                }
            }

            if (error != null) {
                Text(text = error!!, color = MaterialTheme.colorScheme.error, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.weight(1f))

            LavaButton(
                texto = "Guardar Servicio",
                onClick = {
                    val p = precioTexto.toDoubleOrNull() ?: 0.0
                    if (nombre.isBlank() || p <= 0.0) {
                        return@LavaButton
                    }

                    val servicioGuardar = Servicio(
                        id = servicioId,
                        nombre = nombre.trim(),
                        descripcion = descripcion.trim(),
                        unidad = unidad,
                        precio = p,
                        horasEntrega = horasEntrega,
                        activo = activo,
                        icono = icono
                    )

                    vm.guardar(servicioGuardar) {
                        onVolver()
                    }
                }
            )

            if (servicioId != 0 && servicioEditar != null) {
                OutlinedButton(
                    onClick = {
                        vm.eliminar(servicioEditar) {
                            onVolver()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Eliminar Servicio", color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}