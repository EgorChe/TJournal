package ru.tjournal.presentation.feature.feed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import ru.tjournal.R
import ru.tjournal.presentation.feature.feed.model.MediaObject
import ru.tjournal.util.VideoPlayerViewHolder
import java.util.*

class VideoPlayerRecyclerAdapter(
    private var mediaObjects: LinkedHashSet<MediaObject>,
    private val requestManager: RequestManager,
    private val onScrollToBottomListener: () -> Unit
) : RecyclerView.Adapter<VideoPlayerViewHolder>() {

    fun setData(data: LinkedHashSet<MediaObject>) {
        this.mediaObjects = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoPlayerViewHolder {
        return VideoPlayerViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_video_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VideoPlayerViewHolder, position: Int) {
        holder.onBind(mediaObjects.elementAt(position), requestManager)
        if (position == mediaObjects.size - 1) {
            onScrollToBottomListener()
        }
    }

    override fun getItemCount(): Int = mediaObjects.size
}