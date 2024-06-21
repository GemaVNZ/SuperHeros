package com.example.superheros.data

import com.google.gson.annotations.SerializedName

data class SuperheroListResponse(

    @SerializedName("response") val response : String,
    @SerializedName("results-for") val resultsFor : String) {







}