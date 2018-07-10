package com.sterlingng.paylite.data.model

data class Transaction(val amount: String, val name: String, val type: TransactionType, val count: String) {
    enum class TransactionType(val i: Int) {
        Credit(0),
        Debit(1)
    }
}