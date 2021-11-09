package com.example.githubuser.view.favorite

import androidx.recyclerview.widget.DiffUtil
import com.example.githubuser.data.database.User

class UserDiffCallback(private val mOldUserList: List<User>, private val mNewUserList: List<User>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldUserList.size
    }

    override fun getNewListSize(): Int {
        return mNewUserList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldUserList[oldItemPosition].id == mNewUserList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = mOldUserList[oldItemPosition]
        val newItem = mNewUserList[newItemPosition]
        return oldItem.login == newItem.login && oldItem.name == newItem.name
    }
}