package com.insurtech.kanguro.ui.epoxy

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.airbnb.epoxy.ModelCollector
import com.insurtech.kanguro.R
import com.insurtech.kanguro.dashboardSectionItem
import com.insurtech.kanguro.domain.dashboard.Action
import com.insurtech.kanguro.moreActionsItem

fun ModelCollector.moreActionsSection(
    context: Context,
    background: Int = R.color.white,
    moreActionsList: List<Action>,
    sectionName: Int = R.string.more_actions,
    onActionSelected: (Action) -> Unit
) {
    dashboardSectionItem {
        id(context.getString(sectionName).hashCode())
        sectionName(context.getString(sectionName))
    }

    moreActionsList.forEach { action ->
        moreActionsItem {
            id(action.hashCode())
            action(action)
            onClickAction(View.OnClickListener { onActionSelected(action) })
            backgroundTint(ContextCompat.getColor(context, background))
            isHighlighted(action.isHighlighted())
            highlightedLabel(action.highlightedLabel()?.let { context.getString(it) })
        }
    }
}
