package com.sterlingng.paylite.data.model

import java.util.*

data class Rate(
        val date: Date?,
        val rateUSD: String,
        val rateGBP: String,
        val rateEUR: String,
        val rateCNY: String
)