package com.example.pokemon.navigation

sealed class Routes(val route: String) {


    data object Home : Routes("home")
    data object InfoPokemon : Routes("infoPokemon")
}