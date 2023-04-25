package com.example.dogbreed.data.remote.model

import com.google.gson.annotations.SerializedName

data class AllBreedsResponse(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: HashMap<String, List<String>>
)



