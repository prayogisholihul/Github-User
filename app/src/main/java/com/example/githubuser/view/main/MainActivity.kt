package com.example.githubuser.view.main

import android.os.Bundle
import android.os.Handler
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.BuildConfig
import com.example.githubuser.data.response.SearchUserResponse
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.utils.Resource
import com.example.githubuser.utils.Utils
import com.example.githubuser.view.detailUser.DetailActivity.Companion.launchDetail
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel: MainViewModel by viewModel()
    private lateinit var adapterUsers: AdapterUsers
    private val utils = Utils()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        setupObserver()
        setupListener()
        setupView()
    }

    private fun setupView() {
        adapterUsers = AdapterUsers(
            arrayListOf(), this,
            object : AdapterUsers.OnAdapterListener {
                override fun onClick(result: SearchUserResponse.Items) {
                    this@MainActivity.launchDetail(result.login)
                }
            }
        )
        binding.RV.layoutManager = LinearLayoutManager(this)
        binding.RV.adapter = adapterUsers
    }

    private fun setupListener() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.fetchSearchUser(query!!)
                Handler().postDelayed({ adapterUsers.filter.filter(query) }, 1000)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun setupObserver() {
        viewModel.userSearchResponse.observe(this, {
            when (it) {
                is Resource.Loading -> {
                    utils.showLoading(binding.progressBar)
                }
                is Resource.Success -> {
                    utils.hideLoading(binding.progressBar)
                    Timber.d("${it.data}")
                    adapterUsers.setDataSearch(it.data?.items!!)
                    if (it.data.total_count == 0) {
                        utils.showToast(this, "Data not found")
                    }
                }
                is Resource.Error -> {
                    utils.showToast(this, "Data Can't be Loaded")
                }
            }
        })
    }
}
