package com.example.pokemon.presentation

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import com.example.pokemon.domain.model.Result
import kotlinx.coroutines.flow.Flow

@Stable
sealed class PokemonListEvent{

    @Stable
    class ShowPokemonListPaging(val list : Flow<PagingData<Result>>): PokemonListEvent()
}