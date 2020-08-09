package ru.tjournal.presentation.feature.feed.model

import ru.tjournal.data.Feed

data class FeedModel(
    val header: String,
    val url: String
)

fun List<Feed>.toListFeedModel() =
    this.map {
        it.toFeedModel()
    }

fun Feed.toFeedModel(): FeedModel =
    FeedModel(
        header = this.title,
        url = this.cover?.url ?: ""
    )

fun List<Feed>.toListMediaObject(): List<MediaObject> =
    this.map { it.toMediaObject() }

fun Feed.toMediaObject(): MediaObject =
    MediaObject(
        title = this.title,
        mediaUrl = this.cover?.url ?: "",
        thumbnail = this.cover?.thumbnail ?: ""
    )