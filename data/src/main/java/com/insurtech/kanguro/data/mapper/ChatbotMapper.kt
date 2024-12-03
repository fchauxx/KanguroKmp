package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.domain.model.Action
import com.insurtech.kanguro.domain.model.ChatbotActionType
import com.insurtech.kanguro.domain.model.ChatbotDateRange
import com.insurtech.kanguro.domain.model.ChatbotMessageModel
import com.insurtech.kanguro.domain.model.ChatbotMessageType
import com.insurtech.kanguro.domain.model.ChatbotOptions
import com.insurtech.kanguro.domain.model.ChatbotStep
import com.insurtech.kanguro.domain.model.ChatbotStepModel
import com.insurtech.kanguro.domain.model.ChatbotStyles
import com.insurtech.kanguro.domain.model.Journey
import com.insurtech.kanguro.domain.model.Sender
import com.insurtech.kanguro.networking.dto.ActionDto
import com.insurtech.kanguro.networking.dto.ChatbotActionTypeDto
import com.insurtech.kanguro.networking.dto.ChatbotDto
import com.insurtech.kanguro.networking.dto.ChatbotMessageModelDto
import com.insurtech.kanguro.networking.dto.ChatbotMessageTypeDto
import com.insurtech.kanguro.networking.dto.ChatbotOptionsDto
import com.insurtech.kanguro.networking.dto.ChatbotStepDto
import com.insurtech.kanguro.networking.dto.ChatbotStepResponseModelDto
import com.insurtech.kanguro.networking.dto.ChatbotStylesDto
import com.insurtech.kanguro.networking.dto.JourneyDto
import com.insurtech.kanguro.networking.dto.SenderDto

object ChatbotMapper {

    fun mapChatbotDtoToString(dto: ChatbotDto): String {
        return dto.sessionId.orEmpty()
    }

    fun mapJourneyToJourneyDto(journey: Journey) = when (journey) {
        Journey.RentersOnboarding -> JourneyDto.RentersOnboarding
    }

    fun mapChatbotMessageModelDtoListToChatbotMessageModelList(list: List<ChatbotMessageModelDto?>?) =
        list?.mapNotNull {
            ChatbotMessageModel(
                content = it?.content ?: return null,
                sender = when (it.sender) {
                    SenderDto.Chatbot -> Sender.Chatbot
                    SenderDto.User -> Sender.User
                    else -> return null
                },
                type = when (it.type) {
                    ChatbotMessageTypeDto.Text -> ChatbotMessageType.Text
                    else -> return null
                }
            )
        }

    fun mapActionDtoTextToActionText(action: ActionDto.Text?): Action.Text? {
        return Action.Text(
            mapChatbotActionTypeDtoToChatbotActionType(action?.type)
                ?: return null
        )
    }

    fun mapActionDtoDateToActionDate(action: ActionDto.Date?): Action.Date? {
        return Action.Date(
            mapChatbotActionTypeDtoToChatbotActionType(action?.type)
                ?: return null,
            ChatbotDateRange(
                action?.dateRange?.startDate,
                action?.dateRange?.endDate
            )
        )
    }

    fun mapActionDtoScheduledItemsToActionScheduledItems(action: ActionDto.ScheduledItems?): Action.ScheduledItems? {
        return Action.ScheduledItems(
            mapChatbotActionTypeDtoToChatbotActionType(action?.type)
                ?: return null,
            action?.policyId
                ?: return null
        )
    }

    fun mapChatbotOptionsDtoListToChatbotOptions(options: List<ChatbotOptionsDto?>?): List<ChatbotOptions>? {
        return options?.mapNotNull {
            ChatbotOptions(
                it?.label ?: return null,
                it.id ?: return null,
                mapChatbotMessageModelDtoListToChatbotMessageModelList(it.responses)
                    ?: return null,
                it.styles?.firstNotNullOf {
                    when (it) {
                        ChatbotStylesDto.Bold -> ChatbotStyles.Bold
                        ChatbotStylesDto.Italic -> ChatbotStyles.Italic
                        else -> null
                    }
                }
            )
        }
    }

    fun mapActionDtoSingleChoiceToActionSingleChoice(action: ActionDto.SingleChoice?): Action.SingleChoice? {
        val typeAction = mapChatbotActionTypeDtoToChatbotActionType(action?.type)
            ?: return null

        val options = mapChatbotOptionsDtoListToChatbotOptions(action?.options) ?: return null

        return Action.SingleChoice(
            typeAction,
            options
        )
    }

    fun mapActionDtoCameraCaptureVideoToActionCameraCaptureVideo(action: ActionDto.CameraCaptureVideo?): Action.CameraCaptureVideo? {
        return Action.CameraCaptureVideo(
            mapChatbotActionTypeDtoToChatbotActionType(action?.type)
                ?: return null,
            action?.minimunQuantity,
            action?.maximumQuantity
        )
    }

    fun mapActionDtoFinishToActionFinish(action: ActionDto.Finish?): Action.Finish? {
        return Action.Finish(
            mapChatbotActionTypeDtoToChatbotActionType(action?.type)
                ?: return null
        )
    }

    fun mapChatbotActionDtoToAction(action: ActionDto?): Action? {
        return when (action) {
            is ActionDto.Text ->
                mapActionDtoTextToActionText(action)

            is ActionDto.Date ->
                mapActionDtoDateToActionDate(action)

            is ActionDto.ScheduledItems ->
                mapActionDtoScheduledItemsToActionScheduledItems(action)

            is ActionDto.SingleChoice ->
                mapActionDtoSingleChoiceToActionSingleChoice(action)

            is ActionDto.CameraCaptureVideo ->
                mapActionDtoCameraCaptureVideoToActionCameraCaptureVideo(action)

            is ActionDto.Finish -> mapActionDtoFinishToActionFinish(action)

            else -> null
        }
    }

    fun mapChatbotStepDtotoChatbotStep(chatbotStepDto: ChatbotStepDto?): ChatbotStep? {
        return when (chatbotStepDto) {
            ChatbotStepDto.RentersOnboardingInitialStep -> ChatbotStep.RentersOnboardingInitialStep
            ChatbotStepDto.RentersOnboardingSpouseQuestionStep -> ChatbotStep.RentersOnboardingSpouseQuestionStep
            ChatbotStepDto.RentersOnboardingSpouseNameStep -> ChatbotStep.RentersOnboardingSpouseNameStep
            ChatbotStepDto.RentersOnboardingSpouseBirthdateStep -> ChatbotStep.RentersOnboardingSpouseBirthdateStep
            ChatbotStepDto.RentersOnboardingChildrenQuestionStep -> ChatbotStep.RentersOnboardingChildrenQuestionStep
            ChatbotStepDto.RentersOnboardingChildNameStep -> ChatbotStep.RentersOnboardingChildNameStep
            ChatbotStepDto.RentersOnboardingChildBirthdateStep -> ChatbotStep.RentersOnboardingChildBirthdateStep
            ChatbotStepDto.RentersOnboardingMoreChildrenQuestionStep -> ChatbotStep.RentersOnboardingMoreChildrenQuestionStep
            ChatbotStepDto.RentersOnboardingUploadVideoQuestionStep -> ChatbotStep.RentersOnboardingUploadVideoQuestionStep
            ChatbotStepDto.RentersOnboardingUploadVideoStep -> ChatbotStep.RentersOnboardingUploadVideoStep
            ChatbotStepDto.RentersOnboardingFinishUploadingStep -> ChatbotStep.RentersOnboardingFinishUploadingStep
            ChatbotStepDto.RentersOnboardingScheduledItemsStep -> ChatbotStep.RentersOnboardingScheduledItemsStep
            ChatbotStepDto.RentersOnboardingFinishScheduledItemsStep -> ChatbotStep.RentersOnboardingFinishScheduledItemsStep
            ChatbotStepDto.RentersOnboardingAllSetStep -> ChatbotStep.RentersOnboardingAllSetStep
            ChatbotStepDto.RentersOnboardingFinalStep -> ChatbotStep.RentersOnboardingFinalStep
            ChatbotStepDto.RentersOnboardingUploadNotUploadedStep -> ChatbotStep.RentersOnboardingUploadNotUploadedStep
            else -> return null
        }
    }

    fun mapChatbotActionTypeDtoToChatbotActionType(actionDto: ChatbotActionTypeDto?): ChatbotActionType? =
        when (actionDto) {
            ChatbotActionTypeDto.Text -> ChatbotActionType.Text
            ChatbotActionTypeDto.SingleChoice -> ChatbotActionType.SingleChoice
            ChatbotActionTypeDto.Date -> ChatbotActionType.Date
            ChatbotActionTypeDto.CameraCaptureVideo -> ChatbotActionType.CameraCaptureVideo
            ChatbotActionTypeDto.ScheduledItems -> ChatbotActionType.ScheduledItems
            ChatbotActionTypeDto.Finish -> ChatbotActionType.Finish
            else -> null
        }

    fun mapChatbotStepResponseModelDto(chatbotStepResponseModel: ChatbotStepResponseModelDto?): ChatbotStepModel? {
        chatbotStepResponseModel ?: return null

        val messages = mapChatbotMessageModelDtoListToChatbotMessageModelList(
            chatbotStepResponseModel.messages
        ) ?: return null

        val action = mapChatbotActionDtoToAction(chatbotStepResponseModel.action) ?: return null

        val id = mapChatbotStepDtotoChatbotStep(chatbotStepResponseModel.id) ?: return null

        return ChatbotStepModel(
            messages = messages,
            action = action,
            id = id
        )
    }
}
