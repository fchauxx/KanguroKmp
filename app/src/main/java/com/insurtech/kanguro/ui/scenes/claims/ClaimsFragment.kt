package com.insurtech.kanguro.ui.scenes.claims

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.PopupWindow
import android.widget.Toast
import androidx.core.graphics.Insets
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.common.enums.ClaimStatus
import com.insurtech.kanguro.core.base.FullscreenFragment
import com.insurtech.kanguro.databinding.DraftOptionsMenuBinding
import com.insurtech.kanguro.databinding.FragmentClaimsBinding
import com.insurtech.kanguro.domain.coverage.*
import dagger.hilt.android.AndroidEntryPoint

@Deprecated("This class is deprecated. Use TrackYourClaimFragment instead")
@AndroidEntryPoint
class ClaimsFragment : FullscreenFragment<FragmentClaimsBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.Claims

    override var lightSystemBarTint: Boolean = true

    private val claimsAdapter = ClaimsAdapter(
        this@ClaimsFragment::handleClick,
        this@ClaimsFragment::onDirectPayToVetPressed
    )

    override val viewModel: ClaimsViewModel by viewModels()

    override fun onCreateBinding(inflater: LayoutInflater) = FragmentClaimsBinding.inflate(inflater)

    override fun processWindowInsets(insets: Insets) {
        binding.root.updatePadding(top = insets.top)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHeader()
        setObservers()
        setRadioGroupSelector()
        setAddButton()
        setClaimsList()

        viewModel.setFilteredList(R.id.openButton)
    }

    private fun setHeader() {
        binding.coverageLabel.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setRadioGroupSelector() {
        binding.claimsOptions.setOnCheckedChangeListener { _, checkedId ->
            viewModel.setFilteredList(checkedId)
        }
    }

    private fun setAddButton() {
        binding.addClaimFab.setOnClickListener {
//            findNavController().safeNavigate(
//                ClaimsFragmentDirections.actionGlobalJavierChatbotFragment(ChatbotType.NewClaim(null))
//            )
        }
    }

    private fun setObservers() {
        viewModel.displayedList.observe(viewLifecycleOwner) { list ->
            claimsAdapter.submitList(list)
            binding.emptyListMessage.isVisible = list.isEmpty()
        }
    }

    private fun setClaimsList() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refreshClaims()
        }

        binding.claimsList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = claimsAdapter
        }
    }

    private fun handleClick(view: View, claim: Claim) {
        if (claim.status == ClaimStatus.Draft) {
            handleDraftClick(view, claim)
        } else {
            openClaimDetail(claim)
        }
    }

    private fun onDirectPayToVetPressed(claim: Claim) {
        claim.id?.let { id ->
//            findNavController().safeNavigate(
//                ClaimsFragmentDirections.actionClaimsFragmentToDirectPayToVetAlmostDone(id)
//            )
        }
    }

    private fun openClaimDetail(claim: Claim) {
//        findNavController().safeNavigate(
//            ClaimsFragmentDirections.actionClaimsFragmentToClaimDetailsBottomSheet(claim)
//        )
    }

    private fun handleDraftClick(view: View, draftClaim: Claim) {
        val binding = DraftOptionsMenuBinding.inflate(LayoutInflater.from(requireContext()))
        binding.root.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)

        val dropdown = PopupWindow(binding.root, WRAP_CONTENT, WRAP_CONTENT, true)

        binding.apply {
            continueOption.setOnClickListener {
                dropdown.dismiss()
                val sessionId =
                    draftClaim.chatbotSessionsIds?.firstOrNull() ?: return@setOnClickListener
//                findNavController().safeNavigate(
//                    ClaimsFragmentDirections.actionGlobalJavierChatbotFragment(
//                        ChatbotType.NewClaim(
//                            sessionId
//                        )
//                    )
//                )
            }

            deleteOption.setOnClickListener {
                dropdown.dismiss()
                Toast.makeText(
                    requireContext(),
                    "Delete ${draftClaim.pet?.name}!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        dropdown.showAsDropDown(view, -350, 5, Gravity.START)
    }
}
