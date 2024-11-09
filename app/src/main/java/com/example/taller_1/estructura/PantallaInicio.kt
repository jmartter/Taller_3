package com.example.taller_1.estructura

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.taller_1.R
import com.example.taller_1.ui.theme.Taller_1Theme
import java.util.Calendar

// Clase principal de la pantalla de inicio
class PantallaInicio : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val selectedColor = intent.getIntExtra("selectedColor", Color.White.toArgb())
        setContent {
            Taller_1Theme {
                // Configura la pantalla de inicio con el color seleccionado
                PantallaInicioScreen(Color(selectedColor)) {
                    val intent = Intent(this, ActividadPrincipal::class.java)
                    intent.putExtra("selectedColor", selectedColor)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}

// Función composable para la pantalla de inicio
@Composable
fun PantallaInicioScreen(backgroundColor: Color, onButtonClick: () -> Unit) {
    val (greeting, isDayTime) = getGreetingMessage()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Imagen de banner superior
        Image(
            painter = painterResource(id = if (isDayTime) R.drawable.day_banner else R.drawable.night_banner),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Texto de saludo
            Text(
                text = greeting,
                fontWeight = FontWeight.Bold,
                fontSize = 35.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Botón para navegar a la actividad principal
            Button(onClick = onButtonClick) {
                Text(text = "Ir a la actividad principal")
            }
        }
        // Imagen de banner inferior
        Image(
            painter = painterResource(id = if (isDayTime) R.drawable.day_banner else R.drawable.night_banner),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )
    }
}

// Función para obtener el mensaje de saludo basado en la hora del día
fun getGreetingMessage(): Pair<String, Boolean> {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val greeting = when (hour) {
        in 6..12 -> "Buenos días"
        in 13..18 -> "Buenas tardes"
        else -> "Buenas noches"
    }
    val isDayTime = hour in 6..18
    return Pair(greeting, isDayTime)
}

// Vista previa de la pantalla de inicio
@Preview(showBackground = true)
@Composable
fun PantallaInicioScreenPreview() {
    Taller_1Theme {
        PantallaInicioScreen(Color.White) {}
    }
}