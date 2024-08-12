package com.example.pokemon.presentation


sealed class PokemonEventInfo {
    class PokemonInfo(val name :String): PokemonEventInfo()
}