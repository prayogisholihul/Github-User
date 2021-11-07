package com.example.githubuser.view.detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubuser.view.detail.follower.FollowerFragment
import com.example.githubuser.view.detail.following.FollowingFragment

class TabAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, user: String) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val getData = user

    private val fragments: ArrayList<Fragment> = arrayListOf(
        FollowerFragment(),
        FollowingFragment()
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FollowerFragment.passDataToFollowerFrag(getData)
            else -> FollowingFragment.passDataToFollowingFrag(getData)
        }
    }
}
