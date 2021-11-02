package com.example.githubuser.view.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.data.response.SearchUserResponse
import com.example.githubuser.databinding.FragmentAdapterUsersBinding
import timber.log.Timber

class AdapterUsers(
    private var users: ArrayList<SearchUserResponse.Items>,
    private val context: Context,
    private var listener: OnAdapterListener
) :
    RecyclerView.Adapter<AdapterUsers.ViewHolder>(), Filterable {

    var usersFilter = ArrayList<SearchUserResponse.Items>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            FragmentAdapterUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userToView = usersFilter[position]
        holder.binding.username.text = userToView.login
        holder.binding.nodeId.text = userToView.node_id

        // Picture
        Glide.with(context)
            .load(userToView.avatar_url)
            .circleCrop()
            .into(holder.binding.imageView)

        holder.binding.containerRoot.setOnClickListener {
            listener.onClick(userToView)
        }
    }

    override fun getItemCount() = usersFilter.size

    class ViewHolder(val binding: FragmentAdapterUsersBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun setDataSearch(data: List<SearchUserResponse.Items>) {
        users.clear()
        users.addAll(data)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(result: SearchUserResponse.Items)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                Timber.d("charSearch: $charSearch")
                usersFilter = if (charSearch.isEmpty()) {
                    users
                } else {
                    val userFiltered = ArrayList<SearchUserResponse.Items>()
                    for (user in users) {
                        if (user.login.toLowerCase().contains(charSearch.toLowerCase())) {
                            userFiltered.add(user)
                        }
                    }
                    userFiltered
                }
                val userFilteredResult = FilterResults()
                userFilteredResult.values = usersFilter
                return userFilteredResult
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                usersFilter = results?.values as ArrayList<SearchUserResponse.Items>
                notifyDataSetChanged()
            }
        }
    }
}
