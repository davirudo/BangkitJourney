package com.example.katahatiplus.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.katahatiplus.activity.LoginActivity
import com.example.katahatiplus.adapter.LoadingStateAdapter
import com.example.katahatiplus.adapter.StoriesAdapterPaging
import com.example.katahatiplus.databinding.FragmentStoryBinding
import com.example.katahatiplus.model.StoriesViewModelPaging
import com.example.katahatiplus.model.ViewModelFactory
import com.example.katahatiplus.utils.SessionManager

class StoryFragment : Fragment() {

    private lateinit var binding: FragmentStoryBinding
    private val storiesViewModel: StoriesViewModelPaging by viewModels {
        ViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val token = LoginActivity.sessionManager.getString("TOKEN")

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvStory.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvStory.addItemDecoration(itemDecoration)

        getData(token.toString())
    }

    private fun getData(token: String) {
        val adapter = StoriesAdapterPaging()
        binding.rvStory.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        storiesViewModel.getStories(token).observe(viewLifecycleOwner) { getStories ->
            adapter.submitData(lifecycle, getStories)
        }
    }

    companion object {
        lateinit var sessionManager: SessionManager
    }
}