package ru.tjournal.presentation.feature.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import retrofit2.Response
import ru.tjournal.data.AuthResponse
import ru.tjournal.data.SavedData
import ru.tjournal.data.Source

class ProfileViewModel : ViewModel() {

    private var job: Job? = null
    private lateinit var source: Source
    private lateinit var savedData: SavedData

    fun onCreate(source: Source, savedData: SavedData) {
        this.source = source
        this.savedData = savedData
    }

    fun auth(token: String): LiveData<Response<AuthResponse>> {
        return MutableLiveData<Response<AuthResponse>>().apply {
            job = CoroutineScope(Dispatchers.IO).launch {
                val response = source.auth(token.getToken())
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    saveUserData(body.result.name, body.result.avatarUrl)
                }
                withContext(Dispatchers.Main) {
                    value = response
                }
            }
        }
    }

    private fun saveUserData(name: String?, avatarUrl: String?) {
        name?.let { savedData.userName = it }
        avatarUrl?.let { savedData.userAvatar = it }
    }

    fun getUserData(): LiveData<Pair<String, String>> {
        return MutableLiveData<Pair<String, String>>().apply {
            value = Pair(savedData.userName, savedData.userAvatar)
        }
    }

    /**
     * Убираем из QR кода v3|
     */
    private fun String.getToken(): String {
        val splitStrings = this.split("|")
        return if (splitStrings.size >= 2) splitStrings[1]
        else ""
    }

    fun logout() = saveUserData("", "")


    override fun onCleared() {
        job = null
        super.onCleared()
    }
}