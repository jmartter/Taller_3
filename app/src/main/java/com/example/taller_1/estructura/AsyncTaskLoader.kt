package com.example.taller_1.estructura

import android.content.Context
import android.util.Log
import androidx.loader.content.AsyncTaskLoader

class MenuLoader(context: Context) : AsyncTaskLoader<List<String>>(context) {
    override fun onStartLoading() {
        super.onStartLoading()
        Log.d("MenuLoader", "onStartLoading: ${Thread.currentThread().name}")
        forceLoad()
    }

    override fun loadInBackground(): List<String>? {
        Log.d("MenuLoader", "loadInBackground: ${Thread.currentThread().name}")
        // Simulate data loading
        Thread.sleep(2000) // Simulate delay
        return listOf("Menu 1", "Menu 2", "Menu 3")
    }

    override fun deliverResult(data: List<String>?) {
        super.deliverResult(data)
        Log.d("MenuLoader", "deliverResult: ${Thread.currentThread().name}")
    }
}