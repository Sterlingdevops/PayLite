package com.sterlingng.paylite.data.model

data class Card(
        val cardNumber: String,
        val cardExpiry: String,
        val currency: String,
        val cvv: String
)