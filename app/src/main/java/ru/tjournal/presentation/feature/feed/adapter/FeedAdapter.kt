package ru.tjournal.presentation.feature.feed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.tjournal.R
import ru.tjournal.databinding.ItemFeedBinding
import ru.tjournal.presentation.feature.feed.model.FeedModel

class FeedAdapter : RecyclerView.Adapter<FeedAdapter.ViewHolder>() {

    private val feedList: ArrayList<FeedModel> = arrayListOf()

    fun setData(data: ArrayList<FeedModel>) {
        feedList.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFeedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = feedList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(feedList[position])
    }

    inner class ViewHolder(private val binding: ItemFeedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FeedModel) {
            binding.tvHeader.text = item.header
            binding.ivMediaContent.setImageResource(R.drawable.ic_launcher_background)

            Glide
                .with(binding.root)
                .load(item.url)
                .error(R.drawable.ic_error)
                .centerCrop()
                .into(binding.ivMediaContent);
        }
    }
}