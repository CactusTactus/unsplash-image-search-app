package com.example.imagesearchapp.data


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UnsplashUser(
    @SerializedName("id")
    val id: String,
    @SerializedName("username")
    val username: String,
) : Parcelable {
    val attributionUrl: String
        get() = "https://unsplash.com/$username?utm_source=AndroidImageViewer&utm_medium=referral"
}