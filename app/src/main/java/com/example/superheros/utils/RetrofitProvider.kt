package com.example.superheros.utils

import com.example.superheros.data.SuperheroAPIService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitProvider {
    companion object {
        fun getRetrofit() : SuperheroAPIService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://superheroapi.com/api/7252591128153666/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(SuperheroAPIService::class.java)
        }
    }
}