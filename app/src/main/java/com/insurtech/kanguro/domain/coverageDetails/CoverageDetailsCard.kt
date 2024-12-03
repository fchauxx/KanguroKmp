package com.insurtech.kanguro.domain.coverageDetails

import java.util.*

sealed class CoverageDetailsCard(
    var date: Date,
    var value: Double
)

class GoodBoy(date: Date, value: Double) :
    CoverageDetailsCard(date, value)

class PreventiveAndWellness(date: Date, value: Double) :
    CoverageDetailsCard(date, value)

class Payment(
    date: Date,
    value: Double,
    var petName: String,
    var gbValue: Double,
    var pwValue: Double
) : CoverageDetailsCard(date, value)
