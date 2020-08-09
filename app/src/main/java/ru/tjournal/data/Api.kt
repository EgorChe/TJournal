package ru.tjournal.data

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Api {

    @GET("subsite/{id}/timeline/{sorting}")
    suspend fun getFeeds(
        @Path("id") id: Int,
        @Path("sorting") sorting: String
    ): Response<FeedsResponse>

    @POST("auth/qr")
    suspend fun authQR(@Body request: AuthQrRequest): Response<Unit>

}