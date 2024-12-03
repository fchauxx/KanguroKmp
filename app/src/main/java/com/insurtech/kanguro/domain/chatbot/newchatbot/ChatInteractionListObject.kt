package com.insurtech.kanguro.domain.chatbot.newchatbot

import android.content.Context
import com.insurtech.kanguro.R
import com.insurtech.kanguro.domain.chatbot.ChatAction
import com.insurtech.kanguro.domain.chatbot.ChatInteractionStep
import com.insurtech.kanguro.domain.chatbot.ChatMessage
import com.insurtech.kanguro.domain.chatbot.enums.ButtonOrientation
import com.insurtech.kanguro.domain.chatbot.enums.ChatbotAction
import com.insurtech.kanguro.domain.chatbot.enums.ChatbotInputType
import com.insurtech.kanguro.domain.chatbot.enums.ChatbotMessageFormat
import com.insurtech.kanguro.domain.claimsChatbot.ClaimSummary
import com.insurtech.kanguro.domain.model.Pet
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class ChatInteractionListObject(
    private val context: Context,
    private val userName: String,
    private val pets: List<Pet>,
    private val sessionId: String,
    private val shouldUseOtpValidation: Boolean
) {
    private lateinit var selectedPet: Pet
    private var petHasPreventive = false
    private lateinit var claimSummary: String

    fun setSelectedPet(id: Long) {
        val pet = pets.find { it.id == id }
        if (pet != null) {
            selectedPet = pet
        }
    }

    fun setHasPreventive(value: Boolean) {
        petHasPreventive = value
    }

    fun setClaimSummary(claimSummary: ClaimSummary) {
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }

        val date = dateFormatter.format(claimSummary.date!!)
        this.claimSummary = "{\"pet\":\"${selectedPet.name}\"," +
            "\"claim\":\"${claimSummary.claim}\"," +
            "\"claimId\":\"${claimSummary.claimId}\"," +
            "\"date\":\"$date\"," +
            "\"attachments\":${claimSummary.attachments}," +
            "\"amount\":${claimSummary.amount}," +
            "\"paymentMethod\":\"${claimSummary.paymentMethod}\"}"
    }

    fun getInteraction(interactionId: ChatInteractionId): ChatInteraction {
        return when (interactionId) {
            ChatInteractionId.SelectPet -> buildSelectPetInteraction()
            ChatInteractionId.PledgeOfHonor -> buildPledgeOfHonorInteraction()
            ChatInteractionId.SelectClaimType -> buildSelectClaimTypeInteraction()
            ChatInteractionId.SelectedClaimTypePAndW -> buildSelectClaimTypePeWInteraction()
            ChatInteractionId.SelectDate -> buildSelectDateInteraction()
            ChatInteractionId.TypeDescription -> buildTypeDescriptionInteraction()
            ChatInteractionId.TypeTotalAmount -> buildTypeTotalAmountInteraction()
            ChatInteractionId.AttachDocuments -> buildAttachDocumentsInteraction()
            ChatInteractionId.PetHealthStatePicture -> buildPetHealthStatePictureInteraction()
            ChatInteractionId.SelectBankAccount -> buildSelectBankAccountInteraction()
            ChatInteractionId.Summary -> buildSummaryInteraction()
            ChatInteractionId.End -> buildEndInteraction()

            ChatInteractionId.SelectPetPolicyNotActive -> buildSelectPetPolicyNotActiveInteraction()
            ChatInteractionId.SelectPetNoMedicalHistory -> buildSelectPetNoMedicalHistoryInteraction()
            ChatInteractionId.SelectClaimTypeAAndINoLimit -> buildSelectClaimTypeAAndINoLimit()
            ChatInteractionId.SelectClaimTypeAAndIWaitingPeriod -> buildSelectClaimTypeAAndIWaitingPeriod()
            ChatInteractionId.SelectClaimTypePAndWNoMoreBenefits -> buildSelectClaimTypePAndWNoMoreBenefits()
            ChatInteractionId.ClaimDuplicated -> buildDuplicateClaimInteraction()
        }
    }

    private fun buildSelectPetInteraction(): ChatInteraction {
        return ChatInteraction(
            id = ChatInteractionId.SelectPet,
            ChatInteractionStep(
                type = ChatbotInputType.ButtonList,
                orientation = ButtonOrientation.Vertical,
                sessionId = sessionId,
                actions = buildSelectPetActions(),
                messages = buildSelectPetMessages()
            ),
            nextInteractionId = ChatInteractionId.PledgeOfHonor
        )
    }

    private fun buildPledgeOfHonorInteraction(): ChatInteraction {
        return ChatInteraction(
            id = ChatInteractionId.PledgeOfHonor,
            ChatInteractionStep(
                type = ChatbotInputType.ButtonList,
                orientation = ButtonOrientation.Horizontal,
                sessionId = sessionId,
                actions = buildPledgeOfHonorAction(),
                messages = buildPledgeOhHonorMessages()
            ),
            nextInteractionId = ChatInteractionId.SelectClaimType
        )
    }

    private fun buildSelectClaimTypeInteraction(): ChatInteraction {
        return ChatInteraction(
            id = ChatInteractionId.SelectClaimType,
            ChatInteractionStep(
                type = ChatbotInputType.ButtonList,
                orientation = ButtonOrientation.Vertical,
                sessionId = sessionId,
                actions = buildSelectClaimTypeActions(),
                messages = buildSelectClaimTypeMessages()
            ),
            nextInteractionId = ChatInteractionId.SelectDate
        )
    }

    private fun buildSelectClaimTypePeWInteraction(): ChatInteraction {
        return ChatInteraction(
            id = ChatInteractionId.SelectedClaimTypePAndW,
            ChatInteractionStep(
                type = ChatbotInputType.ButtonList,
                orientation = ButtonOrientation.Vertical,
                sessionId = sessionId,
                actions = buildSelectClaimTypePeWActions(),
                messages = buildSelectClaimTypePeWMessages()
            ),
            nextInteractionId = ChatInteractionId.SelectDate
        )
    }

    private fun buildSelectDateInteraction(): ChatInteraction {
        return ChatInteraction(
            id = ChatInteractionId.SelectDate,
            ChatInteractionStep(
                type = ChatbotInputType.DateInput,
                orientation = null,
                sessionId = sessionId,
                actions = buildSelectDateActions(),
                messages = buildSelectDateMessages()
            ),
            nextInteractionId = ChatInteractionId.TypeDescription
        )
    }

    private fun buildTypeDescriptionInteraction(): ChatInteraction {
        return ChatInteraction(
            id = ChatInteractionId.TypeDescription,
            ChatInteractionStep(
                type = ChatbotInputType.TextInput,
                orientation = null,
                sessionId = sessionId,
                actions = buildTypeDescriptionAction(),
                messages = buildTypeDescriptionMessages()
            ),
            nextInteractionId = ChatInteractionId.TypeTotalAmount
        )
    }

    private fun buildTypeTotalAmountInteraction(): ChatInteraction {
        return ChatInteraction(
            id = ChatInteractionId.TypeTotalAmount,
            ChatInteractionStep(
                type = ChatbotInputType.CurrencyInput,
                orientation = null,
                sessionId = sessionId,
                actions = buildTypeTotalAmountAction(),
                messages = buildTotalAmountMessages()
            ),
            nextInteractionId = ChatInteractionId.AttachDocuments
        )
    }

    private fun buildAttachDocumentsInteraction(): ChatInteraction {
        return ChatInteraction(
            id = ChatInteractionId.AttachDocuments,
            ChatInteractionStep(
                type = ChatbotInputType.UploadPicture,
                orientation = ButtonOrientation.Vertical,
                sessionId = sessionId,
                actions = buildAttachDocumentsAction(),
                messages = buildAttachDocumentsMessages()
            ),
            nextInteractionId = ChatInteractionId.PetHealthStatePicture
        )
    }

    private fun buildPetHealthStatePictureInteraction(): ChatInteraction {
        return ChatInteraction(
            id = ChatInteractionId.PetHealthStatePicture,
            ChatInteractionStep(
                type = ChatbotInputType.UploadPicture,
                orientation = ButtonOrientation.Vertical,
                sessionId = sessionId,
                actions = buildPetHealthStatePictureAction(),
                messages = buildPetHealthStatePictureMessages()
            ),
            nextInteractionId = ChatInteractionId.SelectBankAccount
        )
    }

    private fun buildSelectBankAccountInteraction(): ChatInteraction {
        return ChatInteraction(
            id = ChatInteractionId.SelectBankAccount,
            ChatInteractionStep(
                type = ChatbotInputType.ButtonList,
                orientation = ButtonOrientation.Vertical,
                sessionId = sessionId,
                actions = buildSelectBankAccountAction(),
                messages = buildSelectBankAccountMessages()
            ),
            nextInteractionId = ChatInteractionId.Summary
        )
    }

    private fun buildSummaryInteraction(): ChatInteraction {
        return ChatInteraction(
            id = ChatInteractionId.Summary,
            ChatInteractionStep(
                type = ChatbotInputType.ButtonList,
                orientation = ButtonOrientation.Vertical,
                sessionId = sessionId,
                actions = buildSummaryAction(),
                messages = buildSummaryMessages()
            ),
            nextInteractionId = ChatInteractionId.End
        )
    }

    private fun buildEndInteraction(): ChatInteraction {
        return ChatInteraction(
            id = ChatInteractionId.End,
            ChatInteractionStep(
                type = ChatbotInputType.Finish,
                orientation = null,
                sessionId = sessionId,
                actions = buildEndAction(),
                messages = buildEndMessages()
            ),
            nextInteractionId = null
        )
    }

    private fun buildSelectPetPolicyNotActiveInteraction(): ChatInteraction {
        return ChatInteraction(
            id = ChatInteractionId.SelectPetPolicyNotActive,
            ChatInteractionStep(
                type = ChatbotInputType.ButtonList,
                orientation = ButtonOrientation.Vertical,
                sessionId = sessionId,
                actions = buildStopClaimChatAction(),
                messages = buildSelectPetPolicyNotActiveMessages()
            ),
            nextInteractionId = null
        )
    }

    private fun buildSelectPetNoMedicalHistoryInteraction(): ChatInteraction {
        return ChatInteraction(
            id = ChatInteractionId.SelectPetNoMedicalHistory,
            ChatInteractionStep(
                type = ChatbotInputType.ButtonList,
                orientation = ButtonOrientation.Horizontal,
                sessionId = sessionId,
                actions = buildMedicalHistoryClaimChatAction(),
                messages = buildSelectPetNoMedicalHistoryMessages()
            ),
            nextInteractionId = ChatInteractionId.PledgeOfHonor
        )
    }

    private fun buildSelectClaimTypeAAndINoLimit(): ChatInteraction {
        return ChatInteraction(
            id = ChatInteractionId.SelectClaimTypeAAndINoLimit,
            ChatInteractionStep(
                type = ChatbotInputType.ButtonList,
                orientation = ButtonOrientation.Vertical,
                sessionId = sessionId,
                actions = buildStopClaimChatAction(),
                messages = buildSelectClaimTypeAAndINoLimitMessages()
            ),
            nextInteractionId = null
        )
    }

    private fun buildSelectClaimTypeAAndIWaitingPeriod(): ChatInteraction {
        return ChatInteraction(
            id = ChatInteractionId.SelectClaimTypeAAndIWaitingPeriod,
            ChatInteractionStep(
                type = ChatbotInputType.ButtonList,
                orientation = ButtonOrientation.Vertical,
                sessionId = sessionId,
                actions = buildStopClaimChatAction(),
                messages = buildSelectClaimTypeAAndIWaitingPeriodMessages()
            ),
            nextInteractionId = null
        )
    }

    private fun buildSelectClaimTypePAndWNoMoreBenefits(): ChatInteraction {
        return ChatInteraction(
            id = ChatInteractionId.SelectClaimTypePAndWNoMoreBenefits,
            ChatInteractionStep(
                type = ChatbotInputType.ButtonList,
                orientation = ButtonOrientation.Vertical,
                sessionId = sessionId,
                actions = buildStopClaimChatAction(),
                messages = buildSelectClaimTypePAndWNoMoreBenefitsMessages()
            ),
            nextInteractionId = null
        )
    }

    private fun buildDuplicateClaimInteraction(): ChatInteraction {
        return ChatInteraction(
            id = ChatInteractionId.ClaimDuplicated,
            ChatInteractionStep(
                type = ChatbotInputType.ButtonList,
                orientation = ButtonOrientation.Vertical,
                sessionId = sessionId,
                actions = buildStopClaimDuplicatedChatAction(),
                messages = buildDuplicateClaimMessages()
            ),
            nextInteractionId = null
        )
    }

    private fun buildSelectPetActions(): List<ChatAction> {
        return pets.mapIndexed { index, pet ->
            ChatAction(
                order = index,
                label = pet.name,
                value = pet.id.toString()
            )
        }
    }

    private fun buildSelectPetMessages() = listOf(
        ChatMessage(
            order = 0,
            content = context.getString(R.string.start_claim_prompt),
            format = ChatbotMessageFormat.Text
        ),
        ChatMessage(
            order = 1,
            content = context.getString(R.string.ask_claim_for_whom),
            format = ChatbotMessageFormat.Text
        )
    )

    private fun buildPledgeOfHonorAction() = listOf(
        ChatAction(
            order = 0,
            label = context.getString(R.string.sign_label),
            value = SIGNATURE,
            action = ChatbotAction.Signature,
            isMainAction = false,
            userResponseMessage = "\uD83D\uDD8B"
        )
    )

    private fun buildPledgeOhHonorMessages() = listOf(
        ChatMessage(
            order = 0,
            content = context.getString(R.string.request_pledge_signature),
            format = ChatbotMessageFormat.Text
        )
    )

    private fun buildSelectClaimTypeActions(): List<ChatAction> {
        val actions = arrayListOf(
            ChatAction(
                order = 0,
                label = context.getString(R.string.accident_label),
                value = ACCIDENT
            ),
            ChatAction(
                order = 1,
                label = context.getString(R.string.illness_label),
                value = ILLNESS
            )
        )

        if (petHasPreventive) {
            actions.add(
                ChatAction(
                    order = 2,
                    label = context.getString(R.string.preventive_wellness_label),
                    value = OTHER
                )
            )
        }

        return actions
    }

    private fun buildSelectClaimTypeMessages() = listOf(
        ChatMessage(
            order = 0,
            content = context.getString(R.string.acknowledge_thank_you),
            format = ChatbotMessageFormat.Text
        ),
        ChatMessage(
            order = 1,
            content = context.getString(R.string.ask_claim_reason),
            format = ChatbotMessageFormat.Text
        )
    )

    private fun buildSelectClaimTypePeWActions() = listOf(
        ChatAction(
            order = 0,
            label = context.getString(R.string.see_options_label),
            value = OTHER,
            action = ChatbotAction.VaccinesAndExamsSelect
        )
    )

    private fun buildSelectClaimTypePeWMessages() = listOf(
        ChatMessage(
            order = 0,
            content = context.getString(R.string.ask_for_options),
            format = ChatbotMessageFormat.Text
        )
    )

    private fun buildSelectDateActions() = listOf<ChatAction>()

    private fun buildSelectDateMessages() = listOf(
        ChatMessage(
            order = 0,
            content = context.getString(R.string.ask_invoice_date),
            format = ChatbotMessageFormat.Text
        )
    )

    private fun buildTypeDescriptionAction() = listOf<ChatAction>()

    private fun buildTypeDescriptionMessages() = listOf(
        ChatMessage(
            order = 0,
            content = context.getString(R.string.ask_description),
            format = ChatbotMessageFormat.Text
        )
    )

    private fun buildTypeTotalAmountAction() = listOf<ChatAction>()

    private fun buildTotalAmountMessages() = listOf(
        ChatMessage(
            order = 0,
            content = context.getString(R.string.ask_invoice_total_amount),
            format = ChatbotMessageFormat.Text
        )
    )

    private fun buildAttachDocumentsAction() = listOf<ChatAction>()

    private fun buildAttachDocumentsMessages() = listOf(
        ChatMessage(
            order = 0,
            content = context.getString(R.string.instruction_receipt),
            format = ChatbotMessageFormat.Text
        ),
        ChatMessage(
            order = 1,
            content = context.getString(R.string.instruction_medical_records),
            format = ChatbotMessageFormat.Text
        )
    )

    private fun buildPetHealthStatePictureAction() = listOf<ChatAction>()

    private fun buildPetHealthStatePictureMessages() = listOf(
        ChatMessage(
            order = 0,
            content = context.getString(R.string.instruction_pet_health_state),
            format = ChatbotMessageFormat.Text
        ),
        ChatMessage(
            order = 1,
            content = context.getString(R.string.ask_pet_health_state),
            format = ChatbotMessageFormat.Text
        )
    )

    private fun buildSelectBankAccountAction() = listOf(
        ChatAction(
            order = 0,
            label = context.getString(R.string.inform_account_label),
            value = REIMBURSEMENT,
            action = ChatbotAction.Reimbursement
        )
    )

    private fun buildSelectBankAccountMessages() = listOf(
        ChatMessage(
            order = 0,
            content = context.getString(R.string.ask_account_info),
            format = ChatbotMessageFormat.Text
        )
    )

    private fun buildSummaryAction() = listOf(
        ChatAction(
            order = 0,
            label = context.getString(R.string.submit_claim_label),
            value = OTP_VALIDATION,
            action = if (shouldUseOtpValidation) ChatbotAction.OtpValidation else null,
            userResponseMessage = context.getString(R.string.confirmed_label)
        )
    )

    private fun buildSummaryMessages() = listOf(
        ChatMessage(
            order = 0,
            content = context.getString(R.string.display_claim_summary),
            format = ChatbotMessageFormat.Text
        ),
        ChatMessage(
            order = 1,
            content = claimSummary,
            format = ChatbotMessageFormat.Summary
        )
    )

    private fun buildEndAction() = listOf(
        ChatAction(
            order = 0,
            label = context.getString(R.string.finish_label),
            value = FINISH_AND_REDIRECT,
            action = ChatbotAction.FinishAndRedirect,
            userResponseMessage = context.getString(R.string.finish_label)
        )
    )

    private fun buildEndMessages() = listOf(
        ChatMessage(
            order = 0,
            content = context.getString(R.string.notification_claim_submitted),
            format = ChatbotMessageFormat.Text
        ),
        ChatMessage(
            order = 1,
            content = context.getString(R.string.instruction_check_status, selectedPet.name),
            format = ChatbotMessageFormat.Text
        ),
        ChatMessage(
            order = 2,
            content = context.getString(R.string.acknowledge_thank_you),
            format = ChatbotMessageFormat.Text
        )
    )

    // In case of error
    private fun buildStopClaimChatAction() = listOf(
        ChatAction(
            order = 0,
            label = context.getString(R.string.finish_label),
            value = FINISH,
            action = ChatbotAction.StopClaim,
            userResponseMessage = context.getString(R.string.finish_label)
        )
    )

    private fun buildStopClaimDuplicatedChatAction() = listOf(
        ChatAction(
            order = 0,
            label = context.getString(R.string.track_claims),
            value = FINISH,
            action = ChatbotAction.StopDuplicatedClaim,
            userResponseMessage = context.getString(R.string.track_claims)
        )
    )

    private fun buildMedicalHistoryClaimChatAction() = listOf(
        ChatAction(
            order = 0,
            label = context.getString(R.string.finish_label),
            value = FINISH,
            action = ChatbotAction.StopClaim,
            userResponseMessage = context.getString(R.string.finish_label)
        ),
        ChatAction(
            order = 1,
            label = context.getString(R.string.btn_continue),
            value = CONTINUE,
            action = ChatbotAction.Skip,
            userResponseMessage = context.getString(R.string.btn_continue)
        )
    )

    private fun getContactPhrase(): String {
        return context.getString(R.string.instruction_contact_us)
    }

    private fun buildSelectPetPolicyNotActiveMessages() = listOf(
        ChatMessage(
            order = 0,
            content = context.getString(
                R.string.notification_policy_inactive,
                userName,
                selectedPet.name
            ),
            format = ChatbotMessageFormat.Text
        ),
        ChatMessage(
            order = 1,
            content = getContactPhrase(),
            format = ChatbotMessageFormat.Text
        )
    )

    private fun buildSelectPetNoMedicalHistoryMessages() = listOf(
        ChatMessage(
            order = 0,
            content = context.getString(R.string.notification_no_med_history, selectedPet.name),
            format = ChatbotMessageFormat.Text
        ),
        ChatMessage(
            order = 1,
            content = context.getString(R.string.reminder_check_updates),
            format = ChatbotMessageFormat.Text
        )
    )

    private fun buildSelectClaimTypeAAndINoLimitMessages() = listOf(
        ChatMessage(
            order = 0,
            content = context.getString(
                R.string.notification_annual_limit_reached,
                userName,
                selectedPet.name
            ),
            format = ChatbotMessageFormat.Text
        ),
        ChatMessage(
            order = 1,
            content = getContactPhrase(),
            format = ChatbotMessageFormat.Text
        )
    )

    private fun buildSelectClaimTypeAAndIWaitingPeriodMessages() = listOf(
        ChatMessage(
            order = 0,
            content = context.getString(
                R.string.notification_waiting_period,
                userName,
                selectedPet.name
            ),
            format = ChatbotMessageFormat.Text
        ),
        ChatMessage(
            order = 1,
            content = getContactPhrase(),
            format = ChatbotMessageFormat.Text
        )
    )

    private fun buildSelectClaimTypePAndWNoMoreBenefitsMessages() = listOf(
        ChatMessage(
            order = 0,
            content = context.getString(
                R.string.notification_benefits_complete,
                userName,
                selectedPet.name
            ),
            format = ChatbotMessageFormat.Text
        ),
        ChatMessage(
            order = 1,
            content = getContactPhrase(),
            format = ChatbotMessageFormat.Text
        )
    )

    private fun buildDuplicateClaimMessages() = listOf(
        ChatMessage(
            order = 0,
            content = context.getString(R.string.warning_duplicate_claim, selectedPet.name),
            format = ChatbotMessageFormat.Text
        ),
        ChatMessage(
            order = 1,
            content = context.getString(R.string.warning_duplicate_claim_2),
            format = ChatbotMessageFormat.Text
        )
    )

    companion object {
        const val SIGNATURE = "Signature"
        const val ACCIDENT = "Accident"
        const val ILLNESS = "Illness"
        const val OTHER = "Other"
        const val REIMBURSEMENT = "Reimbursement"
        const val OTP_VALIDATION = "OtpValidation"
        const val FINISH_AND_REDIRECT = "FinishAndRedirect"
        const val FINISH = "Finish"
        const val CONTINUE = "Continue"
    }
}
