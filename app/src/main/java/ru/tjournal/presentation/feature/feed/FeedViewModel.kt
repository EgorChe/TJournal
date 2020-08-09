package ru.tjournal.presentation.feature.feed

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import ru.tjournal.data.Source
import ru.tjournal.presentation.feature.feed.model.FeedModel
import ru.tjournal.presentation.feature.feed.model.MediaObject
import ru.tjournal.presentation.feature.feed.model.toListFeedModel
import ru.tjournal.presentation.feature.feed.model.toListMediaObject

class FeedViewModel() : ViewModel() {

    private var job: Job? = null
    private lateinit var source: Source

    fun onCreate(source: Source) {
        this.source = source
    }

    fun getFeeds(): LiveData<List<MediaObject>> {
        return MutableLiveData<List<MediaObject>>().apply {

            job = CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = source.getFeeds()
                    val body = response.body()
                    if (response.isSuccessful && body != null) {
                        withContext(Dispatchers.Main) {
                            value = body.result.toListMediaObject()
                        }
                    } else {
//                    val errorMsg = response.errorBody()?.errorMsg()
//                        ?: provider.getString(R.string.error_something)
//                    withContext(Dispatchers.Main) {
//                        value = Resource.error(errorMsg, "")
//                    }
                    }
                } catch (e: Exception) {
                    Log.e("Test", e.localizedMessage)
//                withContext(Dispatchers.Main) {
//                    val errorMsg = e.message ?: provider.getString(R.string.error_something)
//                    value = Resource.error(errorMsg, "")
//                }
                }
            }
        }
    }


    override fun onCleared() {
        job?.cancel()
        super.onCleared()
    }
}