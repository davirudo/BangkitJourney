package com.example.katahati.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.katahati.databinding.ActivityDetailBinding
import com.example.katahati.fragment.StoryFragment
import com.example.katahati.model.DetailViewModel
import com.example.katahati.response.DetailResponse
import com.example.katahati.response.Story
import com.example.katahati.utils.SessionManager

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by lazy {
        ViewModelProvider(this)[DetailViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        val token = LoginActivity.sessionManager.getString("TOKEN")
        val getID = intent.getStringExtra(KEY)
        if (getID != null) {
            detailViewModel.getDetailUser(token.toString(), getID)
        }

//        detailViewModel.getDetailUser(token.toString(), intent.getStringExtra(StoryFragment.KEY).toString())
//
//        val getID = intent.getStringExtra(KEY)

        detailViewModel.detailUser.observe(this) {
            setUserDetail(it.story)
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

//        if(getID != null) {
//            showLoading(true)
//            detailViewModel.getDetailUser(getID)
//            showLoading(false)
//        }
//
//        getID?.let { detailViewModel.getDetailUser(it)}
    }

    private fun setUserDetail(detail: Story) {
        binding.apply {
            binding.tvUserDetail.text = detail.name
            Glide.with(binding.root)
                .load(detail.photoUrl)
                .into(ivStoriesDetail)
            binding.tvStoriesDetail.text = detail.description
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val KEY = "key_name"
        lateinit var sessionManager: SessionManager
    }
}