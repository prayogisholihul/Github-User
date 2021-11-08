package com.example.githubuser.view.detail.following

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.data.response.FollowingResponse
import com.example.githubuser.databinding.FragmentAdapterUsersBinding

class FollowingAdapter(
    private var followingArrayList: ArrayList<FollowingResponse>,
    private val context: Context
) :
    RecyclerView.Adapter<FollowingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            FragmentAdapterUsersBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val following = followingArrayList[position]

        holder.binding.apply {
            Glide.with(context)
                .load(following.avatar_url)
                .circleCrop()
                .into(imageView)

            username.text = following.login
            nodeId.text = following.node_id
        }
    }

    override fun getItemCount() = followingArrayList.size

    class ViewHolder(val binding: FragmentAdapterUsersBinding) :
        RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<FollowingResponse>?) {
        followingArrayList.clear()
        if (data != null) {
            followingArrayList.addAll(data)
        }
        notifyDataSetChanged()
    }
}
