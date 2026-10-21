package com.ramirez.lavacampus.ui.administrador

import androidx.compose.foundation.clickable
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
import androidx.compose.material3.FilterChip
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
import com.ramirez.lavacampus.ui.components.AppTopBar
import com.ramirez.lavacampus.ui.components.EstadoPedidoChip
import com.ramirez.lavacampus.ui.components.LavaTextField
import com.ramirez.lavacampus.viewmodel.BandejaViewModel

@Composable
fun BandejaScreen(
    vm: BandejaViewModel,
    onGestionarPedido: (Int) -> Unit
) {
    val pedidos by vm.pedidos.collectAsState()
    val filtroEstado by vm.filtroEstado.collectAsState()
    val busqueda by vm.busqueda.collectAsState()

    val opcionesFiltro = listOf("TODOS", "RECIBIDO", "EN_PROCESO", "LISTO", "ENTREGADO", "CANCELADO")

    Scaffold(
        topBar = { AppTopBar(titulo = "Bandeja de Pedidos") }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Buscador por estudiante o id
            LavaTextField(
                valor = busqueda,
                onValorChange = { vm.cambiarBusqueda(it) },
                label = "Buscar por estudiante o # pedido"
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Chips de Filtro por Estado
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(opcionesFiltro) { filtro ->
                    FilterChip(
                        selected = filtroEstado == filtro,
                        onClick = { vm.cambiarFiltro(filtro) },
                        label = { Text(filtro) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de Pedidos
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(pedidos, key = { it.pedido.id }) { item ->
                    val p = item.pedido
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onGestionarPedido(p.id) },
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "${item.usuario.nombre} (${item.usuario.codigo})",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                                EstadoPedidoChip(estadoString = p.estado)
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "Pedido #${p.id} | ${p.fechaRegistro} | Total: S/ %.2f".format(p.total),
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            if (!p.casillero.isNullOrEmpty()) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Casillero: ${p.casillero}",
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}