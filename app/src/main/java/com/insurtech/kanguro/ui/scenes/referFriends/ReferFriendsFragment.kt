package com.insurtech.kanguro.ui.scenes.referFriends

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.session.ISessionManager
import com.insurtech.kanguro.core.utils.openPdf
import com.insurtech.kanguro.core.utils.safeNavigate
import com.insurtech.kanguro.databinding.BottomsheetReferFriendsBinding
import com.insurtech.kanguro.ui.custom.KanguroBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ReferFriendsFragment : KanguroBottomSheetFragment<BottomsheetReferFriendsBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.ReferFriends

    override val viewModel: ReferFriendsViewModel by viewModels()

    @Inject
    lateinit var sessionManager: ISessionManager

    override fun onCreateBinding(inflater: LayoutInflater) =
        BottomsheetReferFriendsBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getColoredText()
        setupListeners()
        referFriends()
        setTermsServiceListener()
        setCodeButton()
    }

    private fun setupListeners() {
        binding.referCloseButton.setOnClickListener {
            dismiss()
        }

        binding.referTermsOfService.setOnClickListener {
            findNavController().safeNavigate(
                ReferFriendsFragmentDirections.actionGlobalTermsAndConditions()
            )
        }
    }

    private fun setCodeButton() {
        binding.referShareButton.text = sessionManager.sessionInfo?.referralCode
    }

    private fun setTermsServiceListener() {
        viewModel.openUseTermsEvent.observe(viewLifecycleOwner) {
            it?.let { requireContext().openPdf(it) }
        }
    }

    @SuppressLint("StringFormatMatches")
    private fun referFriends() {
        val user = sessionManager.sessionInfo?.givenName ?: return
        val code = sessionManager.sessionInfo?.referralCode ?: return
        binding.referShareButton.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(
                Intent.EXTRA_TEXT,
                getString(R.string.share_code_message, user, code)
            )
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, R.string.share_to.toString()))
        }
    }

    @SuppressLint("StringFormatInvalid")
    private fun getColoredText() {
        val giftCard = getString(R.string.amazon_gift_card)
        binding.referContent.text = getString(R.string.refer_a_friend)

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

        binding.referContent.text = toSpan
    }
}
