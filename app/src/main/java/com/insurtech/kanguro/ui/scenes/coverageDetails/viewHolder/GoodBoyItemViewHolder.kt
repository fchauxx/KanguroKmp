package com.insurtech.kanguro.ui.scenes.coverageDetails.viewHolder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.ViewDataBinding
import com.airbnb.epoxy.DataBindingEpoxyModel
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.insurtech.kanguro.R
import com.insurtech.kanguro.common.date.DateUtils.getFormattedLocalDate
import com.insurtech.kanguro.common.enums.PolicyStatus
import com.insurtech.kanguro.databinding.LayoutGoodBoyCardBinding
import com.insurtech.kanguro.databinding.LayoutPolicyDocumentBinding
import com.insurtech.kanguro.domain.coverageDetails.GoodBoyHandler
import com.insurtech.kanguro.domain.model.PetPolicy
import com.insurtech.kanguro.domain.model.PolicyDocument
import com.insurtech.kanguro.domain.policy.getPlanImage
import com.insurtech.kanguro.domain.policy.getPlanName
import java.util.*
import kotlin.math.max
import kotlin.math.roundToInt

@EpoxyModelClass(layout = R.layout.layout_good_boy_card)
abstract class GoodBoyItemViewHolder(
    private val item: PetPolicy,
    private val goodBoyHandler: GoodBoyHandler,
    private val onSummaryOpen: (Boolean) -> Unit,
    private val onDocumentsOpen: (Boolean) -> Unit
) : DataBindingEpoxyModel() {

    @EpoxyAttribute
    var summaryOpen: Boolean = false

    @EpoxyAttribute
    var documentsOpen: Boolean = false

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var documentsList: List<PolicyDocument>? = null

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var documentBeingDownloaded: PolicyDocument? = null

    override fun setDataBindingVariables(binding: ViewDataBinding?) {
        (binding as? LayoutGoodBoyCardBinding)?.let { binding ->
            val context = binding.root.context

            binding.policy = item

            updateAccordions(binding)

            binding.whatIsCovered.setOnClickListener { goodBoyHandler.onWhatsCoveredPressed() }
            binding.policyDocs.setOnClickListener { goodBoyHandler.onPolicyDocsPressed() }

            binding.isDocsAccordionVisible = documentsList?.isNotEmpty() ?: false

            binding.planSummary.setOnClickListener {
                onSummaryOpen.invoke(!summaryOpen)
            }

            binding.policyDocsText.setOnClickListener {
                onDocumentsOpen.invoke(!documentsOpen)
            }

            binding.cardTitle.cardHeaderTitle =
                item.getPlanName()?.let { binding.root.context.getString(it) }.orEmpty()
            item.getPlanImage()?.let {
                binding.cardTitle.cardHeaderIcon =
                    ContextCompat.getDrawable(binding.root.context, it)
            }

            val remainingValue = item.sumInsured?.remainingValue ?: 0f
            val insured = item.sumInsured?.limit ?: 1f

            binding.reachDeductibleBar.progress =
                max(1, ((1 - remainingValue / insured) * 100).roundToInt())

            setBoldCustomText(binding)

            setupPolicyDocumentsButtons(binding)

            setDates(binding, context)
        }
    }

    private fun setDates(binding: LayoutGoodBoyCardBinding, context: Context) {
        binding.cardTitle.upperCardDate.visibility = View.VISIBLE
        binding.cardTitle.lowerCardDate.visibility = View.VISIBLE

        when (item.status) {
            PolicyStatus.ACTIVE -> {
                binding.cardTitle.upperCardDate.text =
                    context.getString(
                        R.string.policy_period,
                        getFormattedDate(item.startDate),
                        getFormattedDate(item.endDate)
                    )
                binding.cardTitle.lowerCardDate.text =
                    context.getString(R.string.renew_in, getFormattedDate(item.endDate))
            }

            PolicyStatus.CANCELED -> {
                binding.cardTitle.upperCardDate.text =
                    context.getString(R.string.purchase_date, getFormattedDate(item.startDate))
                binding.cardTitle.lowerCardDate.visibility = View.GONE
            }

            PolicyStatus.TERMINATED -> {
                binding.cardTitle.upperCardDate.text =
                    context.getString(
                        R.string.policy_period,
                        getFormattedDate(item.startDate),
                        getFormattedDate(item.endDate)
                    )
                binding.cardTitle.lowerCardDate.text =
                    context.getString(R.string.finish_date, getFormattedDate(item.endDate))
            }

            else -> {
                binding.cardTitle.upperCardDate.visibility = View.GONE
                binding.cardTitle.lowerCardDate.visibility = View.GONE
            }
        }
    }

    private fun getFormattedDate(date: Date?): String {
        return if (date != null) {
            getFormattedLocalDate(date, "MM/dd/yyyy", timeZone = TimeZone.getTimeZone("UTC"))
        } else {
            ""
        }
    }

    private fun setupPolicyDocumentsButtons(binding: LayoutGoodBoyCardBinding) {
        val inflater = LayoutInflater.from(binding.root.context)
        binding.documentListLayout.removeAllViews()
        documentsList?.forEach { policy ->
            val button =
                LayoutPolicyDocumentBinding.inflate(inflater, binding.documentListLayout, false)
            with(button) {
                docTitle.setOnClickListener { goodBoyHandler.onPolicyDocPressed(policy) }
                docTitle.text = policy.name
                button.loader.isVisible = policy == documentBeingDownloaded
                binding.documentListLayout.addView(this.root)
            }
        }
    }

    private fun updateAccordions(binding: LayoutGoodBoyCardBinding) {
        binding.isSummaryAccordionExpanded = summaryOpen
        binding.isDocsAccordionExpanded = documentsOpen

        if (summaryOpen) {
            binding.accordionBackground = R.drawable.bg_card_options_open
            binding.accordionArrow = R.drawable.ic_up
        } else {
            binding.accordionBackground = R.drawable.bg_card_options
            binding.accordionArrow = R.drawable.ic_down
        }

        if (documentsOpen) {
            binding.docsAccordionBackground = R.drawable.bg_card_options_open
            binding.docsAccordionArrow = R.drawable.ic_up
        } else {
            binding.docsAccordionBackground = R.drawable.bg_card_options
            binding.docsAccordionArrow = R.drawable.ic_down
        }
    }

    private fun setBoldCustomText(binding: LayoutGoodBoyCardBinding) {
        val remainingValue = binding.remainingValue.text
        var start = remainingValue.indexOf("$")
        var end = remainingValue.lastIndexOf(" ")

//        val remainingValueToSpan = SpannableString(binding.remainingValue.text)
//        remainingValueToSpan.setSpan(
//            ForegroundColorSpan(
//                ContextCompat.getColor(
//                    binding.root.context,
//                    R.color.tertiary_darkest
//                )
//            ), start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE
//        )
//
//        binding.remainingValue.text = remainingValueToSpan

//        val deductibleValue = binding.deductibleValue.text
//        start = 0
//        end = deductibleValue.indexOf(" ")
//
//        val deductibleValueToSpan = SpannableString(deductibleValue)
//        deductibleValueToSpan.setSpan(
//            StyleSpan(Typeface.BOLD),
//            start,
//            end,
//            Spannable.SPAN_INCLUSIVE_INCLUSIVE
//        )
//
//        binding.deductibleValue.text = deductibleValueToSpan

//        val limitValue = binding.limitValue.text
//        start = 0
//        end = limitValue.indexOf(" ")
//
//        val limitValueToSpan = SpannableString(limitValue)
//        limitValueToSpan.setSpan(
//            StyleSpan(Typeface.BOLD),
//            start,
//            end,
//            Spannable.SPAN_INCLUSIVE_INCLUSIVE
//        )
//
//        binding.limitValue.text = limitValueToSpan
    }

    private fun updateSliderPresentation(binding: LayoutGoodBoyCardBinding) {
        // todo: if value has reached deductible then change slider color
        binding.reachDeductibleBar.progressTintList =
            ContextCompat.getColorStateList(binding.root.context, R.color.tertiary_dark)!!
    }
}
