package com.example.pokemon.data.models

import com.example.pokemon.domain.model.StatX
import com.google.gson.annotations.SerializedName

data class StatDto(
    @SerializedName("base_stat")
    val baseStat: Int,
    val effort: Int,
    val stat: StatXDto
)