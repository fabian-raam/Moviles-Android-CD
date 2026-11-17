package com.ramirez.campusgps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.ramirez.campusgps.data.LugaresRepositoryLocal
import com.ramirez.campusgps.data.UbicacionRepositoryGoogle
import com.ramirez.campusgps.ui.PantallaMapa
import com.ramirez.campusgps.ui.theme.CampusgpsTheme
import com.ramirez.campusgps.viewmodel.CampusViewModelFactory
import com.ramirez.campusgps.viewmodel.MapaViewModel

class MainActivity : ComponentActivity() {
    // La Activity conecta las implementaciones; el ViewModel solo recibe interfaces.
    private val mapaViewModel: MapaViewModel by viewModels {
        CampusViewModelFactory(
            ubicacionRepo = UbicacionRepositoryGoogle(applicationContext),
            lugaresRepo = LugaresRepositoryLocal()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CampusgpsTheme {
                PantallaMapa(vm = mapaViewModel)
            }
        }
    }
}
