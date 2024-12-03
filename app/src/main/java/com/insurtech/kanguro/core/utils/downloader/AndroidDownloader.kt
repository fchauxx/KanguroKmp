package com.insurtech.kanguro.core.utils.downloader

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri

class AndroidDownloader(context: Context) : Downloader {
    private val downloadManager = context.getSystemService(DownloadManager::class.java)

    override fun downloadFile(url: String, documentName: String): Long {
        val request = DownloadManager.Request(url.toUri())
            .setMimeType("application/pdf")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle(documentName)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, documentName)
        return downloadManager.enqueue(request)
    }
}
