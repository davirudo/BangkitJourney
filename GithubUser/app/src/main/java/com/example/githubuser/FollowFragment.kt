package com.example.githubuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.databinding.FragmentFollowBinding

class FollowFragment : Fragment() {

    lateinit var binding: FragmentFollowBinding
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        detailViewModel = ViewModelProvider(requireActivity())[DetailViewModel::class.java]
        detailViewModel.isLoading.observe(viewLifecycleOwner, {
            showLoading(it)
        })
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
            username?.let { detailViewModel.detailFollower(it)}
            detailViewModel.followerUser.observe(viewLifecycleOwner, {
                userData(it)
            })
        } else {
            username?.let{ detailViewModel.detailFollowed(it)}
            detailViewModel.followedUser.observe(viewLifecycleOwner, {
                userData(it)
            })
        }
    }

    private fun userData(listUser: List<ItemsItem>) {
        binding.apply {
            binding.rvFollow.layoutManager = LinearLayoutManager(requireActivity())
            val UserAdapter = UserAdapter(listUser)
            binding.rvFollow.adapter = UserAdapter
        }
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val ARG_USERNAME = "section_username"
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}