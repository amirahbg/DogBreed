package com.example.dogbreed.data.remote

import com.example.dogbreed.data.remote.model.AllBreedsResponse
import retrofit2.http.GET

interface DogBreedService {
    @GET("list/all")
    suspend fun getAllBreeds(): AllBreedsResponse
}