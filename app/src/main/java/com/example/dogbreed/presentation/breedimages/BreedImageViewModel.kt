package com.example.dogbreed.presentation.breedimages

import androidx.lifecycle.SavedStateHandle
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
class BreedImageViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val dogBreedRepository: DogBreedRepository
) : ViewModel() {

    val breedName: String = handle["Breed_Name"]!!
    private val _uiState = MutableStateFlow<BreedImageUiModel>(BreedImageUiModel.Loading)
    val uiState
        get() = _uiState

    fun fetchBreedImages() {
        viewModelScope.launch {
            dogBreedRepository.fetchBreedImages(breedName)
                .collectLatest {
                    when (it) {
                        is Result.Success -> _uiState.emit(BreedImageUiModel.Success(it.data))
                        is Result.Error -> _uiState.emit(BreedImageUiModel.Error(it.exception))
                        Result.Loading -> _uiState.emit(BreedImageUiModel.Loading)
                    }
                }
        }
    }
}