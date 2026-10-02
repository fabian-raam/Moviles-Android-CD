package com.ramirez.tecstore.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Report
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ramirez.tecstore.model.Producto
import java.util.Locale

/**
 * Composable que dibuja la tarjeta de un producto individual.
 *
 * @param producto Instancia del modelo Producto a mostrar.
 * @param onEliminar Lambda que se ejecuta para solicitar la eliminación de este producto.
 * @param onFavorito Lambda que se ejecuta cuando el producto se marca como favorito.
 */
@Composable
fun TarjetaProducto(
    producto: Producto,
    onEliminar: () -> Unit,
    onFavorito: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    // Estado para controlar la visibilidad del menú
    var expanded by remember { mutableStateOf(false) }

    val importe = producto.precio * producto.cantidad

    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Columna con peso para nombre y detalle
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "S/ ${
                        String.format(
                            Locale.US,
                            "%.2f",
                            producto.precio
                        )
                    } x ${producto.cantidad}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }

            // Texto de importe total del producto
            Text(
                text = "S/ ${
                    String.format(
                        Locale.US,
                        "%.2f",
                        importe
                    )
                }",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF5A419C),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            // Contenedor para el botón y su DropdownMenu
            Box {
                IconButton(
                    onClick = {
                        expanded = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Opciones"
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {
                    // Opción Favoritos
                    DropdownMenuItem(
                        text = {
                            Text("Favoritos")
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.FavoriteBorder,
                                contentDescription = "Favoritos"
                            )
                        },
                        onClick = {
                            expanded = false
                            onFavorito()
                        }
                    )

                    // Opción Compartir
                    DropdownMenuItem(
                        text = {
                            Text("Compartir")
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Compartir"
                            )
                        },
                        onClick = {
                            expanded = false
                        }
                    )

                    // Opción Reportar
                    DropdownMenuItem(
                        text = {
                            Text("Reportar")
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Report,
                                contentDescription = "Reportar"
                            )
                        },
                        onClick = {
                            expanded = false
                        }
                    )

                    HorizontalDivider()

                    // Opción Eliminar
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = "Eliminar",
                                color = MaterialTheme.colorScheme.error
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Eliminar",
                                tint = MaterialTheme.colorScheme.error
                            )
                        },
                        onClick = {
                            expanded = false
                            onEliminar()
                        }
                    )
                }
            }
        }
    }
}