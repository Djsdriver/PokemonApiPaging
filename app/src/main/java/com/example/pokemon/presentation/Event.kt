package com.example.pokemon.presentation

import androidx.paging.PagingData
import com.example.pokemon.domain.model.Result
import kotlinx.coroutines.flow.Flow

sealed class PokemonListEvent{

    class ShowPokemonListPaging(val list : Flow<PagingData<Result>>): PokemonListEvent()
}