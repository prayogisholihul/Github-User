package com.example.githubuser.view.detail.follower

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.githubuser.R
import com.example.githubuser.databinding.FragmentFollowerAndFollowingBinding
import com.example.githubuser.utils.Resource
import com.example.githubuser.utils.Utils.showToast
import com.example.githubuser.utils.Utils.viewGone
import com.example.githubuser.utils.Utils.viewVisible
import com.skydoves.bundler.bundle
import com.skydoves.bundler.intentOf
import org.koin.androidx.viewmodel.ext.android.viewModel

class FollowerFragment : Fragment(R.layout.fragment_follower_and_following) {
    private val binding by viewBinding<FragmentFollowerAndFollowingBinding>()
    private val viewModel: FollowerViewModel by viewModel()
    private val getUser: String? by bundle(GET_USER)
    private lateinit var followerAdapter: FollowerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchFollower(getUser.toString())
        setupObserver()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupObserver() {
        viewModel.followerResponse.observe(this, {
            when (it) {
                is Resource.Loading -> {
                    viewVisible(binding.loading)
                }
                is Resource.Success -> {
                    viewGone(binding.loading)
                    followerAdapter.setData(it.data)
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
        followerAdapter = FollowerAdapter(
            arrayListOf(), requireContext(),
        )

        binding.rvList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = followerAdapter
        }
    }

    companion object {
        private const val GET_USER = "USER"

        fun passDataToFollowerFrag(text: String? = null) =
            FollowerFragment().apply {
                arguments = intentOf {
                    +(GET_USER to text)
                }.extras
            }
    }
}
