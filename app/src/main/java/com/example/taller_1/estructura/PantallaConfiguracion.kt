package com.example.taller_1.estructura
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taller_1.ui.theme.Taller_1Theme

class PantallaConfiguracion : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val selectedColor = intent.getIntExtra("selectedColor", Color.White.toArgb())
        setContent {
            Taller_1Theme {
                PantallaConfiguracionScreen(Color(selectedColor)) { color ->
                    val intent = Intent(this, PantallaInicio::class.java)
                    intent.putExtra("selectedColor", color)
                    startActivity(intent)
                }
            }
        }
    }
}

@Composable
fun PantallaConfiguracionScreen(initialColor: Color, onInicioClick: (Int) -> Unit) {
    var selectedColor by remember { mutableStateOf(initialColor) }
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    val name = sharedPreferences.getString("saved_name", "Usuario") ?: "Usuario"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(selectedColor)
            .padding(16.dp)
    ) {
        Text(
            text = "Pantalla de Configuración de $name",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 32.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Seleccione el color",
                fontSize = 18.sp,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(colors.size) { index ->
                    ColorCircle(colors[index]) { selectedColor = it }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { onInicioClick(selectedColor.toArgb()) },
                modifier = Modifier.background(selectedColor)
            ) {
                Text("Inicio")
            }
        }
    }
}

@Composable
fun ColorCircle(color: Color, onClick: (Color) -> Unit) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(color, shape = CircleShape)
            .border(2.dp, Color.Black, CircleShape)
            .clickable { onClick(color) }
    )
}

val colors = listOf(
    Color.Red,
    Color.Green,
    Color.Blue,
    Color.Yellow,
    Color.Cyan,
    Color.Magenta
)

@Preview(showBackground = true)
@Composable
fun PantallaConfiguracionScreenPreview() {
    Taller_1Theme {
        PantallaConfiguracionScreen(Color.White) {}
    }
}