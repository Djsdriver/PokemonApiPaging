package com.example.pokemon.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pokemon.data.api.Api
import com.example.pokemon.data.models.PokemonListDto
import com.example.pokemon.data.models.ResultDto
import com.example.pokemon.domain.model.Result
import javax.inject.Inject

class PokemonPagingSource @Inject constructor(
    private val api: Api
) :PagingSource<Int,ResultDto>() {
    override fun getRefreshKey(state: PagingState<Int, ResultDto>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultDto> {
        return try {
            val offset = params.key ?: 0
            val limit = params.loadSize
            val response = api.getPokemonList(limit, offset)

            // Предполагая, что ваш ответ содержит поля count, next, previous и results
            val pokemons = response.results // Получаем массив из объекта

            LoadResult.Page(
                data = pokemons,
                nextKey = if (response.next != null) offset + limit else null,
                prevKey = if (offset == 0) null else offset - limit
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


}
