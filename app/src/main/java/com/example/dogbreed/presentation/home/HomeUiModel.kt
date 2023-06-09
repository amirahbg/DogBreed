package com.example.dogbreed.presentation.home

sealed class HomeUiModel {
    data class Success(val data: HashMap<String, List<String>>) : HomeUiModel()

    object Loading : HomeUiModel()

    data class Error(val exception: Throwable?) : HomeUiModel()
}