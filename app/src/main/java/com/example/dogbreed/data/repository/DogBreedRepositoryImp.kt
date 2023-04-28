package com.example.dogbreed.data.repository

import com.example.dogbreed.data.remote.DogBreedRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.example.dogbreed.data.utils.Result
import kotlinx.coroutines.flow.map


class DogBreedRepositoryImp @Inject constructor(
    private val dogBreedRemoteDataSource: DogBreedRemoteDataSource
) : DogBreedRepository {

    override fun fetchAllDogBreeds(): Flow<Result<HashMap<String, List<String>>>> {
        return dogBreedRemoteDataSource.getAllDogBreeds()
            .map { data ->
                when (data) {
                    is Result.Success -> Result.Success(data.data.message)
                    is Result.Error -> data
                    Result.Loading -> Result.Loading
                }
            }
    }

    override fun fetchBreedImages(breedName: String): Flow<Result<List<String>>> {
        return dogBreedRemoteDataSource.getBreedImage(breedName)
            .map { data ->
                when (data) {
                    Result.Loading -> Result.Loading
                    is Result.Success -> Result.Success(data.data.imageUrls)
                    is Result.Error -> Result.Error(data.exception)
                }
            }
    }
}