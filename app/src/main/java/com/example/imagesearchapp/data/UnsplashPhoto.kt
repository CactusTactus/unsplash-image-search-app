package com.example.imagesearchapp.data


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UnsplashPhoto(
    @SerializedName("id")
    val id: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("user")
    val user: UnsplashUser,
    @SerializedName("urls")
    val urls: UnsplashPhotoUrls
) : Parcelable