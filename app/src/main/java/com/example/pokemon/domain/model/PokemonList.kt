package com.example.pokemon.domain.model


data class PokemonList(
    val count: Int?,
    val next: String?,
    val previous: Any?,
    val results: List<Result>?
)