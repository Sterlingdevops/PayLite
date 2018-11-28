package com.sterlingng.paylite.ui.securityquestions

import com.sterlingng.paylite.ui.base.MvpPresenter

interface SetSecurityQuestionMvpContract<V : SetSecurityQuestionMvpView> : MvpPresenter<V> {
    fun loadQuestions()
    fun saveQuestions(data: HashMap<String, Any>)
}

