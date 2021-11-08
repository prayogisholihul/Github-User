package com.example.githubuser.utils

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast

object Utils {
    fun showToast(ctx: Context, text: String) {
        Toast.makeText(ctx, text, Toast.LENGTH_SHORT).show()
    }

    fun showLoading(view: ProgressBar) {
        view.visibility = View.VISIBLE
    }

    fun hideLoading(view: ProgressBar) {
        view.visibility = View.GONE
    }

    fun viewVisible(view: View) {
        view.visibility = View.VISIBLE
    }

    fun viewGone(view: View) {
        view.visibility = View.GONE
    }
}
