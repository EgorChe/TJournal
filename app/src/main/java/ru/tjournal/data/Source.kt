package ru.tjournal.data

import retrofit2.Response

class Source(private val api: Api) {

    companion object {
        private const val ID_VIDEO_AND_GIF = 237832
        private const val NEW = "new"
    }

    suspend fun getFeeds(
        id: Int = ID_VIDEO_AND_GIF,
        sorting: String = NEW
    ): Response<FeedsResponse> {
        return api.getFeeds(id, sorting)
    }

    suspend fun auth(token: String): Response<Unit> {
        val request = AuthQrRequest(token)
        return api.authQR(request)
    }
}