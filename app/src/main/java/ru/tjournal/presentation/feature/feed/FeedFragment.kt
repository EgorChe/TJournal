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
import com.bumptech.glide.request.RequestOptions
import ru.tjournal.MainActivity
import ru.tjournal.databinding.FragmentFeedBinding
import ru.tjournal.presentation.feature.feed.adapter.VideoPlayerRecyclerAdapter
import ru.tjournal.presentation.feature.feed.model.MediaObject
import ru.tjournal.util.VerticalSpacingItemDecorator


class FeedFragment : Fragment() {

    private val feedViewModel: FeedViewModel by viewModels()
    private lateinit var binding: FragmentFeedBinding

    private lateinit var videoAdapter: VideoPlayerRecyclerAdapter

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
//            setMediaObjects()
            videoAdapter =
                VideoPlayerRecyclerAdapter(arrayListOf(), initGlide())
            adapter = videoAdapter
        }

        fetchData()
    }

    private fun initGlide(): RequestManager {
        val options: RequestOptions = RequestOptions()
//            .placeholder(R.drawable.white_background)
//            .error(R.drawable.white_background)
        return Glide.with(this)
            .setDefaultRequestOptions(options)
    }

    private fun fetchData() {
        feedViewModel.getFeeds().observe(viewLifecycleOwner, Observer { list ->
            binding.rvFeeds.setMediaObjects(list as ArrayList<MediaObject>, requireContext())
            videoAdapter.setData(list)
        })
    }

    private fun getTestData(): ArrayList<MediaObject> {
        return arrayListOf(
            MediaObject(
                "Sending Data to a New Activity with Intent Extras",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Sending+Data+to+a+New+Activity+with+Intent+Extras.mp4",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Sending+Data+to+a+New+Activity+with+Intent+Extras.png"
            ),
            MediaObject(
                "REST API, Retrofit2, MVVM Course SUMMARY",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/REST+API+Retrofit+MVVM+Course+Summary.mp4",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/REST+API%2C+Retrofit2%2C+MVVM+Course+SUMMARY.png"
            ),
            MediaObject(
                "MVVM and LiveData",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/MVVM+and+LiveData+for+youtube.mp4",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/mvvm+and+livedata.png"
            ),
            MediaObject(
                "Swiping Views with a ViewPager",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/SwipingViewPager+Tutorial.mp4",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Swiping+Views+with+a+ViewPager.png"
            ),
            MediaObject(
                "Database Cache, MVVM, Retrofit, REST API demo for upcoming course",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Rest+api+teaser+video.mp4",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Rest+API+Integration+with+MVVM.png"
            )
        )
    }

    override fun onDestroyView() {
        binding.rvFeeds.releasePlayer()
        super.onDestroyView()
    }
}