package com.insurtech.kanguro.ui.scenes.reminders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.load.model.GlideUrl
import com.google.android.material.chip.Chip
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.utils.getCacheableImage
import com.insurtech.kanguro.core.utils.getPlaceholderImage
import com.insurtech.kanguro.core.utils.safeNavigate
import com.insurtech.kanguro.databinding.FragmentRemindersBinding
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ScreenLoader
import com.insurtech.kanguro.domain.dashboard.Reminder
import com.insurtech.kanguro.domain.dashboard.ReminderType
import com.insurtech.kanguro.ui.custom.GlideChip
import com.insurtech.kanguro.ui.custom.KanguroBottomSheetFragment
import com.insurtech.kanguro.ui.scenes.dashboard.DashboardFragmentDirections
import com.insurtech.kanguro.ui.scenes.dashboard.DashboardViewModel
import com.insurtech.kanguro.ui.scenes.javier.ChatbotType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RemindersFragment : KanguroBottomSheetFragment<FragmentRemindersBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.Reminders

    private val dashboardViewModel: DashboardViewModel by activityViewModels()

    override val viewModel: RemindersViewModel by viewModels()

    private lateinit var adapter: RemindersAdapter

    override fun onCreateBinding(inflater: LayoutInflater) =
        FragmentRemindersBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.closeButton.setOnClickListener { dismiss() }
        setupListeners()
        composeLoading()
        initIfRentersOn()
    }

    private fun initIfRentersOn() {
        dashboardViewModel.getShouldShowRenters().observe(viewLifecycleOwner) {
            if (it) {
                dashboardViewModel.remindersRefresh()
            }
        }
    }

    private fun setupListeners() {
        adapter = RemindersAdapter(::onReminderPressed)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewModel.filteredReminders.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.noRemindersLabel.isVisible = it.isEmpty()
                adapter.submitList(it)
            }
        }
        dashboardViewModel.remindersList.observe(viewLifecycleOwner) { allReminders ->
            viewModel.setReminders(allReminders)
            setupFilterChipGroup(allReminders)
        }
        dashboardViewModel.remindersState.observe(viewLifecycleOwner) { state ->
            binding.recyclerView.isVisible = state is DashboardViewModel.State.Data
            binding.filterChips.isVisible = state is DashboardViewModel.State.Data
            binding.subtitle.isVisible = state is DashboardViewModel.State.Data
        }
    }

    private fun composeLoading() {
        binding.composeLoader.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val isLoading = dashboardViewModel.isRemindersLoading.collectAsState()
                if (isLoading.value) {
                    ScreenLoader()
                }
            }
        }
    }

    private fun onReminderPressed(reminder: Reminder) {
        when (reminder.type) {
            ReminderType.MedicalHistory -> {
                findNavController().safeNavigate(
                    DashboardFragmentDirections.actionGlobalMedicalHistoryChatbotFragment(
                        ChatbotType.CompleteMedicalHistory(reminder.pet.id ?: return)
                    )
                )
            }

            ReminderType.Claim -> {
                if (reminder.claimId != null) {
                    findNavController().safeNavigate(
                        DashboardFragmentDirections.actionGlobalCommunicationChatbotFragment(
                            reminder.claimId
                        )
                    )
                }
            }

            else -> {
                // Left empty on purpose
            }
        }
    }

    private fun setupFilterChipGroup(allReminders: List<Reminder>) {
        binding.filterChips.removeAllViews()

        binding.filterChips.addView(
            inflateGlideChip(
                "All",
                RemindersViewModel.FILTER_ALL,
                onChecked = viewModel::clearRemindersFilter
            ).also {
                binding.filterChips.post { it.isChecked = true }
            }
        )

        // Pets
        allReminders.groupBy { it.pet }.keys.forEach {
            val petId = it.id ?: return@forEach
            val petName = it.name ?: return@forEach
            binding.filterChips.addView(
                inflateGlideChip(
                    text = petName,
                    tag = petId,
                    image = it.getCacheableImage(),
                    placeholder = it.getPlaceholderImage(),
                    onChecked = { viewModel.updateSelectedFilter(petId) }
                )
            )
        }
    }

    private fun inflateGlideChip(
        text: String,
        tag: Long,
        image: GlideUrl? = null,
        placeholder: Int? = null,
        onChecked: () -> Unit
    ): GlideChip {
        return (Chip.inflate(requireContext(), R.layout.chip_layout, null) as GlideChip).apply {
            this.text = text
            this.tag = tag
            if (placeholder != null) {
                setIconUrl(image, placeholder)
            }
            this.setOnCheckedChangeListener { _, checked ->
                if (checked) onChecked.invoke()
            }
        }
    }
}
