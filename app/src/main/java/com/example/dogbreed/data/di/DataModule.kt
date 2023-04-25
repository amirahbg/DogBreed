package com.example.dogbreed.data.di

import com.example.dogbreed.data.remote.DogBreedRemoteDataSource
import com.example.dogbreed.data.remote.DogBreedRemoteDataSourceImp
import com.example.dogbreed.data.repository.DogBreedRepository
import com.example.dogbreed.data.repository.DogBreedRepositoryImp
import com.example.dogbreed.data.remote.DogBreedService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
abstract class BindableDataModule {

    @Binds
    abstract fun bindDogBreedRepository(imp: DogBreedRepositoryImp): DogBreedRepository

    @Binds
    abstract fun bindDogBreedRemoteDataSource(imp: DogBreedRemoteDataSourceImp): DogBreedRemoteDataSource
}

@Module
@InstallIn(SingletonComponent::class)
object ProvidableDataModule {

    @Provides
    fun provideDogBreedService(): DogBreedService {
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breeds/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DogBreedService::class.java)
    }
}