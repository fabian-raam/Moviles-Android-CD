package com.ramirez.lavacampus.ui.estudiante

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramirez.lavacampus.model.Unidad
import com.ramirez.lavacampus.ui.components.IconoServicio
import com.ramirez.lavacampus.ui.components.LavaButton
import com.ramirez.lavacampus.ui.components.LavaTextField
import com.ramirez.lavacampus.ui.components.SelectorCantidad
import com.ramirez.lavacampus.viewmodel.NuevoPedidoViewModel

@Composable
fun NuevoPedidoScreen(
    vm: NuevoPedidoViewModel,
    onPedidoConfirmado: () -> Unit
) {
    val servicios by vm.serviciosActivos.collectAsState()
    val cantidades by vm.cantidades.collectAsState()
    val nota by vm.nota.collectAsState()
    val total by vm.total.collectAsState()
    val fechaEntrega by vm.fechaEntrega.collectAsState()
    val error by vm.error.collectAsState()

    Scaffold { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Nuevo Pedido",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Elige los servicios y cantidades que necesitas",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Lista de Servicios con Selector de Cantidad
            items(servicios, key = { it.id }) { servicio ->
                val cantidadActual = cantidades[servicio.id] ?: 0.0

                val (paso, minimo, maximo, textoUnidad) = when (servicio.unidad) {
                    Unidad.KG.name -> Tuple4(0.5, 1.0, 10.0, "por kg")
                    Unidad.PRENDA.name -> Tuple4(1.0, 1.0, 30.0, "por prenda")
                    else -> Tuple4(1.0, 1.0, 30.0, "por unidad")
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            IconoServicio(icono = servicio.icono)
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = servicio.nombre,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                                Text(
                                    text = "S/ %.2f %s".format(servicio.precio, textoUnidad),
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }

                        SelectorCantidad(
                            cantidad = cantidadActual,
                            paso = paso,
                            minimo = minimo,
                            maximo = maximo,
                            onCantidadChange = { nueva ->
                                vm.cambiarCantidad(servicio.id, nueva)
                            }
                        )
                    }
                }
            }

            // Resumen del Pedido (Total, Fecha estimada y Nota)
            item {
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Total a pagar:",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            Text(
                                text = "S/ %.2f".format(total),
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "📅 Fecha estimada de entrega: $fechaEntrega",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        LavaTextField(
                            valor = nota,
                            onValorChange = { vm.cambiarNota(it) },
                            label = "Nota adicional (ej. cuidado ropa delicada)"
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

            item {
                LavaButton(
                    texto = "Confirmar Pedido",
                    enabled = total > 0.0,
                    onClick = {
                        vm.confirmarPedido {
                            onPedidoConfirmado()
                        }
                    }
                )
            }
        }
    }
}

private data class Tuple4<A, B, C, D>(val a: A, val b: B, val c: C, val d: D)