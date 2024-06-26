package com.example.superheros.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.superheros.R
import com.example.superheros.data.SuperheroAPIService
import com.example.superheros.databinding.ActivityDetailBinding
import com.example.superheros.utils.RetrofitProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_ID = "SUPERHERO_ID"
        const val EXTRA_NAME = "SUPERHERO_NAME"
        const val EXTRA_IMAGE = "SUPERHERO_IMAGE"

    }

    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val id = intent.getIntExtra("SUPERHERO_ID",-1)

        getSuperheroById(id)


        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }


    private fun getSuperheroById (id:Int) {

        val service: SuperheroAPIService = RetrofitProvider.getRetrofit()
        //Llamada al segundo hilo
        CoroutineScope(Dispatchers.IO).launch {
            try {

                val response = service.getSuperHeroById(id)

                runOnUiThread {

                }

            } catch (e:Exception){
                e.printStackTrace()
            }
        }

    }

}

