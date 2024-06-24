package com.example.superheros.data

import retrofit2.http.GET
import retrofit2.http.Path

interface SuperheroAPIService {

    @GET ("search/{name}")
    suspend fun findSuperHeroByName(@Path("name") query :String) :SuperheroResponse

}