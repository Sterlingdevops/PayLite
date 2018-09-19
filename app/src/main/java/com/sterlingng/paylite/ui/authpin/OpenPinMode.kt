package com.sterlingng.paylite.ui.authpin

enum class OpenPinMode(mode: String) {
    NONE("NONE"),
    ENTER_NEW("ENTER_NEW"),
    CONFIRM_NEW("CONFIRM_NEW"),
    VALIDATE_FOR_CHANGE("VALIDATE_FOR_CHANGE");

    fun get(mode: String): OpenPinMode {
        return when (mode) {
            "VALIDATE_FOR_CHANGE" -> VALIDATE_FOR_CHANGE
            "CONFIRM_NEW" -> CONFIRM_NEW
            "ENTER_NEW" -> ENTER_NEW
            else -> NONE
        }
    }
}