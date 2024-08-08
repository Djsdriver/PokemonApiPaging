package com.example.pokemon.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pokemon.data.PokemonPagingSource
import com.example.pokemon.data.api.Api
import com.example.pokemon.data.models.PokemonListDto
import com.example.pokemon.data.models.ResultDto
import com.example.pokemon.domain.model.Pokemon
import com.example.pokemon.domain.model.PokemonList
import com.example.pokemon.domain.model.Result
import com.example.pokemon.domain.repository.ApiRepository
import com.example.pokemon.utils.toPokemonList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(private val api: Api): ApiRepository {
    override suspend fun getPokemonList(limit: Int, offset: Int): Flow<PokemonList> = flow {
        /*val dto = api.getPokemonList(limit, offset)
        Log.d("ApiResponse", "Received: $dto")

        val list = dto.toPokemonList()
        emit(list)*/
    }

    override fun getPokemonListPaging(): Flow<PagingData<Result>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { PokemonPagingSource(api)}
        ).flow
    }

    /*override suspend fun getPokemonDetails(name: String): Pokemon {
        return
    }*/


}