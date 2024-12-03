package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.domain.model.AddressModel
import com.insurtech.kanguro.networking.dto.AddressModelDto

object AddressModelMapper {

    fun mapAddressModelDtoToAddressModel(addressModelDto: AddressModelDto?): AddressModel? {
        if (addressModelDto == null) return null
        return AddressModel(
            complement = addressModelDto.complement,
            streetNumber = addressModelDto.streetNumber,
            streetName = addressModelDto.streetName,
            city = addressModelDto.city,
            state = addressModelDto.state,
            zipCode = addressModelDto.zipCode
        )
    }
}
