package com.example.githubuser.view.detail

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.data.response.DetailResponse
import com.example.githubuser.databinding.ActivityProfileBinding
import com.example.githubuser.utils.Resource
import com.example.githubuser.utils.Utils.hideLoading
import com.example.githubuser.utils.Utils.showLoading
import com.example.githubuser.utils.Utils.showToast
import com.google.android.material.tabs.TabLayoutMediator
import com.skydoves.bundler.bundle
import com.skydoves.bundler.intentOf
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity(R.layout.activity_profile) {

    private val binding by viewBinding<ActivityProfileBinding>()
    private val viewModel: DetailViewModel by viewModel()
    private val getData: String? by bundle(PASS_DATA)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            title = getString(R.string.profile_detail_label)
        }
        viewModel.fetchDetailUser(getData.toString())
        setupObserver()
        setupTab()
    }

    private fun setupObserver() {
        viewModel.detailResponse.observe(this, {
            when (it) {
                is Resource.Loading -> {
                    showLoading(binding.loading)
                }
                is Resource.Success -> {
                    hideLoading(binding.loading)
                    setupView(it.data)
                    favoriteClick(it.data)
                }
                is Resource.Error -> {
                    showToast(this, "Data Can't be Loaded")
                }
            }
        })
    }

    private fun setupView(data: DetailResponse?) {
        binding.apply {
            Glide.with(this@DetailActivity)
                .load(data?.avatar_url)
                .circleCrop()
                .into(profilePicture)

            profileName.text = data?.login
            profileFullName.text = data?.name
        }
    }

    private fun setupTab() {
        val tabTitles =
            arrayOf(getString(R.string.follower_label), getString(R.string.following_label))
        val tabAdapter = TabAdapter(supportFragmentManager, lifecycle, getData!!)
        binding.viewPager.adapter = tabAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

    private fun favoriteClick(data: DetailResponse?) = with(binding) {
        fabFavorite.setOnClickListener {
            if (data != null) {
                viewModel.insert(data)
                showToast(this@DetailActivity, "Added To Favorite")
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val PASS_DATA = "USERDATA"

        fun Context.launchDetail(text: String? = null) = intentOf<DetailActivity> {
            +(PASS_DATA to text)
            startActivity(this@launchDetail)
        }
    }
}
