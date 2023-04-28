package com.example.dogbreed.data.repository

import kotlinx.coroutines.flow.Flow
import com.example.dogbreed.data.utils.Result


interface DogBreedRepository {
    fun fetchAllDogBreeds(): Flow<Result<HashMap<String, List<String>>>>

    fun fetchBreedImages(breedName: String): Flow<Result<List<String>>>
}