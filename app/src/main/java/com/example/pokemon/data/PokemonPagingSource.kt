package com.example.pokemon.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pokemon.data.api.Api
import com.example.pokemon.data.models.PokemonListDto
import com.example.pokemon.data.models.ResultDto
import com.example.pokemon.domain.model.Result
import com.example.pokemon.utils.toResult
import javax.inject.Inject

class PokemonPagingSource @Inject constructor(
    private val api: Api
) : PagingSource<Int, Result>() {

    // Установим фиксированный лимит на 20 покемонов
    private companion object {
        const val PAGE_SIZE = 20
    }

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let { state.closestPageToPosition(it)?.nextKey?.minus(PAGE_SIZE) }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            // Используем params.key ?: 0 для начальной загрузки
            val page = params.key ?: 0
            val response = api.getPokemonList(PAGE_SIZE, page)

            // Преобразуем ответ в нужный формат
            val pokemons = response.results.map { it.toResult() }

            LoadResult.Page(
                data = pokemons,
                nextKey = page + PAGE_SIZE,
                prevKey = if (page == 0) null else page - PAGE_SIZE
            )
        } catch (e: Exception) {
            Log.e("PokemonPagingSource", "Error loading data", e)
            LoadResult.Error(e)
        }
    }
}
