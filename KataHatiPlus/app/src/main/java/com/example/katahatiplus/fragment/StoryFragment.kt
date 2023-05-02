package com.example.katahatiplus.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.katahatiplus.activity.LoginActivity
import com.example.katahatiplus.adapter.StoriesAdapter
import com.example.katahatiplus.databinding.FragmentStoryBinding
import com.example.katahatiplus.model.StoriesViewModel
import com.example.katahatiplus.response.ListStoryItem
import com.example.katahatiplus.utils.SessionManager

class StoryFragment : Fragment() {

    private lateinit var binding: FragmentStoryBinding
    private val storiesViewModel: StoriesViewModel by lazy {
        ViewModelProvider(this)[StoriesViewModel::class.java]
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

        sessionManager = SessionManager(requireContext())
        val token = LoginActivity.sessionManager.getString("TOKEN")
        storiesViewModel.getAllStories(token.toString())

        storiesViewModel.listStory.observe(viewLifecycleOwner) {
            getAllStories(it)
        }

        storiesViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvStory.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvStory.addItemDecoration(itemDecoration)

    }

    private fun getAllStories(data: List<ListStoryItem>) {
        val adapter = StoriesAdapter(data)
        binding.rvStory.adapter = adapter
        }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        lateinit var sessionManager: SessionManager
        private lateinit var context: Context
    }

}