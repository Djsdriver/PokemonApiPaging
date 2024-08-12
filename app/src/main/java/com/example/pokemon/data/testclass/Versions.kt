package com.example.pokemon.data.testclass


import com.example.pokemon.data.testclass.GenerationI
import com.example.pokemon.data.testclass.GenerationIi
import com.example.pokemon.data.testclass.GenerationIii
import com.example.pokemon.data.testclass.GenerationIv
import com.example.pokemon.data.testclass.GenerationV
import com.example.pokemon.data.testclass.GenerationVi
import com.example.pokemon.data.testclass.GenerationVii
import com.google.gson.annotations.SerializedName

data class Versions(
    @SerializedName("generation-i")
    val generationI: GenerationI,
    @SerializedName("generation-ii")
    val generationIi: GenerationIi,
    @SerializedName("generation-iii")
    val generationIii: GenerationIii,
    @SerializedName("generation-iv")
    val generationIv: GenerationIv,
    @SerializedName("generation-v")
    val generationV: GenerationV,
    @SerializedName("generation-vi")
    val generationVi: GenerationVi,
    @SerializedName("generation-vii")
    val generationVii: GenerationVii,
    @SerializedName("generation-viii")
    val generationViii: GenerationViii
)