package com.example.pokemon.utils

import android.os.Bundle
import androidx.navigation.NavType
import com.example.pokemon.domain.PokemonDetails
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object CustomNavType{
    val PokemonDetailsType = object : NavType<PokemonDetails>(
        isNullableAllowed = false
    ){
        override fun get(bundle: Bundle, key: String): PokemonDetails? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): PokemonDetails {
            return Json.decodeFromString(value)
        }

        override fun put(bundle: Bundle, key: String, value: PokemonDetails) {
            bundle.putString(key,Json.encodeToString(value))
        }

        override fun serializeAsValue(value: PokemonDetails): String {
            return Json.encodeToString(value)
        }

    }
}