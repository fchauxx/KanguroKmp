package com.insurtech.kanguro.ui.scenes.claims

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.insurtech.kanguro.R
import com.insurtech.kanguro.common.enums.ClaimStatus
import com.insurtech.kanguro.databinding.LayoutClosedClaimBinding
import com.insurtech.kanguro.databinding.LayoutDraftClaimBinding
import com.insurtech.kanguro.databinding.LayoutOpenClaimBinding
import com.insurtech.kanguro.domain.coverage.Claim

class ClaimsAdapter(
    private val callback: ((View, Claim) -> Unit),
    private val onDirectPayToVetPressed: (Claim) -> Unit
) :
    ListAdapter<Claim, ClaimViewHolder>(ClaimDiffCallback) {

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position]?.status) {
            ClaimStatus.Closed, ClaimStatus.Approved, ClaimStatus.Denied, ClaimStatus.Paid -> CLOSED_CLAIM
            ClaimStatus.Draft -> DRAFT_CLAIM
            else -> OPEN_CLAIM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClaimViewHolder {
        return when (viewType) {
            OPEN_CLAIM -> {
                val binding = LayoutOpenClaimBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                OpenClaimViewHolder(binding, callback, onDirectPayToVetPressed)
            }

            CLOSED_CLAIM -> {
                val binding = LayoutClosedClaimBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ClosedClaimViewHolder(binding, callback)
            }

            else -> {
                val binding = LayoutDraftClaimBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                DraftClaimViewHolder(binding, callback)
            }
        }
    }

    override fun onBindViewHolder(holder: ClaimViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}

const val OPEN_CLAIM = R.layout.layout_open_claim
const val CLOSED_CLAIM = R.layout.layout_closed_claim
const val DRAFT_CLAIM = R.layout.layout_draft_claim

object ClaimDiffCallback : DiffUtil.ItemCallback<Claim>() {
    override fun areItemsTheSame(oldItem: Claim, newItem: Claim) = oldItem === newItem

    override fun areContentsTheSame(oldItem: Claim, newItem: Claim) = oldItem == newItem
}
