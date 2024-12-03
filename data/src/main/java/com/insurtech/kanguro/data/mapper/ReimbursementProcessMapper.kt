package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.domain.model.ReimbursementProcess
import com.insurtech.kanguro.networking.dto.ReimbursementProcessDto

object ReimbursementProcessMapper {
    fun reimbursementProcessDtoToReimbursementProcess(dto: ReimbursementProcessDto): ReimbursementProcess =
        when (dto) {
            ReimbursementProcessDto.UserReimbursement -> ReimbursementProcess.UserReimbursement
            ReimbursementProcessDto.VeterinarianReimbursement -> ReimbursementProcess.VeterinarianReimbursement
        }
}
