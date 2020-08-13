package ru.tjournal.presentation.feature.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import ru.tjournal.MainActivity
import ru.tjournal.data.Source
import ru.tjournal.databinding.FragmentFeedBinding
import ru.tjournal.presentation.feature.feed.adapter.VideoPlayerRecyclerAdapter
import ru.tjournal.presentation.feature.feed.model.MediaObject
import ru.tjournal.util.VerticalSpacingItemDecorator


class FeedFragment : Fragment() {

    private val feedViewModel: FeedViewModel by viewModels()
    private lateinit var binding: FragmentFeedBinding

    private lateinit var videoAdapter: VideoPlayerRecyclerAdapter
    private var page = Source.OFFSET

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val source = (activity as MainActivity).service
        feedViewModel.onCreate(source)

        binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvFeeds.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(VerticalSpacingItemDecorator(10))
            videoAdapter =
                VideoPlayerRecyclerAdapter(
                    linkedSetOf(),
                    initGlide(),
                    onScrollToBottomListener = {
                        fetchData()
                    }
                )
            adapter = videoAdapter
        }

        fetchData()
    }

    private fun initGlide(): RequestManager {
        return Glide.with(this)
    }

    private fun fetchData() {
        feedViewModel.getFeeds(page).observe(viewLifecycleOwner, Observer { set ->
            binding.rvFeeds.setMediaObjects(
                set.toList() as ArrayList<MediaObject>,
                requireContext()
            )
            videoAdapter.setData(set as LinkedHashSet<MediaObject>)
            page++
        })
    }

    override fun onDestroyView() {
        binding.rvFeeds.releasePlayer()
        super.onDestroyView()
    }
}