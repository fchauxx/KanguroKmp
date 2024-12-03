package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.domain.model.ContactInformation
import com.insurtech.kanguro.domain.model.ContactInformationData
import com.insurtech.kanguro.domain.model.ContactInformationType
import com.insurtech.kanguro.networking.dto.ContactInformationDto
import com.insurtech.kanguro.networking.dto.ContactInformationTypeDto

object ContactInformationMapper {

    private fun mapContactInformationDtoToContactInformation(dto: ContactInformationDto): ContactInformation? {
        return ContactInformation(
            type = when (dto.type) {
                ContactInformationTypeDto.Text -> ContactInformationType.Text
                ContactInformationTypeDto.Whatsapp -> ContactInformationType.Whatsapp
                ContactInformationTypeDto.Sms -> ContactInformationType.Sms
                ContactInformationTypeDto.Phone -> ContactInformationType.Phone
            },
            action = dto.action,
            data = ContactInformationData(
                number = dto.data.number ?: return null,
                text = dto.data.text ?: return null
            )
        )
    }

    fun mapContactsInformationDtoToContactsInformation(dtoList: List<ContactInformationDto>): List<ContactInformation> {
        return dtoList.mapNotNull { mapContactInformationDtoToContactInformation(it) }
    }
}
