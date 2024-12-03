package com.insurtech.kanguro.domain.chatbot.newchatbot

import android.content.Context
import android.util.Base64
import com.haroldadmin.cnradapter.NetworkResponse
import com.insurtech.kanguro.common.enums.PolicyStatus
import com.insurtech.kanguro.core.api.KanguroClaimsApiService
import com.insurtech.kanguro.core.api.KanguroPolicyApiService
import com.insurtech.kanguro.core.api.KanguroUserApiService
import com.insurtech.kanguro.core.api.bodies.ChatbotSessionStartBody
import com.insurtech.kanguro.core.api.bodies.FileInputType
import com.insurtech.kanguro.core.api.bodies.NewClaimAttachmentsBody
import com.insurtech.kanguro.core.api.bodies.NewClaimBody
import com.insurtech.kanguro.core.api.responses.PetPolicyResponse
import com.insurtech.kanguro.core.repository.chatbot.IChatbotRepository
import com.insurtech.kanguro.core.utils.asValidExtension
import com.insurtech.kanguro.core.utils.getAge
import com.insurtech.kanguro.data.repository.BaseRepository
import com.insurtech.kanguro.domain.Base64FileInfo
import com.insurtech.kanguro.domain.chatbot.ChatInteractionStep
import com.insurtech.kanguro.domain.chatbot.ChatSessionInfo
import com.insurtech.kanguro.domain.chatbot.enums.ChatbotAction
import com.insurtech.kanguro.domain.claimsChatbot.ClaimSummary
import com.insurtech.kanguro.domain.dashboard.ReminderType
import com.insurtech.kanguro.domain.model.ClaimType
import com.insurtech.kanguro.domain.model.Pet
import com.insurtech.kanguro.domain.model.ReimbursementProcess
import com.insurtech.kanguro.networking.dto.ErrorDto
import com.insurtech.kanguro.networking.dto.PetPolicyViewModelDto
import com.insurtech.kanguro.networking.extensions.onError
import com.insurtech.kanguro.networking.extensions.onSuccess
import com.insurtech.kanguro.remoteconfigdata.repository.IRemoteConfigManager
import com.insurtech.kanguro.remoteconfigdomain.KanguroRemoteConfigKeys
import com.squareup.moshi.Moshi
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import timber.log.Timber
import java.text.SimpleDateFormat
import java.time.temporal.ChronoUnit
import java.util.Locale
import javax.inject.Inject

class NewChatBotRepository @Inject constructor(
    moshi: Moshi,
    private val userApiService: KanguroUserApiService,
    private val policyApiService: KanguroPolicyApiService,
    private val claimsApiService: KanguroClaimsApiService,
    private val remoteConfigManager: IRemoteConfigManager
) : IChatbotRepository, BaseRepository() {

    private val converter = moshi.adapter(Base64FileInfo::class.java)

    private val dateFormatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())

    private val userActionsMap: MutableMap<ChatInteractionId, String> = mutableMapOf()

    private lateinit var currentInteractionStep: ChatInteraction

    private lateinit var chatInteraction: ChatInteractionListObject

    private lateinit var selectedPetPolicy: PetPolicyViewModelDto

    private lateinit var userName: String

    private lateinit var pets: List<Pet>

    private var claimId: String? = null

    private var claimPledgeOfHonorId: Int? = null
    private var claimAttachmentsIds: List<Int> = listOf()
    private var petHealthStateAttachmentsIds: List<Int> = listOf()

    suspend fun setupChatInteractions(
        context: Context,
        userName: String,
        pets: List<Pet>,
        sessionId: String
    ) {
        val shouldUseOtpValidation =
            remoteConfigManager.getBoolean(KanguroRemoteConfigKeys.ShouldUseOTPValidation.key)
        this.userName = userName
        this.pets = pets
        chatInteraction =
            ChatInteractionListObject(context, userName, pets, sessionId, shouldUseOtpValidation)
    }

    fun getClaimId(): String? {
        return claimId
    }

    override suspend fun getOngoingSessions(): NetworkResponse<List<ChatSessionInfo>, ErrorDto> {
        TODO("Not yet implemented")
    }

    override suspend fun startSession(sessionStartInfo: ChatbotSessionStartBody): NetworkResponse<ChatInteractionStep, ErrorDto> {
        if (!this::chatInteraction.isInitialized) return handleErrorResponse("Chat not initialized")

        currentInteractionStep = chatInteraction.getInteraction(ChatInteractionId.SelectPet)

        if (pets.size == 1) {
            val pet = pets.first()
            val nextStepId = validateSelectedPet(pet.id)
            currentInteractionStep =
                chatInteraction.getInteraction(nextStepId ?: ChatInteractionId.SelectPet)
        }

        return handleSuccessResponse(currentInteractionStep.step)
    }

    private fun handleSuccessResponse(chatInteractionStep: ChatInteractionStep): NetworkResponse<ChatInteractionStep, ErrorDto> {
        return NetworkResponse.Success(
            chatInteractionStep,
            response = Response.success(chatInteractionStep)
        )
    }

    private fun handleErrorResponse(message: String): NetworkResponse<ChatInteractionStep, ErrorDto> {
        return NetworkResponse.ServerError(
            body = null,
            Response.error<ErrorDto>(500, message.toResponseBody())
        )
    }

    override suspend fun nextStep(
        sessionId: String,
        value: String?,
        action: ChatbotAction?
    ): NetworkResponse<ChatInteractionStep, ErrorDto> {
        userActionsMap[currentInteractionStep.id] = value.orEmpty()

        val nextStepId = getNextStepOrFinish(value)

        return if (nextStepId != null) {
            currentInteractionStep = chatInteraction.getInteraction(nextStepId)
            handleSuccessResponse(currentInteractionStep.step)
        } else {
            handleErrorResponse("Chat interaction not found")
        }
    }

    override suspend fun nextStep(
        sessionId: String,
        data: ByteArray,
        extension: String,
        action: ChatbotAction?
    ): NetworkResponse<ChatInteractionStep, ErrorDto> {
        val imageStr = Base64.encodeToString(data, Base64.DEFAULT)
        val value = converter.toJson(Base64FileInfo(imageStr, extension.asValidExtension()))
        return nextStep(sessionId, value, action)
    }

    override suspend fun getPetPolicy(sessionId: String): NetworkResponse<PetPolicyResponse, ErrorDto> {
        if (!this::selectedPetPolicy.isInitialized) {
            Timber.e("Var 'selectedPetPolicy' not initialized. Chat step: ${currentInteractionStep.id}")

            return NetworkResponse.ServerError(
                body = null,
                Response.error<ErrorDto>(404, "Selected Pet Policy not initialized or Policy not found".toResponseBody())
            )
        }

        val petPolicyResponse = PetPolicyResponse(
            policyId = selectedPetPolicy.id,
            petId = selectedPetPolicy.petId?.toInt()
        )
        return NetworkResponse.Success(
            petPolicyResponse,
            response = Response.success(petPolicyResponse)
        )
    }

    private suspend fun getNextStepOrFinish(value: String?): ChatInteractionId? {
        if (value == null) return null

        return when (currentInteractionStep.id) {
            ChatInteractionId.SelectPet -> validateSelectedPet(value.toLongOrNull())
            ChatInteractionId.PledgeOfHonor -> sendPledgeOfHonor(value)
            ChatInteractionId.SelectClaimType -> validateSelectClaimType(value)
            ChatInteractionId.SelectDate -> checkPreventiveAndWellness()
            ChatInteractionId.AttachDocuments -> sendAttachments(value)
            ChatInteractionId.PetHealthStatePicture -> sendPetHealthStatePictures(value)
            ChatInteractionId.SelectBankAccount -> generateSummary()
            ChatInteractionId.Summary -> sendNewClaim()

            else -> currentInteractionStep.nextInteractionId
        }
    }

    private suspend fun validateSelectedPet(petId: Long?): ChatInteractionId? {
        if (petId == null) return null

        chatInteraction.setSelectedPet(petId)

        val hasActivePolicy = petHasActivePolicy(petId)
        if (!hasActivePolicy) {
            return ChatInteractionId.SelectPetPolicyNotActive
        }

        val hasMedicalHistory = petHasMedicalHistory(petId)
        if (!hasMedicalHistory) {
            return ChatInteractionId.SelectPetNoMedicalHistory
        }

        return currentInteractionStep.nextInteractionId
    }

    private suspend fun sendPledgeOfHonor(pledgeOfHonor: String): ChatInteractionId? {
        val pledgeOfHonorBody = NewClaimAttachmentsBody(
            pledgeOfHonor,
            FileInputType.PledgeOfHonor
        )

        claimPledgeOfHonorId = getSafeNetworkResponse {
            claimsApiService.saveNewClaimAttachments(pledgeOfHonorBody)
        }.let { (it as? NetworkResponse.Success)?.body?.first() }

        return currentInteractionStep.nextInteractionId
    }

    private suspend fun petHasActivePolicy(petId: Long): Boolean {
        val policiesResponse = getSafeNetworkResponse { policyApiService.getPolicies() }

        if (policiesResponse is NetworkResponse.Success) {
            val policyResponse = policiesResponse.body.find { it.petId == petId } ?: return false

            selectedPetPolicy = policyResponse

            if (policyResponse.status == PolicyStatus.ACTIVE) {
                chatInteraction.setHasPreventive(policyResponse.preventive ?: false)

                return true
            }
        }
        return false
    }

    private suspend fun petHasMedicalHistory(petId: Long): Boolean {
        val remindersResponse = getSafeNetworkResponse { userApiService.getReminders() }

        if (remindersResponse is NetworkResponse.Success) {
            return remindersResponse.body.none {
                it.petId == petId && it.type == ReminderType.MedicalHistory
            }
        }
        return false
    }

    private suspend fun validateSelectClaimType(value: String): ChatInteractionId? {
        if (value == ChatInteractionListObject.ACCIDENT || value == ChatInteractionListObject.ILLNESS) {
            val hasLimit = (selectedPetPolicy.sumInsured?.remainingValue ?: 0f) > 0f
            if (!hasLimit) {
                return ChatInteractionId.SelectClaimTypeAAndINoLimit
            }

            val hasPassedWaitingPeriod = (selectedPetPolicy.waitingPeriodRemainingDays ?: 0) <= 0
            if (hasPassedWaitingPeriod.not()) {
                if (isPetSixMonthsOrLess()) {
                    return currentInteractionStep.nextInteractionId
                }

                return ChatInteractionId.SelectClaimTypeAAndIWaitingPeriod
            }
        } else {
            val hasMoreBenefits = petHasMoreBenefits()
            if (hasMoreBenefits.not()) {
                return ChatInteractionId.SelectClaimTypePAndWNoMoreBenefits
            }
            return ChatInteractionId.SelectedClaimTypePAndW
        }

        return currentInteractionStep.nextInteractionId
    }

    private fun isPetSixMonthsOrLess(): Boolean {
        val pet = pets.find { it.id == selectedPetPolicy.petId } ?: return false
        return pet.getAge(ChronoUnit.MONTHS) <= 6
    }

    private suspend fun petHasMoreBenefits(): Boolean {
        val policyId = selectedPetPolicy.id ?: return false
        val reimbursement = selectedPetPolicy.reimbursement?.amount?.div(100) ?: return false
        val offerId = selectedPetPolicy.policyOfferId

        val benefits = getSafeNetworkResponse {
            policyApiService.getPolicyCoverage(policyId, reimbursement, offerId)
        }

        if (benefits is NetworkResponse.Success) {
            return benefits.body.any { !it.isCompleted() }
        }

        return false
    }

    private fun checkPreventiveAndWellness(): ChatInteractionId? {
        if (userActionsMap[ChatInteractionId.SelectedClaimTypePAndW] != null) {
            return ChatInteractionId.TypeTotalAmount
        }
        return currentInteractionStep.nextInteractionId
    }

    private suspend fun sendAttachments(attachments: String): ChatInteractionId? {
        val attachmentsBody = NewClaimAttachmentsBody(
            attachments,
            FileInputType.ReceiptDocument
        )

        claimAttachmentsIds = getSafeNetworkResponse {
            claimsApiService.saveNewClaimAttachments(attachmentsBody)
        }.let {
            (it as? NetworkResponse.Success)?.body ?: emptyList()
        }

        return currentInteractionStep.nextInteractionId
    }

    private suspend fun sendPetHealthStatePictures(pictures: String): ChatInteractionId? {
        val attachmentsBody = NewClaimAttachmentsBody(
            pictures,
            FileInputType.RecentPetPicture
        )

        petHealthStateAttachmentsIds = getSafeNetworkResponse {
            claimsApiService.saveNewClaimAttachments(attachmentsBody)
        }.let {
            (it as? NetworkResponse.Success)?.body ?: emptyList()
        }

        return currentInteractionStep.nextInteractionId
    }

    private fun generateSummary(): ChatInteractionId? {
        val claimSummary = ClaimSummary(
            pet = null,
            claim = userActionsMap[ChatInteractionId.SelectClaimType],
            date = dateFormatter.parse(userActionsMap[ChatInteractionId.SelectDate]!!),
            attachments = claimAttachmentsIds.size + petHealthStateAttachmentsIds.size,
            amount = userActionsMap[ChatInteractionId.TypeTotalAmount]?.toDouble(),
            claimId = "",
            paymentMethod = "Bank Account"
        )

        chatInteraction.setClaimSummary(claimSummary)
        return currentInteractionStep.nextInteractionId
    }

    private suspend fun sendNewClaim(): ChatInteractionId? {
        val newClaimBody = createNewClaimToSave()

        claimsApiService.saveNewClaim(newClaimBody).onSuccess {
            claimId = this.body.id
            return currentInteractionStep.nextInteractionId
        }.onError {
            if (this is NetworkResponse.ServerError && this.code == 409) {
                return ChatInteractionId.ClaimDuplicated
            }
        }

        return null
    }

    private fun createNewClaimToSave(): NewClaimBody {
        val claimTypeParsed = when (userActionsMap[ChatInteractionId.SelectClaimType]) {
            ChatInteractionListObject.ACCIDENT -> ClaimType.Accident
            ChatInteractionListObject.ILLNESS -> ClaimType.Illness
            ChatInteractionListObject.OTHER -> ClaimType.Other
            else -> null
        }

        val claimBody = NewClaimBody(
            description = userActionsMap[ChatInteractionId.TypeDescription]
                ?: userActionsMap[ChatInteractionId.SelectedClaimTypePAndW],
            invoiceDate = userActionsMap[ChatInteractionId.SelectDate]?.let {
                dateFormatter.parse(it)
            },
            amount = userActionsMap[ChatInteractionId.TypeTotalAmount]?.toDouble(),
            petId = selectedPetPolicy.petId,
            type = claimTypeParsed,
            pledgeOfHonorId = claimPledgeOfHonorId,
            reimbursementProcess = ReimbursementProcess.UserReimbursement,
            documentIds = claimAttachmentsIds + petHealthStateAttachmentsIds
        )

        return claimBody
    }
}
