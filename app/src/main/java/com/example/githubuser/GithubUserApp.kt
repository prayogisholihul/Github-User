package com.example.githubuser

import android.app.Application
import androidx.viewbinding.BuildConfig
import com.example.githubuser.data.Repository
import com.example.githubuser.data.api.ApiNetwork
import com.example.githubuser.data.database.UserRoomDatabase
import com.example.githubuser.view.detail.DetailViewModel
import com.example.githubuser.view.detail.follower.FollowerViewModel
import com.example.githubuser.view.detail.following.FollowingViewModel
import com.example.githubuser.view.favorite.FavoriteViewModel
import com.example.githubuser.view.main.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class GithubUserApp : Application() {

    private val viewModelModule = module {
        viewModel {
            MainViewModel(get(), get())
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
        viewModel {
            FavoriteViewModel(get())
        }
    }

    private val single = module {
        single {
            ApiNetwork.getClient()
        }

        single { Repository(get(), get()) }

        single {
            UserRoomDatabase(get())
        }
    }

    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin {
            androidLogger()
            androidContext(this@GithubUserApp)
            modules(listOf(viewModelModule, single))
        }
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
