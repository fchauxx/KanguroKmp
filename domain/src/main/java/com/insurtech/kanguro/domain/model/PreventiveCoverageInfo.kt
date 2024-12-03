package com.insurtech.kanguro.domain.model

data class PreventiveCoverageInfo(
    val name: String? = null,
    val value: Double? = null,
    val usedValue: Double? = null,
    val remainingValue: Double? = null,
    val annualLimit: Double? = null,
    val remainingLimit: Double? = null,
    val uses: Int? = null,
    val remainingUses: Int? = null,
    val varName: String? = null,
    val usesLimit: Int? = null
) {
    fun getAvailablePercentage(): Double = try {
        remainingUses!!.toDouble() / uses!!.toDouble()
    } catch (e: Exception) {
        0.0
    }

    fun isCompleted(): Boolean =
        remainingLimit == 0.0 || remainingValue == 0.0 || remainingUses == 0

    fun containsNumberWordInName(): Boolean =
        name?.contains(NUMBER_WORD, ignoreCase = true) == true

    companion object {
        const val NUMBER_WORD = "number"
    }
}
