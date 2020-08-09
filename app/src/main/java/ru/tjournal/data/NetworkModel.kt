package ru.tjournal.data

import com.google.gson.annotations.SerializedName

data class AuthQrRequest(
    @SerializedName("token")
    val token: String
)

data class FeedsResponse(

    @SerializedName("message")
    val message: String,

    @SerializedName("result")
    val result: List<Feed>
)

data class Feed(

    @SerializedName("title")
    val title: String,

    @SerializedName("cover")
    val cover: Cover?
)

data class Cover(
    @SerializedName("url")
    val url: String?,

    @SerializedName("thumbnailUrl")
    val thumbnail: String = ""
)