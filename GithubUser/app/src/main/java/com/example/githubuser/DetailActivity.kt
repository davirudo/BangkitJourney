package com.example.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.githubuser.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    companion object {
        const val KEY = "key_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        var getID = intent.getStringExtra(KEY)


        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)

        getID?.let { detailViewModel.detailUser(it)}
        detailViewModel.detailUser.observe(this, {
            setUserDetail(it)
        })
        detailViewModel.isLoading.observe(this, {
            showLoading(it)
        })



    }

    private fun setUserDetail(detail: DetailResponse) {
        binding.apply {
            Glide.with(binding.root)
                .load(detail.avatarUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(detailAvatar)
            tvNama.text = detail.name
            tvId.text = detail.login
            tvAngkaFollower.text = detail.followers.toString()
            tvAngkaFollowed.text = detail.following.toString()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}