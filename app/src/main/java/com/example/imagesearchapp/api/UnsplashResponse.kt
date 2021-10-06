package com.example.imagesearchapp.api

import com.example.imagesearchapp.data.UnsplashPhoto
import com.google.gson.annotations.SerializedName

data class UnsplashResponse(
    @SerializedName("results")
    val results: List<UnsplashPhoto>
)
