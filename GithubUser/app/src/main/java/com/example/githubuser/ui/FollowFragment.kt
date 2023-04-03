package com.example.githubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.ItemsItem
import com.example.githubuser.adapter.UserAdapter
import com.example.githubuser.databinding.FragmentFollowBinding
import com.example.githubuser.model.DetailViewModel

class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        detailViewModel = ViewModelProvider(requireActivity())[DetailViewModel::class.java]
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var username = arguments?.getString(ARG_USERNAME)
        var position = 0

        arguments?.let {
            position = it.getInt(ARG_SECTION_NUMBER)
            username = it.getString(ARG_USERNAME)
        }
        if (position == 1){
            showLoading(true)
            username?.let { detailViewModel.detailFollower(it)}
            detailViewModel.followerUser.observe(viewLifecycleOwner){
                if (it != null) {
                    userData(it)
                }
                showLoading(false)
            }
        } else {
            showLoading(true)
            username?.let{ detailViewModel.detailFollowed(it)}
            detailViewModel.followedUser.observe(viewLifecycleOwner){
                if (it != null) {
                    userData(it)
                }
                showLoading(false)
            }
        }
    }

    private fun userData(listUser: List<ItemsItem>) {
        binding.apply {
            binding.rvFollow.layoutManager = LinearLayoutManager(requireActivity())
            val userAdapter = UserAdapter(listUser)
            binding.rvFollow.adapter = userAdapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarFollow.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val ARG_USERNAME = "section_username"
    }



}