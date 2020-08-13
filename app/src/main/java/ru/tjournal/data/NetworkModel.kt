package ru.tjournal.data

import com.google.gson.annotations.SerializedName

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

data class AuthResponse(

    @SerializedName("message")
    val message: String,

    @SerializedName("result")
    val result: UserInfo
)

data class UserInfo(

    @SerializedName("name")
    val name: String = "",

    @SerializedName("avatar_url")
    val avatarUrl: String = ""
)