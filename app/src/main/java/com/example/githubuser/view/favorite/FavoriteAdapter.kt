package com.example.githubuser.view.favorite

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.data.database.User
import com.example.githubuser.databinding.FragmentAdapterUsersBinding

class FavoriteAdapter(
    private var users: ArrayList<User>,
    private val context: Context,
    private var listener: OnAdapterListener
) :
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

            holder.binding.containerRoot.setOnClickListener {
                listener.onClick(user)
            }
        }
    }

    override fun getItemCount() = users.size

    interface OnAdapterListener {
        fun onClick(result: User)
    }

    inner class ViewHolder(val binding: FragmentAdapterUsersBinding) :
        RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listUsers: List<User>) {
        users.clear()
        users.addAll(listUsers)
        notifyDataSetChanged()
    }
}