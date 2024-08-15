package com.example.pokemon.presentation

sealed class ResultClass<out T> {
    data class Success<out T>(val data: T) : ResultClass<T>()
    data class Error(val exception: Exception) : ResultClass<Nothing>()
}
