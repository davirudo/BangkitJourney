package com.example.githubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.response.ItemsItem
import com.example.githubuser.adapter.UserAdapter
import com.example.githubuser.databinding.ActivityFavBinding
import com.example.githubuser.model.FavViewModel
import com.example.githubuser.model.ViewModelFactory

class FavActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavBinding
    private lateinit var favViewModel: FavViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvFav.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)

        binding.rvFav.addItemDecoration(itemDecoration)

        val favViewModel = getFavViewModel(this@FavActivity)
        favViewModel.getAllFav().observe(this) { favList ->
            if (favList != null) {
                val items = arrayListOf<ItemsItem>()
                for (fav in favList) {
                    val item = ItemsItem( login = fav.username,
                        avatarUrl = fav.avatar_url.toString(),
                    )
                    items.add(item)
                }
                binding.rvFav.adapter = UserAdapter(items)
            }
        }
    }

    private fun getFavViewModel(activity: AppCompatActivity): FavViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavViewModel::class.java]
    }
}