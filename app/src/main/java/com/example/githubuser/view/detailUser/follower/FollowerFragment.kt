package com.example.githubuser.view.detailUser.follower

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.databinding.FragmentFollowerBinding
import com.example.githubuser.utils.Resource
import com.example.githubuser.utils.Utils
import com.example.githubuser.utils.Utils.hideLoading
import com.example.githubuser.utils.Utils.showLoading
import com.example.githubuser.utils.Utils.showToast
import com.skydoves.bundler.bundle
import com.skydoves.bundler.intentOf
import org.koin.androidx.viewmodel.ext.android.viewModel

class FollowerFragment : Fragment() {
    private var binding: FragmentFollowerBinding? = null
    private val mbinding get() = binding!!
    private val viewModel: FollowerViewModel by viewModel()
    private val getUser: String? by bundle(GETUSER)
    private lateinit var followerAdapter: FollowerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFollowerBinding.inflate(layoutInflater, container, false)
        return mbinding.root
    }

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
                    context?.showLoading(binding!!.loading)
                }
                is Resource.Success -> {
                    context?.hideLoading(binding!!.loading)
                    followerAdapter.setData(it.data!!)
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
        followerAdapter = FollowerAdapter(
            arrayListOf(), requireContext(),
        )

        mbinding.rvFollower.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = followerAdapter
        }
    }

    companion object {
        const val GETUSER = "USER"

        fun passDataToFollowerFrag(text: String? = null) =
            FollowerFragment().apply {
                arguments = intentOf {
                    +(GETUSER to text)
                }.extras
            }
    }
}
