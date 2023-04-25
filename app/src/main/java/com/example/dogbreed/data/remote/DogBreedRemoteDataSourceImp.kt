package com.example.dogbreed.data.remote

import com.example.dogbreed.data.remote.model.AllBreedsResponse
import com.example.dogbreed.data.utils.Result
import com.example.dogbreed.data.utils.asResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DogBreedRemoteDataSourceImp @Inject constructor(
    private val dogBreedService: DogBreedService
) : DogBreedRemoteDataSource {

    override fun getAllDogBreeds(): Flow<Result<AllBreedsResponse>> = flow {
        emit(dogBreedService.getAllBreeds())
    }.asResult()
}