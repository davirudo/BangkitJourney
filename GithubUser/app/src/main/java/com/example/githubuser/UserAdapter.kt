package com.example.githubuser

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.githubuser.databinding.ItemUserBinding


class UserAdapter(private val listUser: ArrayList<ItemsItem>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = listUser[position]
            holder.bind(item)

//            holder.itemView.setOnClickListener{
//                val intent = Intent(holder.itemView.context, DetailUserActivity::class.java)
//                intent.putExtra("dapit", item.login)
//                holder.itemView.context.startActivities(intent)
//            }
    }

    override fun getItemCount() = listUser.size

    class ViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemsItem) {
            binding.apply {
                Glide.with(binding.root)
                    .load(item.avatarUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .circleCrop()
                    .into(imgItemPhoto)
                binding.tvItemName.text = item.login
            }
        }
    }

}