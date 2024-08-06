package com.example.pokemon.di

import com.example.pokemon.data.repository.ApiRepositoryImpl
import com.example.pokemon.domain.repository.ApiRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {

    @Binds
    fun bindsMainRepository(api: ApiRepositoryImpl): ApiRepository

}