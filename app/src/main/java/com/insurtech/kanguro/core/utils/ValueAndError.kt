package com.insurtech.kanguro.core.utils

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

open class ValueAndError<V> : MutableLiveData<V> {

    constructor() : super()
    constructor(initialValue: V) : super(initialValue)

    val error: MutableLiveData<String> by lazy {
        MediatorLiveData<String>().apply {
            addSource(this@ValueAndError) { value = null }
        }
    }
}
