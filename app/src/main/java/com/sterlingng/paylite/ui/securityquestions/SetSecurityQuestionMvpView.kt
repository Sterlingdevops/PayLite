package com.sterlingng.paylite.ui.securityquestions

import com.sterlingng.paylite.data.model.Question
import com.sterlingng.paylite.ui.base.MvpView

interface SetSecurityQuestionMvpView : MvpView {
    fun initView(mockQuestions: ArrayList<Question>)
}