package com.example.superheros.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.RecyclerView
import com.example.superheros.data.Superhero
import com.example.superheros.databinding.ItemSuperheroBinding
import com.squareup.picasso.Picasso

class SuperheroAdapter (private var dataSet : List <Superhero> = emptyList(), private val onItemClickListener:(Int) -> Unit ): RecyclerView.Adapter<SuperheroViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperheroViewHolder {
        val binding = ItemSuperheroBinding.inflate(LayoutInflater.from(parent.context))
        return SuperheroViewHolder(binding)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: SuperheroViewHolder, position: Int) {
        holder.render(dataSet[position])
        holder.itemView.setOnClickListener{
            onItemClickListener(holder.adapterPosition)
        }
    }

    fun updateData(dataSet: List<Superhero>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }

}

class SuperheroViewHolder (private val binding: ItemSuperheroBinding) : RecyclerView.ViewHolder(binding.root) {

    //Método para pintar la vista
    fun render (superhero : Superhero) {
        binding.nameTextView.text = superhero.name
        Picasso.get().load(superhero.image.url).into(binding.avatarImageView)
    }

}