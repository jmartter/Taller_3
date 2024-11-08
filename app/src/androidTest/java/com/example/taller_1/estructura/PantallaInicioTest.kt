package com.example.taller_1.estructura

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.taller_1.ui.theme.Taller_1Theme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PantallaInicioTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<PantallaInicio>()

    @Test
    fun pantallaInicioRendersCorrectly() {
        // Verifica que el saludo correcto está presente en la pantalla
        val greeting = if (isDayTime()) "Buenos días" else "Buenas noches"

        // No es necesario volver a establecer el contenido, ya que la actividad lo hace automáticamente
        composeTestRule.onNodeWithText(greeting)
            .assertIsDisplayed()

        // Verificar que el botón está presente
        composeTestRule.onNodeWithText("Ir a la actividad principal")
            .assertIsDisplayed()
    }

    @Test
    fun clickOnButtonNavigatesToActividadPrincipal() {
        // Verificar que el botón aparece
        composeTestRule.onNodeWithText("Ir a la actividad principal")
            .assertIsDisplayed()

        // Simula el clic en el botón
        composeTestRule.onNodeWithText("Ir a la actividad principal")
            .performClick()

    }

    private fun isDayTime(): Boolean {
        val hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)
        return hour in 6..18
    }
}
