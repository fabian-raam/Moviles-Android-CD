package com.ramirez.tecstore.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ramirez.tecstore.model.Producto
import kotlinx.coroutines.launch
import java.util.Locale

// Color violeta/púrpura representativo de TECSUP
val PurpleTecsup = Color(0xFF5A419C)

/**
 * Composable principal de la pantalla del carrito de compras con navegación real.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaCarrito() {
    // Estado del NavigationDrawer y coroutine scope
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Estado para la sección/pantalla activa mediante la navegación del drawer
    var seccionActual by remember { mutableStateOf("Carrito") }

    // Lista observable para los productos
    val productos = remember { mutableStateListOf<Producto>() }

    // Estados para los campos del formulario
    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }

    // Estado para el producto seleccionado a eliminar
    var productoAEliminar by remember { mutableStateOf<Producto?>(null) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = "Menú de Navegación",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                HorizontalDivider()
                Spacer(modifier = Modifier.height(8.dp))

                NavigationDrawerItem(
                    label = { Text("Inicio") },
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    selected = (seccionActual == "Inicio"),
                    onClick = {
                        seccionActual = "Inicio"
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                NavigationDrawerItem(
                    label = { Text("Carrito de Compras") },
                    icon = { Icon(Icons.Default.ShoppingCart, contentDescription = null) },
                    selected = (seccionActual == "Carrito"),
                    onClick = {
                        seccionActual = "Carrito"
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                NavigationDrawerItem(
                    label = { Text("Configuración") },
                    icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                    selected = (seccionActual == "Configuracion"),
                    onClick = {
                        seccionActual = "Configuracion"
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = when (seccionActual) {
                                "Inicio" -> "Inicio - TECSUP Store"
                                "Configuracion" -> "Configuración"
                                else -> "Mi Carrito TECSUP"
                            },
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Abrir menú",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = PurpleTecsup
                    )
                )
            }
        ) { innerPadding ->
            // NAVEGACIÓN REAL: Cambia la vista según la opción elegida en el drawer
            when (seccionActual) {
                "Inicio" -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "¡Bienvenido a TECSUP Store!",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = PurpleTecsup
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Usa el menú lateral para gestionar tu Carrito de Compras.",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Gray
                            )
                        }
                    }
                }

                "Configuracion" -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Pantalla de Configuración",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Opciones generales de la aplicación.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )
                        }
                    }
                }

                else -> { // "Carrito"
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(16.dp)
                    ) {
                        // --- FORMULARIO COMPACTO (Etapa 2) ---
                        OutlinedTextField(
                            value = nombre,
                            onValueChange = { nombre = it },
                            label = { Text("Nombre del producto") },
                            placeholder = { Text("Nombre del producto") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = precio,
                                onValueChange = { precio = it },
                                label = { Text("Precio (S/)") },
                                placeholder = { Text("Precio (S/)") },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                modifier = Modifier.weight(1f)
                            )

                            OutlinedTextField(
                                value = cantidad,
                                onValueChange = { cantidad = it },
                                label = { Text("Cantidad") },
                                placeholder = { Text("Cantidad") },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = {
                                val precioNum = precio.toDoubleOrNull() ?: 0.0
                                val cantidadNum = cantidad.toIntOrNull() ?: 0
                                if (nombre.isNotBlank() && precioNum > 0 && cantidadNum > 0) {
                                    productos.add(Producto(nombre.trim(), precioNum, cantidadNum))
                                    nombre = ""
                                    precio = ""
                                    cantidad = ""
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = PurpleTecsup),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "AGREGAR",
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // --- CONTENIDO PRINCIPAL: ESTADO VACÍO O LAZYCOLUMN (Etapa 3 y 4) ---
                        if (productos.isEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Tu carrito está vacío",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Gray
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "Agrega tu primer producto",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.Gray
                                    )
                                }
                            }
                        } else {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                contentPadding = PaddingValues(vertical = 4.dp)
                            ) {
                                items(productos) { producto ->
                                    TarjetaProducto(
                                        producto = producto,
                                        onEliminar = { productoAEliminar = producto }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // --- PANEL DE TOTALES (Etapa 4 + Reto Opcional Descuento) ---
                        val subtotal = productos.sumOf { it.precio * it.cantidad }
                        val igv = subtotal * 0.18
                        val totalSinDescuento = subtotal + igv

                        val porcentajeDescuento = when {
                            totalSinDescuento > 5000 -> 0.10
                            totalSinDescuento > 3000 -> 0.05
                            else -> 0.0
                        }
                        val montoDescuento = totalSinDescuento * porcentajeDescuento
                        val totalFinal = totalSinDescuento - montoDescuento

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F2F9)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = "Productos: ${productos.size}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Gray
                                )

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = "Subtotal", style = MaterialTheme.typography.bodyMedium)
                                    Text(
                                        text = "S/ ${String.format(Locale.US, "%.2f", subtotal)}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = "IGV (18%)", style = MaterialTheme.typography.bodyMedium)
                                    Text(
                                        text = "S/ ${String.format(Locale.US, "%.2f", igv)}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }

                                if (porcentajeDescuento > 0) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = "Descuento (${(porcentajeDescuento * 100).toInt()}%)",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = Color(0xFF2E7D32)
                                        )
                                        Text(
                                            text = "- S/ ${String.format(Locale.US, "%.2f", montoDescuento)}",
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.SemiBold,
                                            color = Color(0xFF2E7D32)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(4.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "TOTAL",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "S/ ${String.format(Locale.US, "%.2f", totalFinal)}",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = PurpleTecsup
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // --- DIÁLOGO DE CONFIRMACIÓN DE BORRADO ---
            if (productoAEliminar != null) {
                AlertDialog(
                    onDismissRequest = { productoAEliminar = null },
                    title = { Text("¿Eliminar este producto?") },
                    text = { Text("¿Deseas quitar '${productoAEliminar?.nombre}' del carrito?") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                productoAEliminar?.let { productos.remove(it) }
                                productoAEliminar = null
                            }
                        ) {
                            Text("Eliminar", color = MaterialTheme.colorScheme.error)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { productoAEliminar = null }) {
                            Text("Cancelar")
                        }
                    }
                )
            }
        }
    }
}
