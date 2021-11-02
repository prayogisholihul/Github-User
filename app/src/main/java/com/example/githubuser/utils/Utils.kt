package com.example.githubuser.utils

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast

class Utils {
    fun showToast(ctx: Context, text: String) {
        Toast.makeText(ctx, text, Toast.LENGTH_LONG).show()
    }

    fun showLoading(view: ProgressBar) {
        view.visibility = View.VISIBLE
    }

    fun hideLoading(view: ProgressBar) {
        view.visibility = View.GONE
    }
}
