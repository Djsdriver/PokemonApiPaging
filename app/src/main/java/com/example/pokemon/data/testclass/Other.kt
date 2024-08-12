package com.example.pokemon.data.testclass


import com.example.pokemon.data.testclass.DreamWorld
import com.example.pokemon.data.testclass.OfficialArtwork
import com.google.gson.annotations.SerializedName

data class Other(
    @SerializedName("dream_world")
    val dreamWorld: DreamWorld,
    @SerializedName("official-artwork")
    val officialArtwork: OfficialArtwork
)