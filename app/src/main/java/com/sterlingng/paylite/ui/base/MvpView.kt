package com.sterlingng.paylite.ui.base

import android.support.annotation.StringRes

/**
 * Created by rtukpe on 13/03/2018.
 */

/**
 * Base interface that any class that wants to act as a View in the MVP (Model View Presenter)
 * pattern must implement. Generally this interface will be extended by a more specific interface
 * that then usually will be implemented by an Activity or Fragment.
 */
interface MvpView {

    val isNetworkConnected: Boolean

    fun showLoading()

    fun hideLoading()

    fun onError(@StringRes resId: Int)

    fun onError(message: String)

    fun show(message: String, useToast: Boolean)

    fun showMessage(message: String)

    fun showMessage(@StringRes resId: Int)

    fun hideKeyboard()

    fun showKeyboard()
}
