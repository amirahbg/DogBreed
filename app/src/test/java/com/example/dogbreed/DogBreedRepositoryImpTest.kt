package com.example.dogbreed

import com.example.dogbreed.data.remote.DogBreedRemoteDataSource
import com.example.dogbreed.data.remote.model.AllBreedsResponse
import com.example.dogbreed.data.remote.model.BreedImageResponse
import com.example.dogbreed.data.repository.DogBreedRepositoryImp
import com.example.dogbreed.data.utils.Result
import com.example.dogbreed.data.utils.asResult
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DogBreedRepositoryImpTest {

    private val remoteDataSource = mockk<DogBreedRemoteDataSource>()
    private lateinit var repository: DogBreedRepositoryImp

    @Before
    fun setUp() {
        repository = DogBreedRepositoryImp(remoteDataSource)
    }

    @Test
    fun `fetchAllDogBreeds should return expected result`() = runBlocking {
        // Given
        val expectedBreeds = hashMapOf("Hound" to listOf("afghan", "basset", "blood"))
        coEvery { remoteDataSource.getAllDogBreeds() } returns flowOf(AllBreedsResponse("success", expectedBreeds)).asResult()

        // When
        val result = repository.fetchAllDogBreeds().toList()

        // Then
        val expected = listOf(Result.Loading, Result.Success(expectedBreeds))
        assertEquals(expected, result)
    }

    @Test
    fun `fetchBreedImages should return expected result`() = runBlocking {
        // Given
        val breedName = "hound"
        val expectedImages = listOf("https://images.dog.ceo/breeds/hound-afghan/n02088094_1003.jpg")
        coEvery { remoteDataSource.getBreedImage(breedName) } returns flowOf(BreedImageResponse(expectedImages, "success")).asResult()

        // When
        val result = repository.fetchBreedImages(breedName).toList()

        // Then
        val expected = listOf(Result.Loading, Result.Success(expectedImages))
        assertEquals(expected, result)
    }
}