package com.ramirez.avisoscampus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.SystemBarStyle
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramirez.avisoscampus.data.AvisosRepositoryMemoria
import com.ramirez.avisoscampus.ui.PantallaAvisos
import com.ramirez.avisoscampus.ui.theme.AvisoscampusTheme
import com.ramirez.avisoscampus.viewmodel.AvisosViewModel
import com.ramirez.avisoscampus.viewmodel.AvisosViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT)
        )
        setContent {
            AvisoscampusTheme {
                val factory = remember {
                    AvisosViewModelFactory(AvisosRepositoryMemoria())
                }
                val vm: AvisosViewModel = viewModel(factory = factory)
                PantallaAvisos(vm)
            }
        }
    }
}
