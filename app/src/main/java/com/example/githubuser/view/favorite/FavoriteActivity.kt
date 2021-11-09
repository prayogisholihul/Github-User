package com.example.githubuser.view.favorite

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.githubuser.R
import com.example.githubuser.data.database.User
import com.example.githubuser.databinding.ActivityFavoriteBinding
import com.example.githubuser.utils.Utils.viewGone
import com.example.githubuser.utils.Utils.viewVisible
import com.example.githubuser.view.detail.DetailActivity.Companion.launchDetail
import com.skydoves.bundler.intentOf
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteActivity : AppCompatActivity(R.layout.activity_favorite) {
    private val binding by viewBinding<ActivityFavoriteBinding>()
    private val viewModel: FavoriteViewModel by viewModel()
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            title = getString(R.string.favorite_title)
        }
        setObserver()
    }

    private fun setObserver() {
        viewModel.getAllNotes().observe(this, { userList ->
            if (userList.isNullOrEmpty()) {
                viewVisible(binding.tvNoDataFound)
            } else {
                viewGone(binding.tvNoDataFound)
                setRV(userList)
            }
        })
    }

    private fun setRV(userList: List<User>) {
            adapter = FavoriteAdapter(arrayListOf(), this, object : FavoriteAdapter.OnAdapterListener {
                override fun onClick(result: User) {
                    this@FavoriteActivity.launchDetail(result.login)
                }
            })
            binding.rvList.layoutManager = LinearLayoutManager(this)
            binding.rvList.adapter = adapter
            adapter.setData(userList)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun Context.launchFavorite() = intentOf<FavoriteActivity> {
            startActivity(this@launchFavorite)
        }
    }
}