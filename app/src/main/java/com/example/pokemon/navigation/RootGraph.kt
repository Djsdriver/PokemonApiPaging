package com.example.pokemon.navigation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.pokemon.domain.PokemonDetails
import com.example.pokemon.domain.Strong
import com.example.pokemon.presentation.PokemonInfo
import com.example.pokemon.presentation.PokemonListView
import com.example.pokemon.utils.CustomNavType
import kotlin.reflect.typeOf

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
    ShowBackStack(navController = navController)
}

fun NavGraphBuilder.pokemonHomeList(navController: NavHostController) {
    composable<PokemonListHome> {
        PokemonListView(
            onClick = { name ->
                navController.navigate(name) {
                    launchSingleTop = true
                }
            }
        )
    }
}


fun NavGraphBuilder.pokemonInfo(navController: NavHostController) {
    composable<PokemonDetails>(
        typeMap = mapOf(
            typeOf<PokemonDetails>() to CustomNavType.PokemonDetailsType,
            typeOf<Strong>() to NavType.EnumType(Strong::class.java)
        )
    ) { backStackEntry ->
        val name: PokemonDetails = backStackEntry.toRoute()
        PokemonInfo(name) {
            navController.navigateUp()
        }
    }
}


@SuppressLint("RestrictedApi")
@Composable
fun ShowBackStack(navController: NavHostController) {
    navController.addOnDestinationChangedListener { controller, _, _ ->
        val routes = controller
            .currentBackStack.value
            .map { it.destination.route }
            .joinToString(", ")

        Log.d("BackStackLog", "BackStack: $routes")
    }
}


