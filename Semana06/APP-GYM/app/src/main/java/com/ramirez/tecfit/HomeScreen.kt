package com.ramirez.tecfit

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

private val TecsupGreen = Color(0xFF2E7D32)
private val TecsupDarkGreen = Color(0xFF1B5E20)
private val TecsupLightGreen = Color(0xFFE8F5E9)
private val BackgroundColor = Color(0xFFF7F8F7)

@Composable
fun HomeScreen(
    onClassClick: (String) -> Unit = {}
) {

    val sampleClasses = listOf(
        GymClass(
            name = "Yoga funcional",
            schedule = "7:00 am",
            room = "Sala 2"
        ),
        GymClass(
            name = "Cross Training",
            schedule = "6:00 pm",
            room = "Sala 1"
        ),
        GymClass(
            name = "Spinning",
            schedule = "7:30 pm",
            room = "Sala 3"
        )
    )

    val filters = listOf(
        "Hoy",
        "Esta semana"
    )

    var selectedFilter by remember {
        mutableStateOf("Hoy")
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {

        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = TecsupGreen,
                shape = RoundedCornerShape(
                    bottomStart = 28.dp,
                    bottomEnd = 28.dp
                )
            ) {
                Column(
                    modifier = Modifier.padding(
                        start = 20.dp,
                        end = 20.dp,
                        top = 24.dp,
                        bottom = 26.dp
                    )
                ) {

                    Text(
                        text = "TECSUP Fit",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(
                        modifier = Modifier.height(6.dp)
                    )

                    Text(
                        text = "Hola, Fabian Ramirez",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White.copy(alpha = 0.85f)
                    )

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    Text(
                        text = "¿Listo para entrenar hoy?",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }
        }

        item {
            Column(
                modifier = Modifier.padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 22.dp
                )
            ) {

                Text(
                    text = "Explorar clases",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF202020)
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    items(filters) { filter ->

                        FilterChip(
                            selected = selectedFilter == filter,
                            onClick = {
                                selectedFilter = filter
                            },
                            label = {
                                Text(
                                    text = filter,
                                    fontWeight =
                                        if (selectedFilter == filter) {
                                            FontWeight.SemiBold
                                        } else {
                                            FontWeight.Normal
                                        }
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                containerColor = Color.White,
                                labelColor = Color(0xFF555555),
                                selectedContainerColor = TecsupLightGreen,
                                selectedLabelColor = TecsupDarkGreen
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = selectedFilter == filter,
                                borderColor = Color(0xFFD0D0D0),
                                selectedBorderColor = TecsupGreen
                            )
                        )
                    }
                }
            }
        }

        item {
            Text(
                text = "Clases disponibles",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF202020),
                modifier = Modifier.padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 18.dp,
                    bottom = 10.dp
                )
            )
        }

        items(sampleClasses) { gymClass ->

            Box(
                modifier = Modifier.padding(
                    horizontal = 20.dp,
                    vertical = 6.dp
                )
            ) {

                ClassCard(
                    gymClass = gymClass,
                    onClick = {
                        onClassClick(gymClass.name)
                    }
                )
            }
        }
    }
}

@Composable
fun ClassCard(
    gymClass: GymClass,
    onClick: () -> Unit
) {

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(TecsupLightGreen),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Clase",
                    tint = TecsupGreen,
                    modifier = Modifier.size(27.dp)
                )
            }

            Spacer(
                modifier = Modifier.width(14.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = gymClass.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF202020)
                )

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                Text(
                    text = gymClass.schedule,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = TecsupGreen
                )

                Spacer(
                    modifier = Modifier.height(2.dp)
                )

                Text(
                    text = gymClass.room,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF666666)
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Ver detalle",
                tint = TecsupGreen,
                modifier = Modifier.size(22.dp)
            )
        }
    }
}