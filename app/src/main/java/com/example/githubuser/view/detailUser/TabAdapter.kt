package com.example.githubuser.view.detailUser

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubuser.view.detailUser.follower.FollowerFragment
import com.example.githubuser.view.detailUser.following.FollowingFragment

class TabAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, user: String) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val getdata = user

    private val fragments: ArrayList<Fragment> = arrayListOf(
        FollowerFragment(),
        FollowingFragment()
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FollowerFragment.passDataToFollowerFrag(getdata)
            else -> FollowingFragment.passDataToFollowingFrag(getdata)
        }
    }
}
