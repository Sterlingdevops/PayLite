package com.sterlingng.paylite.data.model

import java.util.*

data class Transaction(val amount: String, val name: String, val type: TransactionType, val date: Date) {
    enum class TransactionType(val i: Int) {
        Credit(0),
        Debit(1)
    }
}