package com.example.taller_1.estructura

import android.os.AsyncTask
import android.util.Log

class NetworkTask(private val callback: (Int) -> Unit) : AsyncTask<Void, Int, Void>() {
    override fun onPreExecute() {
        super.onPreExecute()
        Log.d("NetworkTask", "onPreExecute: ${Thread.currentThread().name}")
    }

    override fun doInBackground(vararg params: Void?): Void? {
        Log.d("NetworkTask", "doInBackground: ${Thread.currentThread().name}")
        for (i in 1..100) {
            Thread.sleep(50) // Simulate network delay
            publishProgress(i)
        }
        return null
    }

    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
        Log.d("NetworkTask", "onProgressUpdate: ${Thread.currentThread().name}")
        values[0]?.let { callback(it) }
    }

    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)
        Log.d("NetworkTask", "onPostExecute: ${Thread.currentThread().name}")
    }
}