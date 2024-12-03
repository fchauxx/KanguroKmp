package com.insurtech.kanguro.ui.scenes.cloud

import com.insurtech.kanguro.ui.scenes.cloud.adapter.PolicyFilesType

data class CloudFileDocument(
    val type: PolicyFilesType,
    val id: Long? = null,
    val name: String? = null,
    val fileName: String? = null,
    val fileSize: Long? = null
) {
    val visibleName: String
        get() {
            return if (type == PolicyFilesType.PolicyAttachment) {
                name.orEmpty()
            } else {
                fileName.orEmpty()
            }
        }
}
