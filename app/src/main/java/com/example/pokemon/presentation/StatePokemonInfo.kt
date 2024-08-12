package com.example.pokemon.presentation

import androidx.compose.runtime.Immutable
import com.example.pokemon.domain.model.Stat

@Immutable
data class StatePokemonInfo(
    val namePokemon: String = "",
    val frontDefault: String = "",
    val stats: List<com.example.pokemon.data.testclass.Stat> = emptyList(),
)
