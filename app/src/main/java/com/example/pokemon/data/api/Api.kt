package com.example.pokemon.data.api

import com.example.pokemon.data.models.PokemonDto
import com.example.pokemon.data.models.PokemonListDto
import com.example.pokemon.data.testclass.Pokemon
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ) : PokemonListDto

    @GET("pokemon/{name}")
    suspend fun getPokemonDetails(
        @Path("name") name: String
    ) : Pokemon

}