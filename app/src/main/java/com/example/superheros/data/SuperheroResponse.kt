package com.example.superheros.data

import com.google.gson.annotations.SerializedName

data class SuperheroResponse(

    @SerializedName("response") val response : String,
    @SerializedName("results-for") val resultsFor : String,
    @SerializedName("results") val results: List<Superhero> ) {
    }

data class Superhero (
    @SerializedName("id") val id:Int,
    @SerializedName("name") val name: String,
    @SerializedName("work") val work : Work,
    @SerializedName("powerstats") val stats : Stats,
    @SerializedName("biography") val biography : Biography,
    @SerializedName("image") val image : Image) {

}

data class Biography (
    @SerializedName("full-name") val realName:String,
    @SerializedName ("aliases") val aliases:String,
    @SerializedName ("place-of-birth") val placebirth:String,
    @SerializedName("alignment") val alignment:String,
    @SerializedName ("publisher") val publisher:String ) {


}

data class Stats(
    @SerializedName("intelligence") val intelligence:Int,
    @SerializedName ("strength") val strength:Int,
    @SerializedName ("speed") val speed:Int,
    @SerializedName("durability") val durability:Int,
    @SerializedName ("power") val power:Int,
    @SerializedName("combat") val combat:Int){

}

data class Work(
    @SerializedName("occupation") val occupation:String,
    @SerializedName ("base-of-operation") val base:String) {
}

data class Image (
    @SerializedName ("url") val url : String)