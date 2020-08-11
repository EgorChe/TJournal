package ru.tjournal.data

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

    @POST("auth/qr")
    suspend fun authQR(@Body request: AuthQrRequest): Response<Unit>

}