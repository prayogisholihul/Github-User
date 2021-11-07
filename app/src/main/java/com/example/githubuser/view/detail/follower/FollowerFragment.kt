package com.example.githubuser.view.detail.follower

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.githubuser.R
import com.example.githubuser.databinding.FragmentFollowerBinding
import com.example.githubuser.utils.Resource
import com.example.githubuser.utils.Utils.hideLoading
import com.example.githubuser.utils.Utils.showLoading
import com.example.githubuser.utils.Utils.showToast
import com.skydoves.bundler.bundle
import com.skydoves.bundler.intentOf
import org.koin.androidx.viewmodel.ext.android.viewModel

class FollowerFragment : Fragment(R.layout.fragment_follower) {
    private val binding by viewBinding<FragmentFollowerBinding>()
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
                    showLoading(binding.loading)
                }
                is Resource.Success -> {
                    hideLoading(binding.loading)
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

        binding.rvFollower.apply {
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
