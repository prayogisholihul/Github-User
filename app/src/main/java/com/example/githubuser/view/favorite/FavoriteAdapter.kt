package com.example.githubuser.view.favorite

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.data.database.User
import com.example.githubuser.databinding.FragmentAdapterUsersBinding

class FavoriteAdapter(private var users: ArrayList<User>, private val context: Context) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            FragmentAdapterUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]

        holder.binding.apply {
            Glide.with(context)
                .load(user.avatarUrl)
                .circleCrop()
                .into(imageView)

            username.text = user.login
            nodeId.text = user.nodeId
        }
    }

    override fun getItemCount() = users.size

    inner class ViewHolder(val binding: FragmentAdapterUsersBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun setData(listUsers: List<User>) {
        val diffCallback = UserDiffCallback(users, listUsers)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        users.clear()
        users.addAll(listUsers)
        diffResult.dispatchUpdatesTo(this)
    }
}