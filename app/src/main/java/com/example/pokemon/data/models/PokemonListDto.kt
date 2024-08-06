package com.example.pokemon.data.models

data class PokemonListDto(
    val count: Int,
    val next: String,
    val previous: Any?,
    val results: List<ResultDto>
)