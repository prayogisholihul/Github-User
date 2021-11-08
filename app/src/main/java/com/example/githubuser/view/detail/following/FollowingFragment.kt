package com.example.githubuser.view.detail.following

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.githubuser.R
import com.example.githubuser.databinding.FragmentFollowerAndFollowingBinding
import com.example.githubuser.utils.Resource
import com.example.githubuser.utils.Utils.hideLoading
import com.example.githubuser.utils.Utils.showLoading
import com.example.githubuser.utils.Utils.showToast
import com.example.githubuser.view.detail.follower.FollowerFragment
import com.skydoves.bundler.bundle
import com.skydoves.bundler.intentOf
import org.koin.androidx.viewmodel.ext.android.viewModel

class FollowingFragment : Fragment(R.layout.fragment_follower_and_following) {

    private val binding by viewBinding<FragmentFollowerAndFollowingBinding>()
    private val viewModel: FollowingViewModel by viewModel()
    private val getUser: String? by bundle(GET_USER)
    private lateinit var followingAdapter: FollowingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchFollowing(getUser.toString())
        setupObserver()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupObserver() {
        viewModel.followingResponse.observe(this, {
            when (it) {
                is Resource.Loading -> {
                    showLoading(binding.loading)
                }
                is Resource.Success -> {
                    hideLoading(binding.loading)
                    followingAdapter.setData(it.data)
                    if (it.data.isNullOrEmpty()) {
                        binding.tvNoDataFound.visibility = View.VISIBLE
                    }
                }
                is Resource.Error -> {
                    showToast(requireContext(), "Data Can't be Loaded")
                }
            }
        })
    }

    private fun setupView() {
        followingAdapter = FollowingAdapter(
            arrayListOf(), requireContext(),
        )

        binding.rvList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = followingAdapter
        }
    }

    companion object {
        private const val GET_USER = "USER"

        fun passDataToFollowingFrag(text: String? = null) =
            FollowerFragment().apply {
                arguments = intentOf {
                    +(GET_USER to text)
                }.extras
            }
    }
}
