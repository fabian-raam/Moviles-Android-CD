package com.ramirez.lavacampus.ui.administrador

import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalLaundryService
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import com.ramirez.lavacampus.model.EstadoPedido
import com.ramirez.lavacampus.ui.components.AppTopBar
import com.ramirez.lavacampus.ui.components.EstadoPedidoChip
import com.ramirez.lavacampus.ui.theme.VioletaPrimary
import com.ramirez.lavacampus.viewmodel.BandejaViewModel

@Composable
fun BandejaScreen(
    vm: BandejaViewModel,
    onGestionarPedido: (Int) -> Unit
) {
    val pedidos by vm.pedidos.collectAsState()
    val filtroEstado by vm.filtroEstado.collectAsState()
    val busqueda by vm.busqueda.collectAsState()

    val opcionesFiltro = listOf("TODOS", "RECIBIDO", "EN_PROCESO", "LISTO")

    Scaffold(
        topBar = { AppTopBar(titulo = "Pedidos") }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Buscador con icono
            OutlinedTextField(
                value = busqueda,
                onValueChange = { vm.cambiarBusqueda(it) },
                placeholder = { Text("Buscar por estudiante o número") },
                leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Buscar", tint = Color.Gray) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Chips de Filtro por Estado
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(opcionesFiltro) { filtro ->
                    val esSeleccionado = filtroEstado == filtro
                    val etiqueta = when (filtro) {
                        "TODOS" -> "Todos"
                        "RECIBIDO" -> "Recibido"
                        "EN_PROCESO" -> "En proceso"
                        "LISTO" -> "Listo"
                        else -> filtro
                    }

                    FilterChip(
                        selected = esSeleccionado,
                        onClick = { vm.cambiarFiltro(filtro) },
                        label = { Text(etiqueta) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = VioletaPrimary,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de Tarjetas de Pedido
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(pedidos, key = { it.pedido.id }) { item ->
                    val p = item.pedido
                    val estadoEnum = try { EstadoPedido.valueOf(p.estado) } catch (e: Exception) { EstadoPedido.RECIBIDO }

                    val (colorIconoBg, colorIconoText) = when (estadoEnum) {
                        EstadoPedido.RECIBIDO -> Color(0xFFFEF3C7) to Color(0xFFD97706)
                        EstadoPedido.EN_PROCESO -> Color(0xFFDBEAFE) to Color(0xFF2563EB)
                        EstadoPedido.LISTO -> Color(0xFFDCFCE7) to Color(0xFF16A34A)
                        else -> Color(0xFFF1F5F9) to Color(0xFF475569)
                    }

                    val botonTexto = when (estadoEnum) {
                        EstadoPedido.RECIBIDO -> "Iniciar"
                        EstadoPedido.EN_PROCESO -> "Marcar listo"
                        EstadoPedido.LISTO -> "Entregar"
                        else -> "Ver"
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onGestionarPedido(p.id) },
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
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Surface(
                                    modifier = Modifier.size(48.dp),
                                    shape = RoundedCornerShape(16.dp),
                                    color = colorIconoBg
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = Icons.Default.LocalLaundryService,
                                            contentDescription = null,
                                            tint = colorIconoText,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Column {
                                    Text(
                                        text = "Pedido #%03d".format(p.id),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp
                                    )

                                    Text(
                                        text = "${item.usuario.nombre} · ${item.detalles.sumOf { it.detalle.cantidad }} kg",
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )

                                    Spacer(modifier = Modifier.height(4.dp))
                                    EstadoPedidoChip(estadoString = p.estado)
                                }
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = "S/ %.2f".format(p.total),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = VioletaPrimary
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                Button(
                                    onClick = { onGestionarPedido(p.id) },
                                    colors = ButtonDefaults.buttonColors(containerColor = VioletaPrimary),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Text(botonTexto, fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
