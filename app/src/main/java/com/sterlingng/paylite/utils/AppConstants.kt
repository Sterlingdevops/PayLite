package com.sterlingng.paylite.utils

import android.view.View
import android.view.ViewGroup


/**
 * Created by rtukpe on 13/03/2018.
 */

internal object AppConstants {
    const val TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss"
    //Network Messages
    const val SOCKET_TIME_OUT_EXCEPTION = "Request timed out while trying to connect. Please ensure you have a strong signal and try again."
    const val UNKNOWN_NETWORK_EXCEPTION = "An unexpected error has occurred. Please check your network connection and try again."
    const val CONNECT_EXCEPTION = "Could not connect to the server. Please check your internet connection and try again."
    const val UNKNOWN_HOST_EXCEPTION = "Couldn't connect to the server at the moment. Please try again in a few minutes."
    const val SSL_EXCEPTION = "Software caused connection abort. Please check your internet connection and try again."

    //Events

    const val SPLIT_COST = "SPLIT_COST"
    const val ON_BOARDING = "ON_BOARDING"
    const val FUND_WALLET = "FUND_WALLET"
    const val REQUEST_MONEY = "REQUEST_MONEY"
    const val GET_CASH_SELF = "GET_CASH_SELF"
    const val UPDATE_PROFILE = "UPDATE_PROFILE"
    const val GET_CASH_OTHERS = "GET_CASH_OTHERS"
    const val CHANGE_LOGIN_PIN = "CHANGE_LOGIN_PIN"
    const val ACCOUNT_RECOVERY = "ACCOUNT_RECOVERY"
    const val SET_SECURITY_QUESTION = "SET_SECURITY_QUESTION"
    const val CREATE_TRANSACTION_PIN = "CREATE_TRANSACTION_PIN"
    const val UPADTE_TRANSACTION_PIN = "UPADTE_TRANSACTION_PIN"
    const val SEND_MONEY_TO_CONTACTS = "SEND_MONEY_TO_CONTACTS"
    const val CREATE_SCHEDULED_PAYMENT = "CREATE_SCHEDULED_PAYMENT"
    const val SEND_MONEY_TO_BANK_ACCOUNT = "SEND_MONEY_TO_BANK_ACCOUNT"
    const val SET_TRANSACTION_PAYMENT_CATEGORY = "SET_TRANSACTION_PAYMENT_CATEGORY"

    const val EVENT_ONE = "1"
    const val EVENT_TWO = "2"
    const val EVENT_THREE = "3"
    const val EVENT_FOUR = "4"
    const val EVENT_FIVE = "5"
    const val EVENT_SIX = "6"
    const val EVENT_SEVEN = "7"
    const val EVENT_EIGHT = "8"
    const val EVENT_NINE = "9"
    const val EVENT_TEN = "10"
}

operator fun ViewGroup.get(view: View): Int {
    return this.indexOfChild(view)
}

const val PIN_KEY = "j+N89vDbHBf8+CE2"
const val CLIENT_ID = "42aec90f-0142-48de-a66b-e637596fc7b8"
const val SECRET_KEY = "VCsJcMNfCLaXCB29+dTaudk3fG44k2chkpprXryxhF8="
