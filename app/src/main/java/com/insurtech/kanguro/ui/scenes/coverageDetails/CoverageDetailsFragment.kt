package com.insurtech.kanguro.ui.scenes.coverageDetails

import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.Insets
import androidx.core.text.buildSpannedString
import androidx.core.text.inSpans
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.base.FullscreenFragment
import com.insurtech.kanguro.core.utils.*
import com.insurtech.kanguro.databinding.FragmentCoverageDetailsBinding
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ScreenLoader
import com.insurtech.kanguro.domain.coverageDetails.GoodBoyHandler
import com.insurtech.kanguro.domain.coverageDetails.PaymentHandler
import com.insurtech.kanguro.domain.coverageDetails.PreventViewHandler
import com.insurtech.kanguro.domain.dashboard.Action
import com.insurtech.kanguro.domain.model.PetPolicy
import com.insurtech.kanguro.domain.model.PolicyDocument
import com.insurtech.kanguro.ui.eventHandlers.MoreActionsListItemHandler
import com.insurtech.kanguro.ui.scenes.javier.ChatbotType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoverageDetailsFragment :
    FullscreenFragment<FragmentCoverageDetailsBinding>(),
    MoreActionsListItemHandler,
    GoodBoyHandler,
    PreventViewHandler {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.CoverageDetails

    private val args: CoverageDetailsFragmentArgs by navArgs()

    private val controller: CoverageDetailsEpoxyController by lazy {
        val paymentHandler = PaymentHandler {
            findNavController().safeNavigate(CoverageDetailsFragmentDirections.actionCoverageDetailsFragmentToBillingPreferencesFragment())
        }

        CoverageDetailsEpoxyController(
            requireContext(),
            this,
            this,
            paymentHandler,
            this,
            args.coverage
        )
    }

    override val viewModel: CoverageDetailsViewModel by viewModels()

    private val contentResolver by lazy {
        requireActivity().contentResolver
    }

    override fun onCreateBinding(inflater: LayoutInflater) =
        FragmentCoverageDetailsBinding.inflate(inflater).apply {
            recyclerView.setControllerAndBuildModels(controller)
        }

    override fun processWindowInsets(insets: Insets) {
        binding.returnButtonFrame.updatePadding(top = insets.top)
        binding.editPictureButtonFrame.updatePadding(top = insets.top)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        setCoverage()
        setObservers()

        binding.loadingComponent.setContent {
            ScreenLoader()
        }

        binding.petImage.post {
            startPostponedEnterTransition()
        }
    }

    private fun setListeners() {
        binding.returnButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.editPictureButton.setOnClickListener {
            PicturePickerOptionBottomSheet.show(this) { pictureUri ->
                if (pictureUri != null) {
                    val petPictureBase64 = pictureUri.getPetPictureBase64(contentResolver)
                    viewModel.updatePetPicture(petPictureBase64)
                }
            }
        }
    }

    private fun setCoverage() {
        viewModel.setCoverage(args.coverage)

        val pet = args.coverage.pet
        if (pet?.petPictureUrl == null) {
            binding.editPictureButton.setImageResource(R.drawable.ic_add_pet_picture)
        } else {
            binding.editPictureButton.setImageResource(R.drawable.ic_edit_picture)
        }
    }

    private fun setObservers() {
        with(viewModel) {
            coverage.observe(viewLifecycleOwner) { setHeaderSpan(it) }
            policyDocuments.observe(viewLifecycleOwner) { controller.documentList = it }
            documentToOpen.observe(viewLifecycleOwner) {
                it.let { requireContext().openPdf(it) }
            }
            documentBeingDownloaded.observe(viewLifecycleOwner) {
                controller.downloadedDocument = it
            }

            isLoading.observe(viewLifecycleOwner) {
                binding.petImage.setImageResource(args.coverage.pet!!.getPlaceholderImage())
            }

            petPicture.observe(viewLifecycleOwner) {
                val placeHolder = args.coverage.pet!!.getPlaceholderImage()
                binding.petImage.setGlideUrlWithPlaceholder(it, placeHolder)
                requireContext().sendLocalBroadcast(KanguroConstants.BROADCAST_ACTION_REFRESH_POLICIES)
            }
        }
    }

    private fun setHeaderSpan(policy: PetPolicy) {
        val petName = policy.pet?.name
        val spanned = buildSpannedString {
            append(getString(R.string.hello_pet))
            append(" ")
            inSpans(
                ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.primary_dark))
            ) {
                append(petName)
            }
        }

        binding.petName.text = spanned
    }

    override fun onClickMoreActionsItem(item: Action) {
        when (item) {
            Action.TrackClaims -> {
                findNavController().safeNavigate(CoverageDetailsFragmentDirections.actionGlobalPetTrackYourClaimsFragment())
            }

            Action.FileClaim -> {
                findNavController().safeNavigate(
                    CoverageDetailsFragmentDirections.actionGlobalJavierChatbotFragment(
                        ChatbotType.NewClaim(
                            null
                        )
                    )
                )
            }

            Action.DirectPayYourVet -> {
                findNavController().safeNavigate(
                    CoverageDetailsFragmentDirections.actionCoverageDetailsFragmentToDirectPayToVetInitFlowFragment()
                )
            }

            else -> {
            }
        }
    }

    override fun onWhatsCoveredPressed() {
        findNavController().safeNavigate(
            CoverageDetailsFragmentDirections.actionCoverageDetailsFragmentToWhatsCoveredFragment(
                args.coverage
            )
        )
    }

    override fun onPolicyDocsPressed() {
    }

    override fun onPolicyDocPressed(document: PolicyDocument) {
        viewModel.downloadPolicyDocument(document)
    }

    override fun preventViewWhatsCoveredPressed() {
        findNavController().safeNavigate(
            CoverageDetailsFragmentDirections
                .actionCoverageDetailsFragmentToWhatsCoveredUsageFragment(args.coverage)
        )
    }
}
