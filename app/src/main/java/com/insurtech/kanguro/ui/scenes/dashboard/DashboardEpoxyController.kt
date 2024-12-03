package com.insurtech.kanguro.ui.scenes.dashboard

import android.content.Context
import android.view.View
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.lifecycle.LifecycleCoroutineScope
import com.airbnb.epoxy.*
import com.insurtech.kanguro.*
import com.insurtech.kanguro.R
import com.insurtech.kanguro.core.session.ISessionManager
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.AdvertiserCardCarousel
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.AdvertiserCardModels
import com.insurtech.kanguro.designsystem.ui.theme.spacingXxxs
import com.insurtech.kanguro.domain.dashboard.Action
import com.insurtech.kanguro.domain.dashboard.LastActivity
import com.insurtech.kanguro.domain.dashboard.Reminder
import com.insurtech.kanguro.domain.model.PetPolicy
import com.insurtech.kanguro.ui.epoxy.moreActionsSection
import com.insurtech.kanguro.ui.eventHandlers.DashboardListHandler
import com.insurtech.kanguro.ui.scenes.coverages.CoverageFilterItemViewHolder_
import com.insurtech.kanguro.ui.scenes.coverages.CoverageItemViewHolder_
import com.insurtech.kanguro.ui.scenes.reminders.ReminderListItemViewHolder_

class DashboardEpoxyController(
    private val context: Context,
    private val dashboardListHandler: DashboardListHandler,
    private val sessionManager: ISessionManager,
    private val lifecycleScope: LifecycleCoroutineScope
) : EpoxyController() {

    private var coverageList: List<PetPolicy> = emptyList()

    private var petNames: List<String> = emptyList()

    private var remindersList: List<Reminder> = emptyList()

    private var activityList: List<LastActivity> = emptyList()

    private var isVisible: Boolean = false

    private val moreActionsList = listOf(
        Action.FileClaim,
        Action.TrackClaims,
        Action.DirectPayYourVet,
        Action.GetQuote,
        Action.VetAdvice,
        Action.FindVet,
        Action.Blog,
        Action.FrequentQuestions,
        Action.NewPetParents
    )

    private val hPadding =
        context.resources.getDimension(R.dimen.dashboard_horizontal_padding).toInt()
    private val topCarouselPadding =
        context.resources.getDimension(R.dimen.top_carousel_padding).toInt()
    private val bottomCarouselPadding =
        context.resources.getDimension(R.dimen.bottom_carousel_padding).toInt()
    private val dashboardCarouselItemSpacing =
        context.resources.getDimension(R.dimen.dash_carousel_item_spacing).toInt()

    private val carouselPadding = Carousel.Padding(
        hPadding,
        topCarouselPadding,
        hPadding,
        bottomCarouselPadding,
        dashboardCarouselItemSpacing
    )

    fun setCoveragesList(coverages: List<PetPolicy>, isVisible: Boolean) {
        this.isVisible = isVisible
        coverageList = coverages
        requestModelBuild()
    }

    fun setPetNames(pets: List<String>) {
        petNames = pets
        requestModelBuild()
    }

    fun setRemindersList(reminders: List<Reminder>) {
        remindersList = reminders
        requestModelBuild()
    }

    fun setLastActivitiesList(lastActivities: List<LastActivity>) {
        activityList = lastActivities
        requestModelBuild()
    }

    override fun buildModels() {
        DashboardHeaderViewHolder_(lifecycleScope, context).apply {
            id("header")
            username(this@DashboardEpoxyController.sessionManager.sessionInfo?.givenName)
            petNames(this@DashboardEpoxyController.petNames)
        }.addTo(this)

        DashboardSectionItemBindingModel_().apply {
            id("reminderTitle")
            sectionName(this@DashboardEpoxyController.context.getString(R.string.reminders_section))
            val clickAction = View.OnClickListener {
                this@DashboardEpoxyController.dashboardListHandler.onClickSeeAllReminders()
            }

            onClickAction(clickAction)
        }.addIf(remindersList.isNotEmpty(), this)

        carousel {
            id("reminderCarousel")
            padding(this@DashboardEpoxyController.carouselPadding)

            val reminderItems = this@DashboardEpoxyController.remindersList.map { reminder ->
                ReminderListItemViewHolder_().apply {
                    id("reminder${reminder.hashCode()}")

                    reminder(reminder)

                    onClickAction {
                        this@DashboardEpoxyController.dashboardListHandler.onClickRemindersItem(
                            reminder
                        )
                    }
                }
            }

            models(reminderItems)
        }

        DashboardSectionItemBindingModel_().apply {
            id("title1")
            sectionName(this@DashboardEpoxyController.context.getString(R.string.coverage_menu))
        }.addTo(this)

        CoverageFilterItemViewHolder_().apply {
            id("CoverageItem")
            filterVisible(this@DashboardEpoxyController.isVisible)

            clickListener { filter ->
                this@DashboardEpoxyController.dashboardListHandler.onClickFilterCoverages(
                    filter
                )
            }
        }.addTo(this)

        carousel {
            id("carousel")
            padding(this@DashboardEpoxyController.carouselPadding)
            onBind { model, view, position ->
                view.post {
                    view.scrollToPosition(0)
                }
            }

            val carouselItems =
                this@DashboardEpoxyController.coverageList.mapIndexed { index, coverage ->
                    CoverageItemViewHolder_().apply {
                        id("Carousel${coverage.hashCode()}")
                        policy(coverage)
                        transitionName("coverage_$index")

                        clickListener { coverage, imageView ->
                            this@DashboardEpoxyController.dashboardListHandler.onClickPolicyItem(
                                coverage,
                                imageView
                            )
                        }
                    }
                } + CoveragesAddBindingModel_().apply {
                    id("add")
                    clickListener {
                        this@DashboardEpoxyController.dashboardListHandler.onClickAddCoverageItem()
                    }
                }
            models(carouselItems)
        }

        composableInterop("advertiserCarousel") {
            AdvertiserCardCarousel(
                modifier = Modifier.padding(top = spacingXxxs),
                defaultOnClickCard = { this@DashboardEpoxyController.dashboardListHandler.onClickAdvertising(it) },
                cardList = mutableStateListOf(
                    AdvertiserCardModels.Roam,
                    AdvertiserCardModels.MissingPets
                )
            )
        }

        donationBanner {
            id("donationBanner")
            modifier(Modifier)
            onClick {
                this@DashboardEpoxyController.dashboardListHandler.onDonationPressed()
            }
        }

        dashboardBannerRefer {
            val clickAction = View.OnClickListener {
                this@DashboardEpoxyController.dashboardListHandler.onClickReferFriends()
            }

            id("banner")
            onClickAction(clickAction)
        }

        moreActionsSection(context = context, moreActionsList = moreActionsList) {
            this@DashboardEpoxyController.dashboardListHandler.onClickMoreActionsItem(it)
        }
    }
}

inline fun <T> CarouselModelBuilder.withModelsFrom(
    items: List<T>,
    modelBuilder: (T) -> EpoxyModel<*>
) {
    models(items.map { modelBuilder(it) })
}
