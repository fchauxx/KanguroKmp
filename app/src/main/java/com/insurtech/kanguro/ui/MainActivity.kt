package com.insurtech.kanguro.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.graphics.Insets
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.messaging.FirebaseMessaging
import com.insurtech.kanguro.NavDashboardDirections
import com.insurtech.kanguro.R
import com.insurtech.kanguro.core.base.FullscreenActivity
import com.insurtech.kanguro.core.base.GenericBroadcastReceiver
import com.insurtech.kanguro.core.interceptors.BroadcastUnauthorizedInterceptor
import com.insurtech.kanguro.core.session.ISessionManager
import com.insurtech.kanguro.core.utils.*
import com.insurtech.kanguro.databinding.ActivityMainBinding
import com.insurtech.kanguro.ui.custom.ReviewManagerWrapper
import com.insurtech.kanguro.ui.scenes.dashboard.DashboardFragment
import com.insurtech.kanguro.ui.scenes.dashboard.DashboardFragmentDirections
import com.insurtech.kanguro.ui.scenes.dashboard.DashboardViewModel
import com.insurtech.kanguro.ui.scenes.javier.ChatbotType
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : FullscreenActivity<ActivityMainBinding>() {

    override val enforceNavigationBarVisibility: Boolean = true

    @Inject
    lateinit var preferences: SharedPreferences

    @Inject
    lateinit var sessionManager: ISessionManager

    @Inject
    lateinit var reviewManager: ReviewManagerWrapper

    private var unauthorizedResponseBroadcastReceiver: BroadcastReceiver? = null

    private val userLoggedOffBroadcastReceiver = GenericBroadcastReceiver {
        disconnectUserAndFinish()
    }

    private val onNewFirebaseTokenBroadcastReceiver = GenericBroadcastReceiver {
        viewModel.sendToken()
    }

    private var isJavierVisible = true

    override val viewModel: DashboardViewModel by viewModels()

    private lateinit var navController: NavController

    override fun onCreateBinding(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onShouldShowRentersObserver()

        if (sessionManager.sessionInfo == null) {
            startActivity(StartActivity.newInstance(this))
            finish()
        }

        setupBroadcastReceivers()
        setReviewApp()

        verifyAccountBlocked()

        isAppUpdatedObserver()
        appLanguageObserver()
    }

    override fun onDestroy() {
        unauthorizedResponseBroadcastReceiver?.let {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(it)
        }

        LocalBroadcastManager.getInstance(this).unregisterReceiver(userLoggedOffBroadcastReceiver)

        LocalBroadcastManager.getInstance(this)
            .unregisterReceiver(onNewFirebaseTokenBroadcastReceiver)

        super.onDestroy()
    }

    private fun appLanguageObserver() {
        viewModel.updateAppLanguage.observe(this) { appLanguage ->
            sessionManager.sessionInfo = sessionManager.sessionInfo?.copy(language = appLanguage)
            LanguageUtils.switchLanguage(preferences, this, appLanguage)
        }
    }

    private fun verifyAccountBlocked() {
        sessionManager.sessionInfo?.id?.let { idUser ->
            viewModel.userHasAccountBlocked(idUser)
            viewModel.isAccountBlocked.observe(this) {
                if (it) {
                    navController.safeNavigate(
                        NavDashboardDirections.actionGlobalLoginBlockedFragment(
                            DashboardFragment.IS_LOGGED
                        )
                    )
                    FirebaseMessaging.getInstance().deleteToken()
                    sessionManager.sessionInfo = null
                }
            }
        }
    }

    private fun isAppUpdatedObserver() {
        viewModel.isAppUpdated.observe(this) { isAppUpdated ->
            if (!isAppUpdated) {
                navController.safeNavigate(DashboardFragmentDirections.actionGlobalUpdateAppFragment())
            }
        }
    }

    private fun onShouldShowRentersObserver() {
        viewModel.getShouldShowRenters().observe(this) { showRenters ->
            initNavigation(showRenters)
            setupBottomNavigation(showRenters)
        }
    }

    private fun initNavigation(showRenters: Boolean) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        val inflater = navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_home)

        if (showRenters) {
            graph.setStartDestination(R.id.homeDashboardFragment)
        } else {
            graph.setStartDestination(R.id.dashboardFragment)
        }

        navController.graph = graph
    }

    private fun setupBottomNavigation(showRenters: Boolean) {
        binding.bottomNavigationView.menu.also {
            it.itemIconTintList = null
            it.setOnApplyWindowInsetsListener(null)
            it.setupWithNavController(navController)
            it.inflateMenu(
                if (showRenters) {
                    R.menu.renters_dashboard_menu
                } else {
                    R.menu.dashboard_menu
                }
            )
            if (!showRenters) {
                it.setOnItemSelectedListener(::onBottomMenuItemSelected)
                it.menu.getItem(2).isVisible = JAVIER_ENABLED
            }
        }
        binding.centerJavier.isVisible = if (showRenters) {
            isJavierVisible = false
            false
        } else {
            JAVIER_ENABLED
        }
    }

    private fun setupBroadcastReceivers() {
        unauthorizedResponseBroadcastReceiver =
            BroadcastUnauthorizedInterceptor.registerUnauthorizedRequestReceiver(
                LocalBroadcastManager.getInstance(this),
                ::onUnauthorizedBroadcastReceived
            )

        userLoggedOffBroadcastReceiver.registerForAction(
            this,
            KanguroConstants.BROADCAST_USER_LOGGED_OFF
        )

        onNewFirebaseTokenBroadcastReceiver.registerForAction(
            this,
            KanguroConstants.BROADCAST_ON_NEW_FIREBASE_TOKEN
        )
    }

    private fun onUnauthorizedBroadcastReceived() {
        FirebaseMessaging.getInstance().deleteToken()
        sessionManager.sessionInfo = null
        MaterialAlertDialogBuilder(this)
            .setTitle("Login expired!")
            .setMessage("Please, login again.")
            .setCancelable(false)
            .setPositiveButton(R.string.ok) { _, _ ->
                finish()
                startActivity(intent)
            }
            .show()
    }

    private fun onBottomMenuItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.javierChatFragment -> {
            navController.safeNavigate(
                NavDashboardDirections.actionGlobalJavierChatbotFragment(ChatbotType.Generic)
            )
            false
        }

        else -> {
            item.onNavDestinationSelected(navController)
            true
        }
    }

    private fun disconnectUserAndFinish() {
        FirebaseMessaging.getInstance().deleteToken()
        sessionManager.sessionInfo = null
        finish()
        startActivity(newInstance(this))
    }

    private fun setReviewApp() {
        val minutes: Long = 6000
        val countDownInterval: Long = 1000

        object : CountDownTimer(minutes, countDownInterval) {
            override fun onTick(p0: Long) {
            }

            override fun onFinish() {
                reviewManager.showReview(this@MainActivity)
            }
        }.start()
    }

    fun updateBottomNavigationVisibility(isVisible: Boolean) {
        binding.toolbarGroup.isVisible = isVisible
        binding.centerJavier.isVisible = isVisible and JAVIER_ENABLED and isJavierVisible
    }

    /**
     * We always add the bottom insets to the bottomNavigation.
     * If the menu is visible, we consume the insets (so fragments wont add the bottom spacing twice)
     * Otherwise, we just return the original insets so the fragments can consume it.
     */
    override fun onProcessWindowInsets(insets: Insets): Insets {
        binding.bottomNavigationView.menu.updatePadding(bottom = insets.bottom)
        return if (binding.bottomNavigationView.menu.isVisible) {
            insets.copy(bottom = 0)
        } else {
            insets
        }
    }

    companion object {
        private const val JAVIER_ENABLED = true
        fun newInstance(context: Context) = Intent(context, MainActivity::class.java)
    }
}
