package com.example.dogbreed.data.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogbreed.data.repository.DogBreedRepository
import com.example.dogbreed.data.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dogBreedRepository: DogBreedRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<HomeUiModel>(HomeUiModel.Loading)
    val uiState
        get() = _uiState

    fun fetchDogBreeds() {
        viewModelScope.launch {
            dogBreedRepository.fetchAllDogBreeds()
                .collectLatest {
                    when (it) {
                        is Result.Success -> _uiState.emit(HomeUiModel.Success(it.data))
                        is Result.Error -> _uiState.emit(HomeUiModel.Error(it.exception))
                        Result.Loading -> _uiState.emit(HomeUiModel.Loading)
                    }
                }
        }
    }
}