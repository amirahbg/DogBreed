package com.example.dogbreed.data.remote.model

import com.google.gson.annotations.SerializedName

data class BreedImageResponse(
    @SerializedName("message") val imageUrls: List<String>,
    @SerializedName("status") val status: String
)
