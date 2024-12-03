package com.insurtech.kanguro.common.enums

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.insurtech.kanguro.common.R

enum class CharityCause(@StringRes val title: Int, @DrawableRes val icon: Int) {
    Animals(R.string.animals, R.drawable.ic_animals),
    GlobalWarming(R.string.global_warming, R.drawable.ic_global),
    SocialCauses(R.string.social_causes, R.drawable.ic_social_causes),
    LatinCommunities(R.string.latin_communities, R.drawable.ic_people)
}
