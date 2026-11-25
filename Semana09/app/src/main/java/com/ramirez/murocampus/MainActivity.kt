package com.ramirez.murocampus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramirez.murocampus.data.PostRepositoryRemote
import com.ramirez.murocampus.data.remote.RetrofitClient
import com.ramirez.murocampus.ui.PantallaLista
import com.ramirez.murocampus.ui.PantallaDetalle
import com.ramirez.murocampus.ui.PantallaCrear
import com.ramirez.murocampus.ui.theme.MurocampusTheme
import com.ramirez.murocampus.viewmodel.PostsViewModel
import com.ramirez.murocampus.viewmodel.PostsViewModelFactory

class MainActivity : ComponentActivity() {
    private val postsFactory by lazy {
        PostsViewModelFactory(PostsViewModel::class.java) {
            PostsViewModel(PostRepositoryRemote(RetrofitClient.api))
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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
                BackHandler(enabled = idSeleccionado != null || creando, onBack = volverALista)
                LaunchedEffect(idSeleccionado) {
                    idSeleccionado?.let { vm.cargarDetalleSiNecesario(it) }
                }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text("MuroCampus") },
                            navigationIcon = {
                                if (idSeleccionado != null || creando) {
                                    TextButton(
                                        onClick = volverALista,
                                        colors = ButtonDefaults.textButtonColors(
                                            contentColor = MaterialTheme.colorScheme.onPrimary
                                        )
                                    ) { Text("Atrás") }
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                titleContentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    },
                    floatingActionButton = {
                        if (idSeleccionado == null && !creando) {
                            FloatingActionButton(
                                onClick = {
                                    vm.reiniciarPublicacion()
                                    creando = true
                                },
                                modifier = Modifier.semantics { contentDescription = "Crear publicación" }
                            ) { Text("+", style = MaterialTheme.typography.headlineMedium) }
                        }
                    }
                ) { padding ->
                    Surface(Modifier.fillMaxSize().padding(padding)) {
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
