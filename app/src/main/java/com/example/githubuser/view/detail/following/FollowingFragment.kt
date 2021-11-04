package com.example.githubuser.view.detail.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.databinding.FragmentFollowingBinding
import com.example.githubuser.utils.Resource
import com.example.githubuser.utils.Utils.hideLoading
import com.example.githubuser.utils.Utils.showLoading
import com.example.githubuser.utils.Utils.showToast
import com.example.githubuser.view.detail.follower.FollowerFragment
import com.skydoves.bundler.bundle
import com.skydoves.bundler.intentOf
import org.koin.androidx.viewmodel.ext.android.viewModel

class FollowingFragment : Fragment() {

    private var binding: FragmentFollowingBinding? = null
    private val mbinding get() = binding!!
    private val viewModel: FollowingViewModel by viewModel()
    private val getUser: String? by bundle(GET_USER)
    private lateinit var followingAdapter: FollowingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFollowingBinding.inflate(layoutInflater, container, false)
        return mbinding.root
    }

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
                    context?.showLoading(binding!!.loading)
                }
                is Resource.Success -> {
                    context?.hideLoading(binding!!.loading)
                    followingAdapter.setData(it.data!!)
                    if (it.data.isNullOrEmpty()) {
                        mbinding.tvNoDataFound.visibility = View.VISIBLE
                    }
                }
                is Resource.Error -> {
                    context?.showToast(requireContext(), "Data Can't be Loaded")
                }
            }
        })
    }

    private fun setupView() {
        followingAdapter = FollowingAdapter(
            arrayListOf(), requireContext(),
        )

        mbinding.rvFollower.apply {
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