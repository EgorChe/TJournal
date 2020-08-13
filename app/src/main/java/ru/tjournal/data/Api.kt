package ru.tjournal.data

import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface Api {

    @GET("subsite/{id}/timeline/{sorting}")
    suspend fun getFeeds(
        @Path("id") id: Int,
        @Path("sorting") sorting: String,
        @Query("count") count: Int,
        @Query("offset") offset: Int
    ): Response<FeedsResponse>

    @Multipart
    @POST("auth/qr")
    suspend fun authQR(@Part("token") token: RequestBody): Response<AuthResponse>

}