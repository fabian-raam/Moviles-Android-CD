package com.ramirez.lavacampus.ui.administrador

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramirez.lavacampus.model.Casilleros
import com.ramirez.lavacampus.model.EstadoPedido
import com.ramirez.lavacampus.ui.components.AppTopBar
import com.ramirez.lavacampus.ui.components.EstadoPedidoChip
import com.ramirez.lavacampus.ui.components.LavaButton
import com.ramirez.lavacampus.ui.components.StepperEstado
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

    Scaffold(
        topBar = { AppTopBar(titulo = "Gestión del Pedido", onVolver = onVolver) }
    ) { padding ->
        val p = pedidoConDetalle?.pedido
        val u = pedidoConDetalle?.usuario

        if (p != null && u != null) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Datos del Cliente
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = u.nombre,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                EstadoPedidoChip(estadoString = p.estado)
                            }
                            Text(
                                text = "Código: ${u.codigo} | Correo: ${u.correo}",
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                // Seguimiento de estado
                item {
                    StepperEstado(estadoActualString = p.estado)
                }

                // Selector de Casilleros (C-01 a C-30)
                item {
                    Text(
                        text = "Asignar Casillero (para estado LISTO)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(Casilleros.todos) { casillero ->
                            val estaOcupado = casillerosOcupados.contains(casillero) && casillero != p.casillero
                            val esElSeleccionado = (casilleroElegido ?: p.casillero) == casillero

                            FilterChip(
                                selected = esElSeleccionado,
                                onClick = {
                                    if (!estaOcupado) vm.elegirCasillero(casillero)
                                },
                                enabled = !estaOcupado,
                                label = { Text(casillero) }
                            )
                        }
                    }
                }

                // Cobro del Pedido
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
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
                                    text = "Monto Total: S/ %.2f".format(p.total),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = if (p.pagado || pagado) "Cobrado" else "Pendiente de pago",
                                    fontSize = 13.sp
                                )
                            }
                            Switch(
                                checked = p.pagado || pagado,
                                onCheckedChange = { vm.cambiarPagado(it) }
                            )
                        }
                    }
                }

                if (error != null) {
                    item {
                        Text(
                            text = error!!,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 14.sp
                        )
                    }
                }

                // Botones de Acción
                item {
                    val estadoActualEnum = try {
                        EstadoPedido.valueOf(p.estado)
                    } catch (e: Exception) {
                        EstadoPedido.RECIBIDO
                    }

                    val siguiente = estadoActualEnum.siguiente()

                    if (siguiente != null) {
                        LavaButton(
                            texto = "Avanzar a: ${siguiente.titulo}",
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
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Cancelar Pedido", color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }
}