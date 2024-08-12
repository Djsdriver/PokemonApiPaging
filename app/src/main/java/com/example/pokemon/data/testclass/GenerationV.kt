package com.example.pokemon.data.testclass


import com.example.pokemon.data.testclass.BlackWhite
import com.google.gson.annotations.SerializedName

data class GenerationV(
    @SerializedName("black-white")
    val blackWhite: BlackWhite
)