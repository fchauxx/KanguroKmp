package com.insurtech.kanguro.core.api.bodies

data class NewClaimAttachmentsBody(
    val files: String?,
    val fileInputType: FileInputType?
)

enum class FileInputType {
    ReceiptDocument,
    RecentPetPicture,
    PledgeOfHonor
}
