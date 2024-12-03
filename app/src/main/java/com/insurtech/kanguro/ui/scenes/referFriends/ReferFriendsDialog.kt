package com.insurtech.kanguro.ui.scenes.referFriends

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.insurtech.kanguro.R
import com.insurtech.kanguro.core.utils.safeNavigate
import com.insurtech.kanguro.ui.custom.CustomKanguroDialog

class ReferFriendsDialog : CustomKanguroDialog() {

    override fun getTitle(): String {
        return getString(R.string.get_gift_card)
    }

    override fun getMessage(): SpannableString {
        val giftCard = getString(R.string.earn_amazon)
        binding.referContent.text = getString(R.string.inviting_friends)

        val text = binding.referContent.text.toString()
        val startIndex = text.indexOf(giftCard)
        val endIndex = startIndex + giftCard.length

        val toSpan = SpannableString(text)
        toSpan.apply {
            setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.primary_darkest
                    )
                ),
                startIndex,
                endIndex,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )

            setSpan(
                StyleSpan(Typeface.BOLD),
                startIndex,
                endIndex,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
        }

        return toSpan
    }

    override fun getHeaderImage(): Int {
        return R.drawable.img_refer_dog_cat
    }

    override fun onConfirmClicked(view: View) {
        findNavController().safeNavigate(
            ReferFriendsDialogDirections.actionReferFriendsDialogToReferFriendsFragment()
        )
    }
}
