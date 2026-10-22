package com.ramirez.lavacampus.ui.administrador

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramirez.lavacampus.model.Casilleros
import com.ramirez.lavacampus.model.EstadoPedido
import com.ramirez.lavacampus.ui.components.AppTopBar
import com.ramirez.lavacampus.ui.components.EstadoPedidoChip
import com.ramirez.lavacampus.ui.components.LavaButton
import com.ramirez.lavacampus.ui.components.StepperEstado
import com.ramirez.lavacampus.ui.theme.VioletaContainer
import com.ramirez.lavacampus.ui.theme.VioletaPrimary
import com.ramirez.lavacampus.viewmodel.GestionPedidoViewModel

@Composable
fun GestionPedidoScreen(
    vm: GestionPedidoViewModel,
    onVolver: () -> Unit
) {
    val pedidoConDetalle by vm.pedido.collectAsState()
    val casillerosOcupados by vm.casillerosOcupados.collectAsState()
    val casilleroElegido by vm.casilleroElegido.collectAsState()
    val pagado by vm.pagado.collectAsState()
    val error by vm.error.collectAsState()

    val p = pedidoConDetalle?.pedido
    val u = pedidoConDetalle?.usuario

    val iniciales = if (u != null) {
        u.nombre.split(" ").mapNotNull { it.firstOrNull() }.take(2).joinToString("").uppercase()
    } else "AG"

    Scaffold(
        topBar = {
            AppTopBar(
                titulo = if (p != null) "Gestionar pedido #%03d".format(p.id) else "Gestionar pedido",
                onVolver = onVolver
            )
        }
    ) { padding ->
        if (p != null && u != null) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Tarjeta del Estudiante con Avatar Iniciales (AG)
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    modifier = Modifier.size(48.dp),
                                    shape = CircleShape,
                                    color = VioletaContainer
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = iniciales,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp,
                                            color = VioletaPrimary
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Column {
                                    Text(
                                        text = u.nombre,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = u.codigo,
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            EstadoPedidoChip(estadoString = p.estado)
                        }
                    }
                }

                // Seguimiento Vertical
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            StepperEstado(estadoActualString = p.estado, esVertical = true)
                        }
                    }
                }

                // Selector de Casillero para Recojo
                item {
                    Column {
                        Text(
                            text = "Casillero para el recojo",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(Casilleros.todos) { casillero ->
                                val estaOcupado = casillerosOcupados.contains(casillero) && casillero != p.casillero
                                val esElSeleccionado = (casilleroElegido ?: p.casillero) == casillero

                                FilterChip(
                                    selected = esElSeleccionado,
                                    onClick = {
                                        if (!estaOcupado) vm.elegirCasillero(casillero)
                                    },
                                    enabled = !estaOcupado,
                                    label = { Text(casillero, fontWeight = FontWeight.Bold) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = VioletaPrimary,
                                        selectedLabelColor = Color.White,
                                        disabledContainerColor = Color(0xFFF1F5F9)
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Los casilleros en gris ya están ocupados",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Tarjeta de Cobro
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = "Total S/ %.2f".format(p.total),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                                if (p.descuento > 0.0) {
                                    Text(
                                        text = "Incluye desc. volumen -S/ %.2f".format(p.descuento),
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF16A34A)
                                    )
                                }
                                Text(
                                    text = "Se cobra al entregar",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Cobrado ",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Switch(
                                    checked = p.pagado || pagado,
                                    onCheckedChange = { vm.cambiarPagado(it) }
                                )
                            }
                        }
                    }
                }

                // Banner Informativo de Reglas (Violeta Claro)
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F3FF))
                    ) {
                        Text(
                            text = "Regla: no se entrega un pedido sin cobrar, ni se pasa a Listo sin casillero.",
                            modifier = Modifier.padding(16.dp),
                            fontSize = 12.sp,
                            color = VioletaPrimary
                        )
                    }
                }

                if (error != null) {
                    item {
                        Text(
                            text = error!!,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 13.sp
                        )
                    }
                }

                // Botones de Acción (Bloqueados si no se cumplen las condiciones)
                item {
                    val estadoActualEnum = try {
                        EstadoPedido.valueOf(p.estado)
                    } catch (e: Exception) {
                        EstadoPedido.RECIBIDO
                    }

                    val siguiente = estadoActualEnum.siguiente()

                    val puedeAvanzar = when (siguiente) {
                        EstadoPedido.LISTO -> !casilleroElegido.isNullOrEmpty() || !p.casillero.isNullOrEmpty()
                        EstadoPedido.ENTREGADO -> p.pagado || pagado
                        else -> true
                    }

                    if (siguiente != null) {
                        LavaButton(
                            texto = "Marcar como ${siguiente.titulo}",
                            enabled = puedeAvanzar, // BLOQUEADO SI NO CUMPLE LA REGLA
                            containerColor = if (puedeAvanzar) VioletaPrimary else Color.LightGray,
                            onClick = {
                                vm.avanzarEstado {
                                    onVolver()
                                }
                            }
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    OutlinedButton(
                        onClick = {
                            vm.cancelarPedido {
                                onVolver()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Cancelar pedido", color = Color.Red, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
