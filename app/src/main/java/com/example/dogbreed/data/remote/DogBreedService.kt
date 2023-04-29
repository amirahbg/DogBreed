package com.example.dogbreed.data.remote

import com.example.dogbreed.data.remote.model.AllBreedsResponse
import com.example.dogbreed.data.remote.model.BreedImageResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DogBreedService {
    @GET("breeds/list/all")
    suspend fun getAllBreeds(): AllBreedsResponse

    @GET("breed/{breedName}/images")
    suspend fun getBreedImages(@Path("breedName") breedName: String): BreedImageResponse
}