package com.example.superheros.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.superheros.R
import com.example.superheros.adapters.SuperheroAdapter
import com.example.superheros.data.Superhero
import com.example.superheros.data.SuperheroAPIService
import com.example.superheros.data.SuperheroResponse
import com.example.superheros.databinding.ActivityMainBinding
import com.example.superheros.utils.RetrofitProvider
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: SuperheroAdapter

    private var superheroList: List<Superhero> = emptyList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = SuperheroAdapter(superheroList) { position ->
            navigateToDetail(superheroList[position])

        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)


//binding.content.progress.visibility = View.VISIBLE

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_activitymain, menu)

        val searchViewItem = menu.findItem(R.id.menu_search)
        val searchView = searchViewItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    findSuperHeroByName(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        return true

    }

    private fun navigateToDetail(superhero: Superhero) {
        //Toast.makeText(this, superhero.name, Toast.LENGTH_LONG).show()
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("SUPERHERO_ID", superhero.id)
        intent.putExtra("SUPERHERO_NAME", superhero.name)
        intent.putExtra("SUPERHERO_IMAGE", superhero.image.url)
        startActivity(intent)

    }

    /*
    private fun updateSuperheroList(newSuperheroList: List<Superhero>) {
        superheroList = newSuperheroList
        adapter.updateData(superheroList)
        updateView(superheroList)
    }

    private fun updateView(dataList: List<Superhero>) {
        if (dataList.isEmpty()) {
            binding.recyclerView.visibility = View.GONE
            binding.messageView.visibility = View.VISIBLE
        } else {
            binding.recyclerView.visibility = View.VISIBLE
            binding.messageView.visibility = View.GONE
        }
    } */


    private fun findSuperHeroByName(query: String) {

        //binding.content.progress.visibility = View.VISIBLE

        val service: SuperheroAPIService = RetrofitProvider.getRetrofit()
        //Llamada al segundo hilo
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response: Response<SuperheroResponse> = service.findSuperHeroByName(query)
                runOnUiThread {
                    //binding.content.progress.visibility = View.GONE
                    if (response.isSuccessful) {
                        val superheroResponse = response.body()

                        if (superheroResponse != null) {

                            superheroList = superheroResponse.results.orEmpty()
                        } else {
                            superheroList = emptyList()
                            Toast.makeText(
                                this@MainActivity,
                                "Respuesta nula del servidor",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    } else {
                        superheroList = emptyList()
                       // Toast.makeText(
                            //this@MainActivity
                            //"Error en la solicitud: ${response.code}",
                            //Toast.LENGTH_SHORT
                        //).show()
                    }

                    adapter.updateData(superheroList)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    //binding.content.progress.visibility = View.GONE
                    // Puedes mostrar un mensaje de error genérico al usuario si ocurre una excepción
                    Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}
