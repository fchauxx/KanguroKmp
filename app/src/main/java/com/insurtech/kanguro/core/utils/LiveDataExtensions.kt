package com.insurtech.kanguro.core.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

fun <T> MediatorLiveData<T>.addSources(vararg sources: LiveData<*>, onChanged: () -> Unit) {
    sources.forEach { addSource(it) { onChanged() } }
}
