package com.sterlingng.paylite.ui.signup.complete

import com.sterlingng.paylite.ui.base.MvpPresenter
import java.util.*

interface CompleteMvpContract<V : CompleteMvpView> : MvpPresenter<V> {
    fun initView()
    fun doLogIn(data: HashMap<String, String>)
}
