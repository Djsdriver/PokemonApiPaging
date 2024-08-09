package com.example.pokemon.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.pokemon.domain.PokemonDetails
import com.example.pokemon.presentation.PokemonInfo
import com.example.pokemon.presentation.PokemonListScreenViewModel
import com.example.pokemon.presentation.PokemonListView

@Composable
fun RootGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = PokemonListHome
    )
    {
        pokemonHomeList(navController)
        pokemonInfo(navController)
    }
}

fun NavGraphBuilder.pokemonHomeList(navController: NavHostController){
    composable<PokemonListHome> {
        val viewModel = hiltViewModel<PokemonListScreenViewModel>()
        PokemonListView(
            onClick = { name -> navController.navigate(name){
                launchSingleTop = true
            } },
            viewModel = viewModel,
            onEvent = viewModel::onEvent
        )
    }
}



fun NavGraphBuilder.pokemonInfo(navController: NavHostController) {
    composable<PokemonDetails>{ backStackEntry ->
        val name : PokemonDetails = backStackEntry.toRoute()
        PokemonInfo(name)
    }
}
