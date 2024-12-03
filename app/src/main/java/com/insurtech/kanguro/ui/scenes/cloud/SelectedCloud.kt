package com.insurtech.kanguro.ui.scenes.cloud

import android.os.Parcelable
import com.insurtech.kanguro.domain.model.CloudDocumentPolicy
import com.insurtech.kanguro.domain.model.CloudPet
import com.insurtech.kanguro.domain.model.CloudRenters
import kotlinx.parcelize.Parcelize

data class SelectedCloud(
    val id: String? = null,
    val name: String? = null,
    val type: CloudType? = null,
    val cloudDocumentPolicies: List<CloudDocumentPolicy>? = null
)

@Parcelize
enum class CloudType : Parcelable {
    PET, RENTERS
}

fun CloudRenters.toSelectedCloud(): SelectedCloud {
    return SelectedCloud(
        id = this.id,
        name = this.name,
        type = CloudType.RENTERS,
        cloudDocumentPolicies = this.cloudDocumentPolicies
    )
}

fun CloudPet.toSelectedCloud(): SelectedCloud {
    return SelectedCloud(
        id = this.id.toString(),
        name = this.name,
        type = CloudType.PET,
        cloudDocumentPolicies = this.cloudDocumentPolicies
    )
}
