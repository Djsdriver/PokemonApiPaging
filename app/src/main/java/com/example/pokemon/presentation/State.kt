package com.example.pokemon.presentation

import androidx.paging.PagingData
import com.example.pokemon.domain.model.PokemonItem
import com.example.pokemon.domain.model.PokemonList
import com.example.pokemon.domain.model.Result

data class State(
    val list: PagingData<Result> = PagingData.empty(),
    var listPokemon: MutableList<PokemonItem> = mutableListOf(),
    val namePokemon : String = "",
    val url : String = "",
    val number : Int = 0,

    )
