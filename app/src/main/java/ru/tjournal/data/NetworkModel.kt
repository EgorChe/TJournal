package ru.tjournal.data

import com.google.gson.annotations.SerializedName

data class AuthQrRequest(
    @SerializedName("token")
    val token: String
)