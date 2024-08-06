package com.example.pokemon.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.pokemon.data.models.PokemonListDto
import com.example.pokemon.data.models.ResultDto
import com.example.pokemon.domain.model.Pokemon
import com.example.pokemon.domain.model.PokemonList
import com.example.pokemon.domain.model.Result
import kotlinx.coroutines.flow.Flow


interface ApiRepository {

    suspend fun getPokemonList(
        limit: Int,
        offset: Int,
    ) : Flow<PokemonList>

    fun getPokemonListPaging() : Flow<PagingData<ResultDto>>

    /*suspend fun getPokemonDetails(
        name: String
    ) : Pokemon*/


}