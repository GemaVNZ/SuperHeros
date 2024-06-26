package com.example.superheros.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.superheros.R
import com.example.superheros.data.Superhero
import com.example.superheros.data.SuperheroAPIService
import com.example.superheros.data.SuperheroResponse
import com.example.superheros.databinding.ActivityDetailBinding
import com.example.superheros.utils.RetrofitProvider
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_ID = "SUPERHERO_ID"
        const val EXTRA_NAME = "SUPERHERO_NAME"
        const val EXTRA_IMAGE = "SUPERHERO_IMAGE"

    }

    private lateinit var superhero: Superhero

    private lateinit var binding: ActivityDetailBinding

    private var superheroList: List<Superhero> = emptyList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initActionBar()

        val id = intent.getIntExtra("SUPERHERO_ID", -1)
        val name = intent.getStringExtra("SUPERHERO_NAME")
        val image = intent.getStringExtra("SUPERHERO_IMAGE")

        binding.toolbarLayout.title = name
        Picasso.get().load(image).into(binding.photoImageView)
        getSuperheroById(id)

    }


    private fun initActionBar() {
        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    private fun loadData() {

        binding.toolbarLayout.title = superhero.name

        // binding.content.realNameTextView.text = superhero.biography.realName


    }

    private fun getSuperheroById(id: Int) {
        // binding.content.progress.visibility = View.VISIBLE

        val service: SuperheroAPIService = RetrofitProvider.getRetrofit()
        //Llamada al segundo hilo
        CoroutineScope(Dispatchers.IO).launch {
            val response: Response<SuperheroResponse> = service.getSuperHeroById(id)
            runOnUiThread {
                //binding.content.progress.visibility = View.GONE
                if (response.isSuccessful) {
                    val superheroResponse = response.body()

                    if (superheroResponse != null) {

                        superheroList = superheroResponse.results.orEmpty()
                        loadData()
                    } else {
                        superheroList = emptyList()
                    }
                }
            }
        }
    }
}