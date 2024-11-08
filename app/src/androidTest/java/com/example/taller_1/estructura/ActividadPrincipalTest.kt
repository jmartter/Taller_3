package com.example.taller_1.estructura

import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.taller_1.ui.theme.Taller_1Theme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ActividadPrincipalTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ActividadPrincipal>()

    @Test
    fun pantallaPrincipalRendersCorrectly() {
        // Verifica que el campo de texto y los botones estén presentes
        composeTestRule.onNodeWithText("Ingresa tu nombre")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Guardar nombre")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Ir a la pantalla de configuración")
            .assertIsDisplayed()
    }

    @Test
    fun guardaNombreCorrectamente() {
        // Inserta el nombre en el TextField
        val nombre = "Jose"
        composeTestRule.onNodeWithText("Ingresa tu nombre")
            .performTextInput(nombre)

        // Clic en el botón para guardar el nombre
        composeTestRule.onNodeWithText("Guardar nombre")
            .performClick()

        // Verifica que el saludo correcto se muestre
        composeTestRule.onNodeWithText("Hola, $nombre")
            .assertIsDisplayed()

        // Verifica si el nombre se ha guardado en SharedPreferences (en contexto real)
        val sharedPreferences = composeTestRule.activity.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        assert(sharedPreferences.getString("saved_name", "") == nombre)
    }

    @Test
    fun muestraErrorCuandoNoSeIngresaNombre() {
        // Clic en el botón de "Ir a la pantalla de configuración" sin ingresar un nombre
        composeTestRule.onNodeWithText("Ir a la pantalla de configuración")
            .performClick()

        // Verifica que el mensaje de error se muestre
        composeTestRule.onNodeWithText("Por favor, ingresa tu nombre antes de continuar.")
            .assertIsDisplayed()
    }

    @Test
    fun navegaAPantallaDeConfiguracionCorrectamente() {
        // Inserta el nombre en el TextField
        val nombre = "Jose"
        composeTestRule.onNodeWithText("Ingresa tu nombre")
            .performTextInput(nombre)

        // Clic en el botón de "Ir a la pantalla de configuración"
        composeTestRule.onNodeWithText("Ir a la pantalla de configuración")
            .performClick()

    }
}
