package com.insurtech.kanguro.ui.scenes.welcome

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class WelcomeCarouselAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return WelcomeCarouselItemFragment.newInstance(position)
    }
}
