package com.ramirez.murocampus

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramirez.murocampus.data.PostRepositoryRemote
import com.ramirez.murocampus.data.remote.RetrofitClient
import com.ramirez.murocampus.ui.*
import com.ramirez.murocampus.ui.theme.*
import com.ramirez.murocampus.viewmodel.PostsViewModel
import com.ramirez.murocampus.viewmodel.PostsViewModelFactory

class MainActivity : ComponentActivity() {
    private val postsFactory by lazy {
        PostsViewModelFactory(PostsViewModel::class.java) {
            PostsViewModel(PostRepositoryRemote(RetrofitClient.api))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) window.isNavigationBarContrastEnforced = false
        setContent {
            MurocampusTheme {
                val vm: PostsViewModel = viewModel(factory = postsFactory)
                var idSeleccionado by rememberSaveable { mutableStateOf<Int?>(null) }
                var creando by rememberSaveable { mutableStateOf(false) }
                val volverALista: () -> Unit = {
                    vm.cerrarDetalle()
                    vm.reiniciarPublicacion()
                    idSeleccionado = null
                    creando = false
                }
                val nueva: () -> Unit = {
                    vm.reiniciarPublicacion()
                    creando = true
                }
                BackHandler(enabled = idSeleccionado != null || creando, onBack = volverALista)
                LaunchedEffect(idSeleccionado) {
                    idSeleccionado?.let { vm.cargarDetalleSiNecesario(it) }
                }
                Scaffold(
                    containerColor = Fondo,
                    contentWindowInsets = WindowInsets(0, 0, 0, 0),
                    topBar = {
                        Column(Modifier.fillMaxWidth().statusBarsPadding()) {
                            when {
                                creando -> Row(
                                    Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 14.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    TextButton(onClick = volverALista, contentPadding = PaddingValues(0.dp)) {
                                        Text("Cancelar", style = MaterialTheme.typography.bodyMedium, color = Coral)
                                    }
                                    Text("Nueva publicación", style = MaterialTheme.typography.titleMedium,
                                        modifier = Modifier.weight(1f), textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                                    Spacer(Modifier.width(16.dp))
                                }
                                idSeleccionado != null -> TextButton(
                                    onClick = volverALista,
                                    modifier = Modifier.padding(start = 12.dp, top = 8.dp, bottom = 4.dp),
                                    contentPadding = PaddingValues(horizontal = 4.dp)
                                ) {
                                    IconoAtras(Modifier.size(22.dp))
                                    Text("Muro", color = Coral, style = MaterialTheme.typography.bodyLarge)
                                }
                                else -> MarcaMuroCampus(Modifier.padding(start = 20.dp, end = 20.dp, top = 22.dp, bottom = 16.dp))
                            }
                        }
                    },
                    bottomBar = {
                        if (idSeleccionado == null && !creando) MenuInferior(onMuro = {}, onNueva = nueva)
                    }
                ) { padding ->
                    Surface(
                        Modifier.fillMaxSize().padding(padding)
                            .then(if (idSeleccionado != null || creando) Modifier.navigationBarsPadding() else Modifier),
                        color = Fondo
                    ) {
                        val id = idSeleccionado
                        when {
                            creando -> PantallaCrear(vm)
                            id != null -> PantallaDetalle(vm, id)
                            else -> PantallaLista(vm, onAbrir = { idSeleccionado = it })
                        }
                    }
                }
            }
        }
    }
}
