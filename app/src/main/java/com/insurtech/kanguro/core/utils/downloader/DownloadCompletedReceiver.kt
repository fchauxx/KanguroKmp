package com.insurtech.kanguro.core.utils.downloader

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.insurtech.kanguro.R

class DownloadCompletedReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == ACTION_DOWNLOAD_COMPLETED) {
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L)
            if (id != -1L) {
                val text = context?.getString(R.string.download_completed)
                val toast = Toast.makeText(context, text, Toast.LENGTH_LONG)
                toast.show()
            }
        }
    }

    companion object {
        private const val ACTION_DOWNLOAD_COMPLETED = "android.intent.action.DOWNLOAD_COMPLETE"
    }
}
