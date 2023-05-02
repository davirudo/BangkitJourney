package com.example.katahatiplus.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.katahatiplus.databinding.ActivityDetailBinding
import com.example.katahatiplus.model.DetailViewModel
import com.example.katahatiplus.response.Story
import com.example.katahatiplus.utils.SessionManager

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

        detailViewModel.detailUser.observe(this) {
            setUserDetail(it.story)
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setUserDetail(detail: Story) {
        binding.apply {
            binding.tvDetailName.text = detail.name
            Glide.with(binding.root)
                .load(detail.photoUrl)
                .into(ivDetailPhoto)
            binding.tvDetailDescription.text = detail.description
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