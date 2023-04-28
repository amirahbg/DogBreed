package com.example.dogbreed.data.remote

import com.example.dogbreed.data.remote.model.AllBreedsResponse
import com.example.dogbreed.data.remote.model.BreedImageResponse
import com.example.dogbreed.data.utils.Result
import kotlinx.coroutines.flow.Flow

interface DogBreedRemoteDataSource {
    fun getAllDogBreeds(): Flow<Result<AllBreedsResponse>>

    fun getBreedImage(breedName: String): Flow<Result<BreedImageResponse>>
}