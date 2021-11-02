package com.example.githubuser.view.detailUser.follower

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.data.response.FollowerResponse
import com.example.githubuser.databinding.FragmentFollowerAdapterBinding

class FollowerAdapter(private var followerArrayList: ArrayList<FollowerResponse>, val context: Context) :
    RecyclerView.Adapter<FollowerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            FragmentFollowerAdapterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val follower = followerArrayList[position]

        holder.binding.apply {
            Glide.with(context)
                .load(follower.avatar_url)
                .circleCrop()
                .into(imageView)

            username.text = follower.login
            nodeId.text = follower.node_id
        }
    }

    override fun getItemCount() = followerArrayList.size

    class ViewHolder(val binding: FragmentFollowerAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun setData(data: List<FollowerResponse>) {
        followerArrayList.clear()
        followerArrayList.addAll(data)
        notifyDataSetChanged()
    }
}
