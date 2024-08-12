package com.example.pokemon.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon.domain.repository.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonInfoViewModel @Inject constructor(private val api: ApiRepository) : ViewModel() {

    private val _state = MutableStateFlow(StatePokemonInfo())
    val state = _state.asStateFlow()

    fun onEvent(pokemonEventInfo: PokemonEventInfo) {
        when (pokemonEventInfo) {
            is PokemonEventInfo.PokemonInfo -> {
                getPokemonInfoByName(pokemonEventInfo.name)
            }
        }

    }

    private fun getPokemonInfoByName(name : String){
        viewModelScope.launch(Dispatchers.IO) {
            val result = api.getPokemonDetails(name)
            Log.d("getPokemonDetails", "${result.stats}")
            _state.update {
                it.copy(
                    namePokemon = result.name,
                    frontDefault = result.sprites.frontDefault,
                    stats = result.stats
                )
            }
        }
    }

}