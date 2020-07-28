package ru.tjournal.presentation.feature.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import ru.tjournal.R
import ru.tjournal.databinding.FragmentFeedBinding
import ru.tjournal.presentation.feature.feed.adapter.FeedAdapter
import ru.tjournal.presentation.feature.feed.model.FeedModel

class FeedFragment : Fragment() {

    private lateinit var feedViewModel: FeedViewModel
    private lateinit var binding: FragmentFeedBinding
    private val feedAdapter = FeedAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFeedBinding.inflate(inflater, container, false)
//        feedViewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvFeeds.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = feedAdapter
        }

        val data = arrayListOf(
            FeedModel("Заголовок 1", "https://media.giphy.com/media/rFAYqK1jRxZEQ/giphy.gif"),
            FeedModel("Заголовок 2", "https://media.giphy.com/media/IvjjgsEhnLCzm/giphy.gif"),
            FeedModel("Заголовок 3", "https://media.giphy.com/media/rFAYqK1jRxZEQ/giphy.gif"),
            FeedModel("Заголовок 4", "https://media.giphy.com/media/IvjjgsEhnLCzm/giphy.gif")
        )

        feedAdapter.setData(data)
    }
}