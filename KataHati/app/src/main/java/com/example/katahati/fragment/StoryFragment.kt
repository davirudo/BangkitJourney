package com.example.katahati.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.katahati.adapter.StoriesAdapter
import com.example.katahati.databinding.FragmentStoryBinding
import com.example.katahati.model.StoriesViewModel
import com.example.katahati.response.ListStoryItem

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

        storiesViewModel.listStory.observe(viewLifecycleOwner) {
            setUserData(it)
        }

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvStory.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvStory.addItemDecoration(itemDecoration)

        storiesViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun setUserData(data: List<ListStoryItem>) {
        val adapter = StoriesAdapter(data)
        binding.rvStory.adapter = adapter
        }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}