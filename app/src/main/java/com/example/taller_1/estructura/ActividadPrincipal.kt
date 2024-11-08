package com.example.taller_1.estructura

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.example.taller_1.ui.theme.Taller_1Theme

class ActividadPrincipal : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val selectedColor = intent.getIntExtra("selectedColor", Color.White.toArgb())
        setContent {
            Taller_1Theme {
                ActividadPrincipalScreen(Color(selectedColor)) {
                    val intent = Intent(this, PantallaConfiguracion::class.java)
                    intent.putExtra("selectedColor", selectedColor)
                    startActivity(intent)
                }
            }
        }
    }
}

@Composable
fun ActividadPrincipalScreen(backgroundColor: Color, onConfigButtonClick: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var greeting by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        if (greeting.isNotEmpty() && !showError) {
            Text(
                text = greeting,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 100.dp)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = name,
                onValueChange = {
                    name = it
                    showError = false
                },
                label = { Text("Ingresa tu nombre") }
            )
            Spacer(modifier = Modifier.height(25.dp))
            Button(onClick = {
                val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                with(sharedPreferences.edit()) {
                    putString("saved_name", name)
                    apply()
                }
                greeting = "Hola, $name"
                focusManager.clearFocus()
            }) {
                Text("Guardar nombre")
            }
            Spacer(modifier = Modifier.height(25.dp))
            Button(onClick = {
                if (name.isNotEmpty()) {
                    onConfigButtonClick()
                } else {
                    showError = true
                }
            }) {
                Text("Ir a la pantalla de configuración")
            }
            if (showError) {
                Text(
                    text = "Por favor, ingresa tu nombre antes de continuar.",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActividadPrincipalScreenPreview() {
    Taller_1Theme {
        ActividadPrincipalScreen(Color.White) {}
    }
}