package com.example.superheros.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
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
import android.view.MenuItem

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_ID = "SUPERHERO_ID"
        const val EXTRA_NAME = "SUPERHERO_NAME"
        const val EXTRA_IMAGE = "SUPERHERO_IMAGE"
    }

    private lateinit var superhero: Superhero
    private lateinit var binding: ActivityDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initActionBar()

        val id = intent.getIntExtra("SUPERHERO_ID", -1)
        val name = intent.getStringExtra("SUPERHERO_NAME")
        val image = intent.getStringExtra("SUPERHERO_IMAGE")

        binding.toolbar
        Picasso.get().load(image).into(binding.imageViewSuperhero)
        getSuperheroById(id)
    }

    private fun initActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

    }

    private fun loadData(superhero: Superhero) {


        binding.SuperHeroTextView.text = superhero.name

        //Biography
        var biography = ""
        biography += "Nombre Real: ${superhero.biography.realName}\n"
        biography += "Lugar de Nacimiento: ${superhero.biography.placebirth}\n"
        biography += "Alignment: ${superhero.biography.alignment}\n"
        biography += "Publisher: ${superhero.biography.publisher}"
        binding.BiographytextView.text = biography

        //Work
        var work = ""
        work += "Ocupación: ${superhero.work.occupation}\n"
        work += "Lugar: ${superhero.work.base}"
        binding.WorktextView.text = work


        //Stats
        var stats = ""
        stats += "Inteligencia: ${superhero.stats.intelligence}\n"
        stats += "Fuerza: ${superhero.stats.strength}\n"
        stats += "Velocidad: ${superhero.stats.speed}\n"
        stats += "Resistencia: ${superhero.stats.durability}\n"
        stats += "Poder: ${superhero.stats.power}\n"
        stats += "Combate: ${superhero.stats.combat}"
        binding.StatstextView.text = stats

        Picasso.get().load(superhero.image.url).into(binding.imageViewSuperhero)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun getSuperheroById(id: Int) {

        val service: SuperheroAPIService = RetrofitProvider.getRetrofit()
        //Llamada al segundo hilo
        CoroutineScope(Dispatchers.IO).launch {
            val result = service.getSuperHeroById(id)

            runOnUiThread {

                loadData(result)

            }
        }
    }
}