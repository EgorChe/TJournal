package ru.tjournal.data

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Api {

    @GET("/subsite/{id}/timeline/{sorting}")
    fun getFeeds(@Path("id") id: Int, @Path("sorting") sorting: String): Deferred<Response<Unit>>

    @POST("/auth/qr")
    fun authQR(@Body request: AuthQrRequest): Deferred<Response<Unit>>

}