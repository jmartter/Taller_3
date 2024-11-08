package com.example.taller_1.estructura

import android.os.AsyncTask
import android.util.Log

class LoadMenuTask(private val callback: (List<String>) -> Unit) : AsyncTask<Void, Void, List<String>>() {
    override fun onPreExecute() {
        super.onPreExecute()
        Log.d("LoadMenuTask", "onPreExecute: ${Thread.currentThread().name}")
    }

    override fun doInBackground(vararg params: Void?): List<String> {
        Log.d("LoadMenuTask", "doInBackground: ${Thread.currentThread().name}")
        // Simulate data loading
        Thread.sleep(2000) // Simulate delay
        return listOf("Menu 1", "Menu 2", "Menu 3")
    }

    override fun onPostExecute(result: List<String>) {
        super.onPostExecute(result)
        Log.d("LoadMenuTask", "onPostExecute: ${Thread.currentThread().name}")
        callback(result)
    }
}