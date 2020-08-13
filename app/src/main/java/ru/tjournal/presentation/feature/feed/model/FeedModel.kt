package ru.tjournal.presentation.feature.feed.model

import ru.tjournal.data.Feed

fun List<Feed>.toListMediaObject(): List<MediaObject> =
    this.map { it.toMediaObject() }

fun Feed.toMediaObject(): MediaObject =
    MediaObject(
        title = this.title,
        mediaUrl = this.cover?.url ?: "",
        thumbnail = this.cover?.thumbnail ?: ""
    )
