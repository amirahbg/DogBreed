package com.example.dogbreed.data.di

import com.example.dogbreed.BuildConfig
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
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    fun provideDogBreedService(okHttpClient: OkHttpClient): DogBreedService {
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(DogBreedService::class.java)
    }
}