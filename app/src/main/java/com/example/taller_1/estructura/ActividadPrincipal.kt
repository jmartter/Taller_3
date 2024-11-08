package com.example.taller_1.estructura

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
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

val colorNames = mapOf(
    Color.Red to "Rojo",
    Color.Green to "Verde",
    Color.Blue to "Azul",
    Color.Yellow to "Amarillo",
    Color.Cyan to "Cian",
    Color.Magenta to "Magenta"
)

class ActividadPrincipal : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val selectedColor = intent.getIntExtra("selectedColor", Color.White.toArgb())
        setContent {
            Taller_1Theme {
                ActividadPrincipalScreen(Color(selectedColor))
            }
        }
    }
}

@Composable
fun ActividadPrincipalScreen(initialBackgroundColor: Color) {
    var backgroundColor by remember { mutableStateOf(initialBackgroundColor) } // Mantener el color de fondo actualizado
    var name by remember { mutableStateOf("") }
    var greeting by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var showDuplicateError by remember { mutableStateOf(false) }
    var namesAndColorsList by remember { mutableStateOf(listOf<Pair<String, Int>>()) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var itemToDelete by remember { mutableStateOf<Pair<String, Int>?>(null) }
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val dbHelper = DatabaseHelper(context)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        if (greeting.isNotEmpty() && !showError && !showDuplicateError) {
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
                    showDuplicateError = false
                },
                label = { Text("Ingresa tu nombre") }
            )
            Spacer(modifier = Modifier.height(25.dp))
            Button(onClick = {
                if (name.isNotEmpty()) {
                    val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                    with(sharedPreferences.edit()) {
                        putString("saved_name", name)
                        apply()
                    }
                    greeting = "Hola, $name"
                    focusManager.clearFocus()

                    // Guardar en SQLite
                    val selectedColor = backgroundColor.toArgb()
                    val savedName = sharedPreferences.getString("saved_name", "")
                    if (!savedName.isNullOrEmpty()) {
                        val success = dbHelper.saveNameAndColor(savedName, selectedColor)
                        if (!success) {
                            showDuplicateError = true
                        }
                    }
                } else {
                    showError = true
                }
            }) {
                Text("Guardar nombre y en SQLite")
            }
            Spacer(modifier = Modifier.height(25.dp))
            Button(onClick = {
                namesAndColorsList = dbHelper.getAllNamesAndColors()
                val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                val savedName = sharedPreferences.getString("saved_name", "")
                if (!savedName.isNullOrEmpty()) {
                    greeting = "Hola, $savedName"
                }
            }) {
                Text("Cargar desde SQLite")
            }
            Spacer(modifier = Modifier.height(25.dp))
            Button(onClick = {
                val intent = Intent(context, PantallaConfiguracion::class.java)
                intent.putExtra("selectedColor", backgroundColor.toArgb())
                context.startActivity(intent)
            }) {
                Text("Ir a Pantalla Configuración")
            }
            Spacer(modifier = Modifier.height(25.dp))
            // Mostrar lista de nombres y colores con el nombre del color
            if (namesAndColorsList.isNotEmpty()) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    namesAndColorsList.forEach { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable {
                                    itemToDelete = item
                                    showDeleteDialog = true
                                }
                        ) {
                            val color = Color(item.second)
                            val colorName = colorNames[color] ?: "Desconocido"
                            Text(
                                text = "${item.first} - $colorName",
                                modifier = Modifier.padding(8.dp),
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            }
            if (showError) {
                Text(
                    text = "Por favor, ingresa tu nombre antes de continuar.",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
            if (showDuplicateError) {
                Text(
                    text = "El nombre ya existe. Por favor, ingresa un nombre diferente.",
                    color = Color.Red,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }

        // Dialogo de confirmación para borrar y cargar
        if (showDeleteDialog && itemToDelete != null) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text("Eliminar o Cargar") },
                text = { Text("¿Estás seguro de que deseas eliminar a ${itemToDelete?.first}? O cargar el color?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            itemToDelete?.let {
                                dbHelper.deleteNameAndColor(it.first) // Eliminar de SQLite
                                namesAndColorsList = dbHelper.getAllNamesAndColors() // Actualizar lista
                            }
                            showDeleteDialog = false
                        }
                    ) {
                        Text("Eliminar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        // Acción de cargar nombre y color
                        itemToDelete?.let {
                            val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                            with(sharedPreferences.edit()) {
                                putString("saved_name", it.first)
                                apply()
                            }
                            val color = Color(it.second)
                            // Actualizar el color de fondo en la actividad
                            backgroundColor = color // Cambiar el color de fondo de la pantalla actual
                            greeting = "Hola, ${it.first}"
                        }
                        showDeleteDialog = false
                    }) {
                        Text("Cargar")
                    }
                }
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun ActividadPrincipalScreenPreview() {
    Taller_1Theme {
        ActividadPrincipalScreen(Color.White)
    }
}