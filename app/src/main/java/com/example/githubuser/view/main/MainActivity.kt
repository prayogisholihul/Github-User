package com.example.githubuser.view.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.BuildConfig
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.githubuser.R
import com.example.githubuser.data.response.SearchUserResponse
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.utils.Resource
import com.example.githubuser.utils.Utils.showToast
import com.example.githubuser.utils.Utils.viewGone
import com.example.githubuser.utils.Utils.viewVisible
import com.example.githubuser.view.detail.DetailActivity.Companion.launchDetail
import com.example.githubuser.view.favorite.FavoriteActivity.Companion.launchFavorite
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding<ActivityMainBinding>()
    private val viewModel: MainViewModel by viewModel()
    private lateinit var adapterUsers: AdapterUsers

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                viewModel.fetchSearchUser(query.toString())
                Handler(Looper.getMainLooper()).postDelayed({
                    adapterUsers.filter.filter(query)
                }, 1000)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun setupObserver() {
        viewModel.userSearch.observe(this, {
            when (it) {
                is Resource.Loading -> {
                    viewGone(binding.tvNoDataFound)
                    viewVisible(binding.progressBar)
                }
                is Resource.Success -> {
                    viewGone(binding.tvNoDataFound)
                    viewGone(binding.progressBar)
                    Timber.d("${it.data}")
                    adapterUsers.setDataSearch(it.data?.items)
                    if (it.data?.total_count == 0) {
                        showToast(this, "Data not found")
                    }
                }
                is Resource.Error -> {
                    showToast(this, "Data Can't be Loaded")
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        lifecycleScope.launch {
            val isChecked = viewModel.getUIMode.first()
            val item = menu.findItem(R.id.theme)
            item.isChecked = isChecked
            setUIMode(item, isChecked)
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.theme -> {
                item.isChecked = !item.isChecked
                setUIMode(item, item.isChecked)
                true
            }
            R.id.favorite -> {
                this.launchFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setUIMode(item: MenuItem, isChecked: Boolean) {
        if (isChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            viewModel.saveToDataStore(true)
            item.setIcon(R.drawable.ic_day)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            viewModel.saveToDataStore(false)
            item.setIcon(R.drawable.ic_dark)
        }
    }
}
