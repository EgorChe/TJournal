package ru.tjournal.data

import retrofit2.Response

class Source(private val api: Api) {

    suspend fun getFeeds(id: Int, sorting: String): Response<Unit> {
        return api.getFeeds(id, sorting).await()
    }

    suspend fun auth(token: String): Response<Unit> {
        val request = AuthQrRequest(token)
        return api.authQR(request).await()
    }
}