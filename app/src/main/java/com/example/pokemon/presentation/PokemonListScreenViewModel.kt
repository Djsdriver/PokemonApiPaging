package com.example.pokemon.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.flatMap
import com.example.pokemon.data.models.PokemonListDto
import com.example.pokemon.data.models.ResultDto
import com.example.pokemon.domain.model.PokemonItem
import com.example.pokemon.domain.model.Result
import com.example.pokemon.domain.repository.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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


    fun onEvent(pokemonListEvent: PokemonListEvent){
        when(pokemonListEvent){
            is PokemonListEvent.ShowPokemonListPaging -> {
                viewModelScope.launch {
                    val pokemon = api.getPokemonListPaging()
                    _state.update {
                        it.copy(
                            list = pokemon
                        )
                    }
                }

                }
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