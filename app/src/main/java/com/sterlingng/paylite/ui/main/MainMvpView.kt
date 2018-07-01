package com.sterlingng.paylite.ui.main

import android.view.View
import com.sterlingng.paylite.ui.base.MvpView

/**
 * Created by rtukpe on 21/03/2018.
 */

interface MainMvpView : MvpView {
    fun onLogInClicked(view: View)
    fun onSignUpClicked(view: View)
}
