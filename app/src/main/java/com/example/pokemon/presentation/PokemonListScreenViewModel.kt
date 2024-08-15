package com.example.pokemon.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.flatMap
import com.example.pokemon.data.models.PokemonListDto
import com.example.pokemon.data.models.ResultDto
import com.example.pokemon.domain.model.Pokemon
import com.example.pokemon.domain.model.PokemonItem
import com.example.pokemon.domain.model.PokemonList
import com.example.pokemon.domain.model.Result
import com.example.pokemon.domain.repository.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListScreenViewModel @Inject constructor(private val api: ApiRepository) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    init {
        getListPokemon()
    }

    fun onEvent(pokemonListEvent: PokemonListEvent) {
        when (pokemonListEvent) {
            is PokemonListEvent.ShowPokemonListPaging -> {
                getListPokemon()
            }
        }
    }

    private fun getListPokemon() {
        val errorHandler = CoroutineExceptionHandler { _, throwable ->
            _state.update {
                it.copy(
                    isLoading = false,
                    errorMessage = "Ошибка загрузки: ${throwable.message}"
                )
            }
        }
        viewModelScope.launch(errorHandler) {
            val result = fetchPokemonList()
            _state.update { state ->
                when (result) {
                    is ResultClass.Success<Flow<PagingData<Result>>> -> {
                        state.copy(list = result.data, isLoading = false)
                    }
                    is ResultClass.Error -> {
                        state.copy(
                            isLoading = false,
                            errorMessage = "Ошибка: ${result.exception.message}"
                        )
                    }
                }
            }
        }
    }

    private fun fetchPokemonList(): ResultClass<Flow<PagingData<Result>>> {
        return try {
            val data = api.getPokemonListPaging().cachedIn(viewModelScope)
            ResultClass.Success(data)
        } catch (e: Exception) {
            ResultClass.Error(e)
        }
    }

    fun getPokemonImageUrl(pokemonItem: Result): String {
        val number = if (pokemonItem.url.endsWith("/")) {
            pokemonItem.url.dropLast(1).takeLastWhile { it.isDigit() }
        } else {
            pokemonItem.url.takeLastWhile { it.isDigit() }
        }
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$number.png"
    }


}
