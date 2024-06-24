package com.example.superheros.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.example.superheros.R
import com.example.superheros.adapters.SuperheroAdapter
import com.example.superheros.data.SuperheroAPIService
import com.example.superheros.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter : SuperheroAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = SuperheroAdapter(emptyList())

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)



        searchByName("super")


    }

    private fun searchByName (query:String) {
        //Llamada al segundo hilo
        CoroutineScope(Dispatchers.IO).launch {
            val apiService = getRetrofit().create(SuperheroAPIService::class.java)
            val result = apiService.findSuperHeroByName("super")

            runOnUiThread{
                adapter.updateData(result.results)
            }
        }

    }

    private fun getRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
}