package com.ramirez.centrotareas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramirez.centrotareas.data.TareasRepositoryLocal
import com.ramirez.centrotareas.ui.PantallaTareas
import com.ramirez.centrotareas.ui.theme.CentrotareasTheme
import com.ramirez.centrotareas.viewmodel.TareasViewModel
import com.ramirez.centrotareas.viewmodel.TareasViewModelFactory

class MainActivity : ComponentActivity() {
    private val factory by lazy { TareasViewModelFactory(TareasRepositoryLocal()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CentrotareasTheme {
                val tareasViewModel: TareasViewModel = viewModel(factory = factory)
                PantallaTareas(tareasViewModel)
            }
        }
    }
}
