package com.example.pokemon.domain

import androidx.navigation.NavType
import kotlinx.serialization.Serializable

@Serializable
data class PokemonDetails(val name: String, val strong: Strong)

enum class Strong{
    VERY_STRONG
}

