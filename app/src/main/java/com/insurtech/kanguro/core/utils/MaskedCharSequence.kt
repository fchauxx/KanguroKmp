package com.insurtech.kanguro.core.utils

class MaskedCharSequence(private val source: CharSequence) : CharSequence {
    override val length: Int = source.length

    override fun get(index: Int): Char {
        return if (index >= length - 4) {
            source[index]
        } else {
            '*'
        }
    }

    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence {
        return source.subSequence(startIndex, endIndex)
    }
}
