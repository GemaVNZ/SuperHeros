package com.example.superheros.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.superheros.R
import com.example.superheros.adapters.SuperheroAdapter
import com.example.superheros.data.Superhero
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

    var superheroList = listOf<Superhero>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = SuperheroAdapter(superheroList)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)

        updateView(superheroList)
        searchByName("")

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_activitymain, menu)

        val searchViewItem = menu.findItem(R.id.menu_search)
        val searchView = searchViewItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    searchByName (it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        return true

    }
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
    }


    private fun searchByName (query:String) {
        //Llamada al segundo hilo
        CoroutineScope(Dispatchers.IO).launch {
                val apiService = getRetrofit().create(SuperheroAPIService::class.java)
                val result = apiService.findSuperHeroByName(query)

                runOnUiThread {
                    if (result.response == "success") {
                        updateSuperheroList(result.results)
                    } else {
                        updateSuperheroList(emptyList())
                    }
                }
            }
        }
    private fun getRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://superheroapi.com/api/7252591128153666/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
}