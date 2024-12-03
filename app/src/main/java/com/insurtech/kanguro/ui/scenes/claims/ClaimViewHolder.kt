package com.insurtech.kanguro.ui.scenes.claims

import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.insurtech.kanguro.databinding.LayoutClosedClaimBinding
import com.insurtech.kanguro.databinding.LayoutDraftClaimBinding
import com.insurtech.kanguro.databinding.LayoutOpenClaimBinding
import com.insurtech.kanguro.domain.coverage.Claim
import com.insurtech.kanguro.domain.model.ReimbursementProcess

sealed class ClaimViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(item: Claim)
}

class OpenClaimViewHolder(
    private val binding: LayoutOpenClaimBinding,
    private val callback: (View, Claim) -> Unit,
    private val onDirectPayToVetPressed: (Claim) -> Unit
) :
    ClaimViewHolder(binding.root) {
    override fun bind(item: Claim) {
        val isVeterinarianReimbursement =
            item.reimbursementProcess == ReimbursementProcess.VeterinarianReimbursement

        binding.claim = item

        binding.directPayYourVet.apply {
            isVisible = isVeterinarianReimbursement
            setOnClickListener {
                onDirectPayToVetPressed(item)
            }
        }

        binding.bottomSpacer.isVisible = !isVeterinarianReimbursement

        binding.claimDetails.setOnClickListener {
            callback(it, item)
        }
        binding.executePendingBindings()
    }
}

class ClosedClaimViewHolder(
    private val binding: LayoutClosedClaimBinding,
    private val callback: (View, Claim) -> Unit
) : ClaimViewHolder(binding.root) {
    override fun bind(item: Claim) {
        binding.apply {
            claim = item
            claimDetails.setOnClickListener {
                callback(it, item)
            }
        }
    }
}

class DraftClaimViewHolder(
    private val binding: LayoutDraftClaimBinding,
    private val callback: (View, Claim) -> Unit
) : ClaimViewHolder(binding.root) {
    override fun bind(item: Claim) {
        binding.apply {
            claim = item
            moreOptions.setOnClickListener {
                callback(it, item)
            }
        }
    }
}
