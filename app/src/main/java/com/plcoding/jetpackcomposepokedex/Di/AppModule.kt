package com.plcoding.jetpackcomposepokedex.Di

import com.plcoding.jetpackcomposepokedex.Repository.PokemonRepository
import com.plcoding.jetpackcomposepokedex.Util.Constatns
import com.plcoding.jetpackcomposepokedex.data.remote.PokeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePokemonRepo(
        api:PokeApi
    )=PokemonRepository(api)


    @Singleton
    @Provides
    fun providePokeApi()=Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constatns.BASE_URL)
        .build()
        .create(PokeApi::class.java)
}