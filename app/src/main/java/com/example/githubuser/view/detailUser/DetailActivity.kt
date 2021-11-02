package com.example.githubuser.view.detailUser

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.githubuser.data.response.DetailResponse
import com.example.githubuser.databinding.ActivityProfileBinding
import com.example.githubuser.utils.Resource
import com.example.githubuser.utils.Utils
import com.google.android.material.tabs.TabLayoutMediator
import com.skydoves.bundler.bundle
import com.skydoves.bundler.intentOf
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private val binding by lazy { ActivityProfileBinding.inflate(layoutInflater) }
    private val viewModel: DetailViewModel by viewModel()
    private val getData: String? by bundle(PASSDATA)
    private val utils = Utils()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            title = "Profile Detail"
        }
        viewModel.fetchDetailUser(getData!!)
        setupObserver()
        setupTab()
    }

    private fun setupObserver() {
        viewModel.detailResponse.observe(this, {
            when (it) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    setupView(it.data!!)
                }
                is Resource.Error -> {
                    utils.showToast(this, "Data Can't be Loaded")
                }
            }
        })
    }

    private fun setupView(data: DetailResponse) {
        binding.apply {
            Glide.with(this@DetailActivity)
                .load(data.avatar_url)
                .circleCrop()
                .into(profilePicture)

            profileName.text = data.login
            profileFullName.text = data.name
        }
    }

    private fun setupTab() {
        val tabTitles = arrayOf("Follower", "Following")
        val tabAdapter = TabAdapter(supportFragmentManager, lifecycle, getData!!)
        binding.viewPager.adapter = tabAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val PASSDATA = "USERDATA"

        fun Context.launchDetail(text: String? = null) = intentOf<DetailActivity> {
            +(PASSDATA to text)
            startActivity(this@launchDetail)
        }
    }
}
