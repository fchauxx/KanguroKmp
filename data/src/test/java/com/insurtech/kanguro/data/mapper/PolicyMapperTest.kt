package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.common.enums.PolicyInvoiceInterval
import com.insurtech.kanguro.common.enums.PolicyStatus
import com.insurtech.kanguro.domain.model.AmountInfo
import com.insurtech.kanguro.domain.model.Deductible
import com.insurtech.kanguro.domain.model.Payment
import com.insurtech.kanguro.domain.model.Pet
import com.insurtech.kanguro.domain.model.PetPolicy
import com.insurtech.kanguro.domain.model.Reimbursement
import com.insurtech.kanguro.networking.dto.PetDto
import com.insurtech.kanguro.networking.dto.PetPolicyViewModelDto
import com.insurtech.kanguro.testing.extensions.mapJsonToModel
import org.junit.BeforeClass
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone
import kotlin.test.assertEquals
import kotlin.test.fail

class PolicyMapperTest {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    @Test
    fun `Map policy DTO to policy successfully`() {
        // ARRANGE
        val policyDto = "200_get_get_policy_detail_successfully.json"
            .mapJsonToModel<PetPolicyViewModelDto>()

        // ACT
        if (policyDto != null) {
            val policy = PolicyMapper.mapPolicyDtoToPolicy(policyDto, getExpectedPet())

            // ASSERT
            assertEquals(
                expected = getExpectedPolicy(),
                actual = policy
            )
        } else {
            fail("PolicyDto must be not null.")
        }
    }

    private fun getExpectedPet(): Pet {
        return PetMapper.mapPetDtoToPet(
            "200_get_get_pet_detail_successfully.json"
                .mapJsonToModel<PetDto>()
                ?: throw IllegalStateException("Pet must be not null.")
        )
    }

    private fun getExpectedPolicy(): PetPolicy {
        return PetPolicy(
            id = "c20de444-c6a2-4f2a-6f62-00db27161111",
            policyExternalId = 20000518,
            pet = getExpectedPet(),
            policyOfferId = 518,
            deductible = Deductible(
                id = 550001,
                limit = 500F,
                consumed = 62.1F
            ),
            sumInsured = AmountInfo(
                id = 550012,
                limit = 15000F,
                consumed = 0F,
                remainingValue = 15000F
            ),
            payment = Payment(
                firstPayment = 547.36F,
                totalPayment = 547.36F,
                invoiceInterval = PolicyInvoiceInterval.YEARLY,
                recurringPayment = 0F
            ),
            preventive = true,
            waitingPeriod = dateFormat.parse("2023-03-31T00:00:00"),
            waitingPeriodRemainingDays = -130,
            reimbursement = Reimbursement(
                id = 550008,
                amount = 80.0
            ),
            startDate = dateFormat.parse("2023-03-18T00:00:00"),
            endDate = dateFormat.parse("2024-03-17T00:00:00"),
            status = PolicyStatus.ACTIVE,
            isFuture = false
        )
    }

    companion object {
        @BeforeClass
        @JvmStatic
        fun setUpClass() {
            Locale.setDefault(Locale.ENGLISH)
        }
    }
}
