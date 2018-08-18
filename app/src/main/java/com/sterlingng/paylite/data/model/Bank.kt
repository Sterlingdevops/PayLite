package com.sterlingng.paylite.data.model

data class Bank(
        val name: String,
        val slug: String,
        val code: String,
        val longcode: String,
        val gateway: String,
        val pay_with_bank: String,
        val active: String,
        val is_deleted: String,
        val country: String,
        val currency: String,
        val type: String,
        val id: String
)