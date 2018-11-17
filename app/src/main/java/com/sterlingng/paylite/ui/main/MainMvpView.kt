package com.sterlingng.paylite.ui.main

import com.sterlingng.paylite.data.model.User
import com.sterlingng.paylite.ui.base.MvpView

/**
 * Created by rtukpe on 21/03/2018.
 */

interface MainMvpView : MvpView {
    fun initView(currentUser: User)
}