package com.sterlingng.paylite.utils

import android.view.View
import android.view.ViewGroup


/**
 * Created by rtukpe on 13/03/2018.
 */

internal object AppConstants {
    const val TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss"
}

operator fun ViewGroup.get(view: View): Int {
    return this.indexOfChild(view)
}

const val PIN_KEY = "j+N89vDbHBf8+CE2WkdEtw=="
const val SECRET_KEY = "VCsJcMNfCLaXCB29+dTaudk3fG44k2chkpprXryxhF8="
