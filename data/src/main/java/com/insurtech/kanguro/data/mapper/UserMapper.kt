package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.common.enums.AccountType
import com.insurtech.kanguro.domain.model.User
import com.insurtech.kanguro.domain.model.UserAccount
import com.insurtech.kanguro.domain.model.UserUpdatePasswordBody
import com.insurtech.kanguro.domain.model.UserUpdateProfileBody
import com.insurtech.kanguro.networking.dto.UserAccountBodyDto
import com.insurtech.kanguro.networking.dto.UserAccountDto
import com.insurtech.kanguro.networking.dto.UserDto
import com.insurtech.kanguro.networking.dto.UserUpdatePasswordBodyDto
import com.insurtech.kanguro.networking.dto.UserUpdateProfileBodyDto

object UserMapper {

    fun mapUserAccountDtoToUserAccount(
        userAccountDto: UserAccountDto
    ): UserAccount =
        UserAccount(
            accountNumber = userAccountDto.accountNumber,
            routingNumber = userAccountDto.routingNumber,
            bankName = userAccountDto.bankName,
            accountType = userAccountDto.accountType
        )

    fun mapUserAccountToUserAccountBodyDto(userAccount: UserAccount): UserAccountBodyDto =
        UserAccountBodyDto(
            accountNumber = userAccount.accountNumber,
            routingNumber = userAccount.routingNumber,
            bankName = userAccount.bankName ?: "",
            accountType = userAccount.accountType ?: AccountType.checking
        )

    fun mapUserUpdatePasswordBodyToUserUpdatePasswordBodyDto(
        userUpdatePasswordBody: UserUpdatePasswordBody
    ) = UserUpdatePasswordBodyDto(
        email = userUpdatePasswordBody.email,
        currentPassword = userUpdatePasswordBody.currentPassword,
        newPassword = userUpdatePasswordBody.newPassword
    )

    fun mapUserUpdateProfileBodyToUserUpdateProfileBodyDto(
        userUpdateProfileBody: UserUpdateProfileBody
    ): UserUpdateProfileBodyDto =
        UserUpdateProfileBodyDto(
            givenName = userUpdateProfileBody.givenName,
            surname = userUpdateProfileBody.surname,
            phone = userUpdateProfileBody.phone
        )

    fun mapUserDtoToUser(userDto: UserDto): User? {
        return User(
            id = userDto.id ?: return null,
            givenName = userDto.givenName ?: return null,
            surname = userDto.surname ?: return null,
            language = userDto.language ?: return null,
            referralCode = userDto.referralCode,
            phone = userDto.phone,
            address = userDto.address,
            complement = userDto.complement,
            zipCode = userDto.zipCode,
            email = userDto.email,
            birthDate = userDto.birthDate ?: return null,
            isNeededDeleteData = userDto.isNeededDeleteData ?: return null,
            hasAccessBlocked = userDto.hasAccessBlocked ?: return null,
            insuranceId = userDto.insuranceId
        )
    }
}
