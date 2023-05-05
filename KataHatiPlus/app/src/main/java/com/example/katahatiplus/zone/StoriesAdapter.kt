package com.example.katahatiplus.zone

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.katahatiplus.activity.DetailActivity
import com.example.katahatiplus.databinding.ItemStoriesBinding
import com.example.katahatiplus.response.ListStoryItem

class StoriesAdapter(private val listStories: List<ListStoryItem>) : RecyclerView.Adapter<StoriesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listStories[position]
        holder.bind(item)
    }

    override fun getItemCount() = listStories.size

    class ViewHolder(private val binding: ItemStoriesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ListStoryItem) {
            binding.apply {
                binding.tvItemName.text = item.name
                Glide.with(binding.root)
                    .load(item.photoUrl)
                    .into(ivItemPhoto)
                binding.tvItemDesc.text = item.description
            }

            binding.root.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.KEY, item.id)
                itemView.context.startActivity(intent)
            }
        }

    }





}