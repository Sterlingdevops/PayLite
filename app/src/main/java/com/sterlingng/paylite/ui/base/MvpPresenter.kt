package com.sterlingng.paylite.ui.base

/**
 * Created by rtukpe on 13/03/2018.
 */

/**
 * Every presenter in the app must either implement this interface or extend BasePresenter
 * indicating the MvpView type that wants to be attached with.
 */
interface MvpPresenter<V : MvpView> {

    fun onViewInitialized()

    fun onAttach(mvpView: V)

    fun onDetach()

}
