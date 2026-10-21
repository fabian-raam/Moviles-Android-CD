package com.ramirez.lavacampus.ui.estudiante

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Sell
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import com.ramirez.lavacampus.data.local.entity.Usuario
import com.ramirez.lavacampus.model.EstadoPedido
import com.ramirez.lavacampus.ui.components.EstadoPedidoChip
import com.ramirez.lavacampus.ui.components.PedidoCard
import com.ramirez.lavacampus.ui.components.StepperEstado
import com.ramirez.lavacampus.viewmodel.InicioViewModel

@Composable
fun InicioScreen(
    usuario: Usuario,
    vm: InicioViewModel,
    onNuevoPedido: () -> Unit,
    onVerPedidos: () -> Unit,
    onVerTarifas: () -> Unit,
    onVerDetalle: (Int) -> Unit
) {
    val pedidoActivo by vm.pedidoActivo.collectAsState()
    val recientes by vm.recientes.collectAsState()

    Scaffold { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Saludo de bienvenida
            item {
                Column {
                    Text(
                        text = "¡Hola, ${usuario.nombre}!",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Código: ${usuario.codigo}",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Tarjeta de Pedido Activo
            item {
                Text(
                    text = "Seguimiento de tu ropa",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (pedidoActivo != null) {
                    val p = pedidoActivo!!.pedido
                    ElevatedCard(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.elevatedCardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Pedido Activo #${p.id}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                                EstadoPedidoChip(estadoString = p.estado)
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            StepperEstado(estadoActualString = p.estado)

                            Spacer(modifier = Modifier.height(12.dp))

                            if (p.estado == EstadoPedido.LISTO.name && !p.casillero.isNullOrEmpty()) {
                                Text(
                                    text = "📦 Recógelo en el casillero ${p.casillero}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            } else {
                                Text(
                                    text = "Entrega estimada: ${p.fechaEntrega}",
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                } else {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "No tienes pedidos activos actualmente",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            // Botones de Acceso Rápido
            item {
                Button(
                    onClick = onNuevoPedido,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    Text(text = "  Nuevo Pedido")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onVerPedidos,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(imageVector = Icons.Default.List, contentDescription = null)
                        Text(text = " Mis Pedidos")
                    }

                    OutlinedButton(
                        onClick = onVerTarifas,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Sell, contentDescription = null)
                        Text(text = " Tarifas")
                    }
                }
            }

            // Historial Reciente
            item {
                Text(
                    text = "Últimos pedidos",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            items(recientes, key = { it.pedido.id }) { item ->
                PedidoCard(
                    pedidoConDetalle = item,
                    onClick = { onVerDetalle(item.pedido.id) }
                )
            }
        }
    }
}