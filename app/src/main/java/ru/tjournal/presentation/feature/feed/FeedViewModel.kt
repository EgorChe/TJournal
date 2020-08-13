package ru.tjournal.presentation.feature.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import ru.tjournal.data.Source
import ru.tjournal.presentation.feature.feed.model.MediaObject
import ru.tjournal.presentation.feature.feed.model.toListMediaObject

class FeedViewModel : ViewModel() {

    private var job: Job? = null
    private lateinit var source: Source
    private var data: LinkedHashSet<MediaObject> = linkedSetOf()

    fun onCreate(source: Source) {
        this.source = source
    }

    fun getFeeds(offset: Int): LiveData<Set<MediaObject>> {
        return MutableLiveData<Set<MediaObject>>().apply {

            job = CoroutineScope(Dispatchers.IO).launch {
                val response = source.getFeeds(offset = offset)
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    data.addAll(body.result.toListMediaObject())
                    withContext(Dispatchers.Main) {
                        value = data
                    }
                }
            }
        }
    }

    override fun onCleared() {
        job?.cancel()
        super.onCleared()
    }
}