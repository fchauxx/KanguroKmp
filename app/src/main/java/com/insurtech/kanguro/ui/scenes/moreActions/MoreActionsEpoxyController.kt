package com.insurtech.kanguro.ui.scenes.moreActions

import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.composableInterop
import com.insurtech.kanguro.*
import com.insurtech.kanguro.designsystem.ui.composables.more.sections.MoreEmergencySection
import com.insurtech.kanguro.domain.dashboard.Action
import com.insurtech.kanguro.ui.epoxy.moreActionsSection
import com.insurtech.kanguro.ui.epoxy.supportActionsSection
import com.insurtech.kanguro.ui.eventHandlers.MoreActionsListItemHandler
import com.insurtech.kanguro.ui.eventHandlers.ReferFriendsHandler

class MoreActionsEpoxyController(
    private val context: Context,
    private val moreActionsListItemHandler: MoreActionsListItemHandler,
    private val referFriendsHandler: ReferFriendsHandler
) : EpoxyController() {

    var termsOfUseLoading: Boolean = false
        set(value) {
            field = value
            requestModelBuild()
        }

    var shouldShowRenters: Boolean = false
        set(value) {
            field = value
            requestModelBuild()
        }

    var userHasPets: Boolean = false
        set(value) {
            field = value
            requestModelBuild()
        }

    var shouldShowLiveVet: Boolean = false
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        val preferencesActions = if (shouldShowRenters) {
            listOf(
                Action.Profile,
                Action.Reminders,
                Action.PaymentSettings,
                Action.ReferAFriend,
                Action.SupportCause,
                Action.Settings
            )
        } else {
            listOf(
                Action.Profile,
                Action.Reminders,
                Action.PaymentSettings,
                Action.ReferAFriend,
                Action.SupportCause,
                Action.Settings
            )
        }

        moreActionsSection(
            context = context,
            moreActionsList = preferencesActions,
            sectionName = R.string.preferences
        ) {
            this@MoreActionsEpoxyController.moreActionsListItemHandler.onClickMoreActionsItem(it)
        }

        if (!shouldShowRenters) {
            dashboardBannerRefer {
                val clickAction = View.OnClickListener {
                    this@MoreActionsEpoxyController.referFriendsHandler.onClickReferFriends()
                }
                id("banner")
                onClickAction(clickAction)
            }
        }

        val supportActions = if (shouldShowRenters) {
            listOf(
                Action.ContactUs
            )
        } else {
            listOf(
                Action.Phone,
                Action.VetAdvice,
                Action.FrequentQuestions,
                Action.NewPetParents
            )
        }

        composableInterop("emergencySection") {
            if (userHasPets && shouldShowLiveVet) {
                MoreEmergencySection(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .padding(top = 32.dp)
                ) {
                    moreActionsListItemHandler.onClickMoreActionsItem(Action.LiveVet)
                }
            }
        }

        supportActionsSection(
            context = context,
            moreActionsList = supportActions,
            sectionName = R.string.support
        ) {
            this@MoreActionsEpoxyController.moreActionsListItemHandler.onClickMoreActionsItem(it)
        }

        dashboardSectionItem {
            id(this@MoreActionsEpoxyController.context.getString(R.string.legal).hashCode())
            sectionName(this@MoreActionsEpoxyController.context.getString(R.string.legal))
        }

        moreActionsLoadableItem {
            id(Action.PrivacyPolicy.hashCode())
            action(Action.PrivacyPolicy)
            onClickAction(
                View.OnClickListener {
                    this@MoreActionsEpoxyController.moreActionsListItemHandler.onClickMoreActionsItem(
                        Action.PrivacyPolicy
                    )
                }
            )
            isLoading(false)
            backgroundTint(Color.WHITE)
        }

        moreActionsLoadableItem {
            id(Action.TermsOfUse.hashCode())
            action(Action.TermsOfUse)
            onClickAction(
                View.OnClickListener {
                    this@MoreActionsEpoxyController.moreActionsListItemHandler.onClickMoreActionsItem(
                        Action.TermsOfUse
                    )
                }
            )
            isLoading(this@MoreActionsEpoxyController.termsOfUseLoading)
            backgroundTint(Color.WHITE)
        }

        moreActionsSection(
            context = context,
            moreActionsList = listOf(Action.Logout),
            sectionName = R.string.no_title
        ) {
            this@MoreActionsEpoxyController.moreActionsListItemHandler.onClickMoreActionsItem(it)
        }

        appVersion {
            id(0)
            version("${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})")
        }
    }
}
