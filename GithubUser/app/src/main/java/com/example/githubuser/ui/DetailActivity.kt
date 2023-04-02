package com.example.githubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.adapter.SectionsPagerAdapter
import com.example.githubuser.database.Fav
import com.example.githubuser.response.DetailResponse
import com.example.githubuser.databinding.ActivityDetailBinding
import com.example.githubuser.model.DetailViewModel
import com.example.githubuser.model.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }
    private var isFav = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val getID = intent.getStringExtra(KEY)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getID?.let { detailViewModel.detailUser(it)}
        detailViewModel.detailUser.observe(this) {
            setUserDetail(it)
        }
        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = getID.toString()

        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    private fun setUserDetail(detail: DetailResponse) {
        binding.apply {
            Glide.with(binding.root)
                .load(detail.avatarUrl)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var getID = intent.getStringExtra(KEY)
        showLoading(true)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_fav, menu)

        if (getID != null) {
            detailViewModel.getFavByUser(getID).observe(this) {
                val fav = menu?.findItem(R.id.favDetailBtn)
                if (it != null) {
                    fav?.icon = ContextCompat.getDrawable(this, R.drawable.ic_fav_filled)
                    isFav = true
                } else {
                    fav?.icon = ContextCompat.getDrawable(this, R.drawable.ic_fav)
                    isFav = false
                }
            }
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val Get = intent.getStringExtra(KEY)

        when (item.itemId) {
            R.id.favDetailBtn -> {
                var avatarUrl : String? = null
                detailViewModel.detailUser.observe(this) { detailUser ->
                    avatarUrl = detailUser.avatarUrl
                }

                val favorite = Get?.let { user ->
                    Fav(
                        user,
                        avatarUrl.toString(),
                    )
                }

                if (isFav) {
                    detailViewModel.deleteFav(favorite as Fav)
                } else {
                    detailViewModel.addFav(favorite as Fav)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val KEY = "key_name"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}