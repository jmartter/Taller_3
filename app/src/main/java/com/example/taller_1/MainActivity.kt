package com.example.taller_1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import com.example.taller_1.estructura.PantallaInicio
import com.example.taller_1.estructura.LoadMenuTask
import com.example.taller_1.estructura.MenuLoader

class MainActivity : ComponentActivity(), LoaderManager.LoaderCallbacks<List<String>> {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Usando AsyncTask
        LoadMenuTask { menus ->
            // Aquí puedes manejar los menús cargados
            startPantallaInicio()
        }.execute()

        // Usando AsyncTaskLoader
        LoaderManager.getInstance(this).initLoader(0, null, this)
    }

    private fun startPantallaInicio() {
        startActivity(Intent(this, PantallaInicio::class.java))
        finish()
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<List<String>> {
        return MenuLoader(this)
    }

    override fun onLoadFinished(loader: Loader<List<String>>, data: List<String>?) {
        // Aquí puedes manejar los menús cargados
        startPantallaInicio()
    }

    override fun onLoaderReset(loader: Loader<List<String>>) {
        // Maneja el reinicio del loader si es necesario
    }
}