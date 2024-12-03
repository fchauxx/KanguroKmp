package com.insurtech.kanguro.ui.custom

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReviewManagerWrapper @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    private lateinit var manager: ReviewManager
    var reviewInfo: ReviewInfo? = null

    fun setReviews(context: Context) {
        manager = ReviewManagerFactory.create(context)
        manager.requestReviewFlow().addOnCompleteListener { request ->
            if (request.isSuccessful) {
                reviewInfo = request.result
            }
        }
    }

    private fun askForReview(activity: Activity) {
        if (reviewInfo != null) {
            reviewInfo?.let {
                manager.launchReviewFlow(activity, it)
            }
        }
    }

    fun showReview(activity: Activity) {
        val count = sharedPreferences.getInt(REQUEST_APP_REVIEW, 0)
        if (count == 0) {
            sharedPreferences.edit { putInt(REQUEST_APP_REVIEW, 1) }
        }

        if (sharedPreferences.getInt(REQUEST_APP_REVIEW, 0) == 6) {
            askForReview(activity)
        } else {
            var increaseCount = sharedPreferences.getInt(REQUEST_APP_REVIEW, 0)
            increaseCount += 1

            sharedPreferences.edit { putInt(REQUEST_APP_REVIEW, increaseCount) }
        }
    }

    companion object {
        const val REQUEST_APP_REVIEW = "request_app_review"
    }
}
