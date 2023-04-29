package com.example.dogbreed

import com.example.dogbreed.presentation.home.HomeUiModel
import com.example.dogbreed.presentation.home.HomeViewModel
import com.example.dogbreed.data.repository.DogBreedRepository
import com.example.dogbreed.data.utils.*
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val dogBreedRepository = mockk<DogBreedRepository>()
    private val viewModel = HomeViewModel(dogBreedRepository)

    @Test
    fun `fetchDogBreeds emits Success`() = runBlocking {
        val mockData = hashMapOf("dog" to listOf("Poodle", "Labrador", "Bulldog"))
        every { dogBreedRepository.fetchAllDogBreeds() } returns (flowOf(Result.Success(mockData)))

        viewModel.fetchDogBreeds()

        val uiState = viewModel.uiState.first()
        assert(uiState is HomeUiModel.Success)
        assertEquals(mockData, (uiState as HomeUiModel.Success).data)
    }

    @Test
    fun `fetchDogBreeds emits Error`() = runBlocking {
        val mockException = Exception("Something went wrong")
        every { dogBreedRepository.fetchAllDogBreeds() } returns (flowOf(Result.Error(mockException)))

        viewModel.fetchDogBreeds()

        val uiState = viewModel.uiState.first()
        assert(uiState is HomeUiModel.Error)
        assertEquals(mockException, (uiState as HomeUiModel.Error).exception)
    }

    @Test
    fun `fetchDogBreeds emits Loading`() = runBlocking {
        every { dogBreedRepository.fetchAllDogBreeds() } returns (flowOf(Result.Loading))

        viewModel.fetchDogBreeds()

        val uiState = viewModel.uiState.first()
        assert(uiState is HomeUiModel.Loading)
    }
}
