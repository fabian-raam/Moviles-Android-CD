package com.ramirez.murocampus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramirez.murocampus.data.PostRepositoryRemote
import com.ramirez.murocampus.data.remote.RetrofitClient
import com.ramirez.murocampus.ui.PantallaLista
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
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text("MuroCampus") },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                titleContentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    }
                ) { padding ->
                    Surface(Modifier.fillMaxSize().padding(padding)) {
                        PantallaLista(vm)
                    }
                }
            }
        }
    }
}
