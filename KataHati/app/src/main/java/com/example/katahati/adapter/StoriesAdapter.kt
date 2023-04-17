package com.example.katahati.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.katahati.databinding.ItemStoriesBinding
import com.example.katahati.response.ListStoryItem

class StoriesAdapter(private val listStories: List<ListStoryItem>) : RecyclerView.Adapter<StoriesAdapter.ViewHoolder>() {


    class ViewHoolder(private val binding: ItemStoriesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ListStoryItem) {
            binding.apply {
                Glide.with(binding.root)
                    .load(item.photoUrl)
                    .into(ivStories)
                binding.tvStories.text = item.name
            }

            //abis ini set on buat detail
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoriesAdapter.ViewHoolder {
        val binding = ItemStoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHoolder(binding)
    }

    override fun getItemCount(): Int {
        return listStories.size
    }

    override fun onBindViewHolder(holder: StoriesAdapter.ViewHoolder, position: Int) {
        val item = listStories[position]
        holder.bind(item)
    }

}