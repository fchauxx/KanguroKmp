package com.insurtech.kanguro.ui.scenes.welcome

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class WelcomeOnboardingCarouselAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return WelcomeOnboardingCarouselItemFragment.newInstance(position)
    }
}
