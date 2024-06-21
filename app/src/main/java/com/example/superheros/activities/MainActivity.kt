package com.example.superheros.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.superheros.R
import com.example.superheros.data.SuperheroAPIService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchByName("super")

    }

    private fun searchByName (query:String) {
        //Llamada al segundo hilo
        CoroutineScope(Dispatchers.IO).launch {
            val apiService = getRetrofit().create(SuperheroAPIService::class.java)
            val result = apiService.findSuperHeroByName("super")
        }

    }

    private fun getRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseURl("")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
}