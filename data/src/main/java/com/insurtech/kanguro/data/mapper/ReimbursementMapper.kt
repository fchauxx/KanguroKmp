package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.domain.model.Reimbursement
import com.insurtech.kanguro.networking.dto.ReimbursementDto

object ReimbursementMapper {

    fun mapReimbursementDtoToReimbursement(reimbursementDto: ReimbursementDto): Reimbursement =
        Reimbursement(
            id = reimbursementDto.id,
            amount = reimbursementDto.amount
        )
}
