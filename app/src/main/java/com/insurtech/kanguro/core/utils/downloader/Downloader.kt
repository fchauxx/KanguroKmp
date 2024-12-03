package com.insurtech.kanguro.core.utils.downloader

interface Downloader {

    fun downloadFile(url: String, documentName: String): Long
}
