package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.domain.model.Payment
import com.insurtech.kanguro.networking.dto.PaymentDto

object PaymentMapper {

    fun mapPaymentDtoToPayment(paymentDto: PaymentDto): Payment =
        Payment(
            firstPayment = paymentDto.firstPayment,
            totalPayment = paymentDto.totalPayment,
            invoiceInterval = paymentDto.invoiceInterval,
            recurringPayment = paymentDto.recurringPayment
        )
}
