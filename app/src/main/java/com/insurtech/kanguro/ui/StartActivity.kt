package com.insurtech.kanguro.ui

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.activity.viewModels
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.insurtech.kanguro.R
import com.insurtech.kanguro.core.base.FullscreenActivity
import com.insurtech.kanguro.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StartActivity : FullscreenActivity<FragmentSplashBinding>() {

    override val viewModel: StartViewModel by viewModels()

    override fun onCreateBinding(inflater: LayoutInflater): FragmentSplashBinding {
        return FragmentSplashBinding.inflate(inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        handleSplashScreen()
        super.onCreate(savedInstanceState)
        checkUserAuthenticationAndNavigate()
    }

    private fun handleSplashScreen() {
        val splashScreen = installSplashScreen()
        splashScreen.setOnExitAnimationListener { splash ->
            val slideBack = ObjectAnimator.ofFloat(
                splash.view,
                View.ALPHA,
                1f,
                0f
            ).apply {
                interpolator = DecelerateInterpolator()
                duration = 800L
                doOnEnd { splash.remove() }
            }

            slideBack.start()
        }
    }

    private fun checkUserAuthenticationAndNavigate() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                val nextScreen = executeWithMinimumTime(800) {
                    viewModel.getNextScreen()
                }

                binding.motionParent.transitionToEnd()
                delay(binding.motionParent.transitionTimeMs)

                navigateToNextScreen(nextScreen)
            }
        }
    }

    private suspend fun <T> executeWithMinimumTime(minimumTimeMs: Long, block: suspend () -> T): T {
        val startTime = System.currentTimeMillis()

        val result = block()

        val elapsedTime = System.currentTimeMillis() - startTime
        if (elapsedTime < minimumTimeMs) delay(minimumTimeMs - elapsedTime)

        return result
    }

    private fun navigateToNextScreen(nextScreen: StartViewModel.NextScreen) {
        val destination = when (nextScreen) {
            StartViewModel.NextScreen.LOGIN -> {
                Intent(this, LoginActivity::class.java)
            }
            StartViewModel.NextScreen.HOME_DASHBOARD -> Intent(this, MainActivity::class.java)
        }
        readDeepLinks(destination)
        startActivity(destination)
        overridePendingTransition(R.anim.fade_in, R.anim.no_anim)
        finish()
    }

    private fun readDeepLinks(destination: Intent) {
        intent.data?.let {
            when (it.path) {
                LOGIN_PATH -> {
                    val email: String? = it.getQueryParameter(DEEP_EMAIL)
                    destination.putExtra(DEEP_EMAIL, email)
                }
                FILE_CLAIM_PATH -> {
                    destination.putExtra(IS_FILE_CLAIM, true)
                }
                else -> {}
            }
        }
    }

    companion object {
        const val DEEP_EMAIL = "email"
        const val IS_FILE_CLAIM = "isFileClaim"
        const val LOGIN_PATH = "/login"
        const val FILE_CLAIM_PATH = "/file_claim"
        fun newInstance(context: Context) = Intent(context, StartActivity::class.java)
    }
}
