package com.example.pokemon.domain.model

import com.example.pokemon.data.testclass.Other
import com.example.pokemon.data.testclass.Versions
import com.google.gson.annotations.SerializedName


data class Sprites(
    val backDefault: String,
    val backFemale: Any,
    val backShiny: String,
    val backShinyFemale: Any,
    val frontDefault: String,
    val frontFemale: Any,
    val frontShiny: String,
    val frontShinyFemale: Any,
    val other: Other,
    val versions: Versions
)
