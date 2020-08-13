package ru.tjournal.data

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response

class Source(private val api: Api) {

    companion object {
        private const val ID_VIDEO_AND_GIF = 237832
        private const val NEW = "new"
        private const val COUNT = 10
        const val OFFSET = 1
    }

    suspend fun getFeeds(
        id: Int = ID_VIDEO_AND_GIF,
        sorting: String = NEW,
        count: Int = COUNT,
        offset: Int = OFFSET
    ): Response<FeedsResponse> {
        return api.getFeeds(id, sorting, count, offset)
    }

    suspend fun auth(token: String): Response<AuthResponse> {
        val body = token.toRequestBody("text/plain".toMediaTypeOrNull())
        return api.authQR(body)
    }
}