package com.example.pokemon.presentation

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import com.example.pokemon.domain.model.PokemonItem
import com.example.pokemon.domain.model.PokemonList
import com.example.pokemon.domain.model.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


data class State(
    val list: Flow<PagingData<Result>> = flowOf(PagingData.empty()),
    val namePokemon: String = "",
    val url: String = "",
    val number: Int = 0,
    )
