package com.example.dogbreed.presentation.breedimages

sealed class BreedImageUiModel {
    data class Success(val data: List<String>) : BreedImageUiModel()

    object Loading : BreedImageUiModel()

    data class Error(val exception: Throwable?) : BreedImageUiModel()
}