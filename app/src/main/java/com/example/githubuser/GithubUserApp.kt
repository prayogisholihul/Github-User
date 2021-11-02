package com.example.githubuser

import android.app.Application
import com.example.githubuser.data.Repository
import com.example.githubuser.data.api.ApiNetwork
import com.example.githubuser.view.detailUser.DetailViewModel
import com.example.githubuser.view.detailUser.follower.FollowerViewModel
import com.example.githubuser.view.detailUser.following.FollowingViewModel
import com.example.githubuser.view.main.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class GithubUserApp : Application() {

    private val viewModelModule = module {
        viewModel {
            MainViewModel(get())
        }
        viewModel {
            DetailViewModel(get())
        }
        viewModel {
            FollowerViewModel(get())
        }
        viewModel {
            FollowingViewModel(get())
        }
    }

    private val api = module {
        single {
            ApiNetwork.getClient()
        }
    }

    private val repo = module {
        single { Repository(get()) }
    }

    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin {
            androidLogger()
            androidContext(this@GithubUserApp)
            modules(listOf(viewModelModule, api, repo))
        }
    }
}
