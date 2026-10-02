package com.ramirez.tecstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ramirez.tecstore.ui.PantallaCarrito
import com.ramirez.tecstore.ui.theme.TecStoreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TecStoreTheme {
                PantallaCarrito()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PantallaCarritoPreview() {
    TecStoreTheme {
        PantallaCarrito()
    }
}
