package com.example.taller_1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.taller_1.estructura.PantallaInicio

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startPantallaInicio()
    }

    private fun startPantallaInicio() {
        startActivity(Intent(this, PantallaInicio::class.java))
        finish()
    }
}