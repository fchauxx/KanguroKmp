package com.insurtech.kanguro.core.repository.files

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import com.haroldadmin.cnradapter.NetworkResponse
import com.insurtech.kanguro.common.enums.AppLanguage
import com.insurtech.kanguro.core.repository.claims.IClaimsRepository
import com.insurtech.kanguro.core.repository.policy.IPolicyRepository
import com.insurtech.kanguro.core.utils.getProviderUriForFile
import com.insurtech.kanguro.core.utils.preferredLanguage
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.repository.ITermRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import okio.use
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Inject

class KanguroFileManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val termsRepository: ITermRepository,
    private val policyRepository: IPolicyRepository,
    private val claimsRepository: IClaimsRepository,
    private val sharedPreferences: SharedPreferences
) {

    private val downloadedFolder = File(context.cacheDir, "downloaded")

    suspend fun getUseTermsFile(): Uri? {
        val file = File(downloadedFolder, FILENAME_USE_TERMS)
        val language = sharedPreferences.preferredLanguage ?: AppLanguage.English

        return when (
            val result = termsRepository.getTermPdf(language)
        ) {
            is Result.Success -> {
                getOrDownloadFile(file) {
                    result.data
                }
            }

            else -> null
        }
    }

    suspend fun getPolicyDocument(policyId: String, documentId: Long, fileName: String): Uri? {
        val file = File(downloadedFolder, fileName)
        return getOrDownloadFile(file) {
            (
                policyRepository.getPolicyDocumentContent(
                    policyId,
                    documentId,
                    fileName
                ) as? NetworkResponse.Success
                )?.body?.byteStream()
        }
    }

    suspend fun getPolicyRentersDocument(
        policyId: String,
        documentId: Long,
        fileName: String
    ): Uri? {
        val file = File(downloadedFolder, fileName)
        return getOrDownloadFile(file) {
            (
                policyRepository.getPolicyRentersDocumentContent(
                    policyId,
                    documentId,
                    fileName
                ) as? NetworkResponse.Success
                )?.body?.byteStream()
        }
    }

    suspend fun getPolicyAttachment(policyId: String, attachmentId: Long, fileName: String): Uri? {
        val file = File(downloadedFolder, fileName)
        return getOrDownloadFile(file) {
            (
                policyRepository.getPolicyAttachmentContent(
                    policyId,
                    attachmentId
                ) as? NetworkResponse.Success
                )?.body?.byteStream()
        }
    }

    suspend fun getClaimAttachment(claimId: String, documentId: Long, fileName: String): Uri? {
        val file = File(downloadedFolder, fileName)
        return getOrDownloadFile(file) {
            (
                claimsRepository.getClaimDocument(
                    claimId,
                    documentId
                ) as? NetworkResponse.Success
                )?.body?.byteStream()
        }
    }

    private suspend fun getOrDownloadFile(file: File, fetchFile: suspend () -> InputStream?): Uri? {
        if (!file.exists() || file.length() == 0L) {
            saveByteArrayToFile(fetchFile(), file)
        }
        return file.getProviderUriForFile(context)
    }

    private fun saveByteArrayToFile(inputStream: InputStream?, file: File) {
        inputStream ?: return
        if (!file.exists()) {
            downloadedFolder.mkdirs()
            file.createNewFile()
        }
        FileOutputStream(file).use {
            var read: Int
            val bytes = ByteArray(1024)
            read = inputStream.read(bytes)
            while (read != -1) {
                it.write(bytes, 0, read)
                read = inputStream.read(bytes)
            }
        }
    }

    companion object {
        const val FILENAME_USE_TERMS = "use_terms.pdf"
        const val FILENAME_PRIVACY_POLICY = "privacy_policy.pdf"
    }
}
