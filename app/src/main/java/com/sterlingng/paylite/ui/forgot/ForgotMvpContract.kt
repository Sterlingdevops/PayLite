package com.sterlingng.paylite.ui.forgot

import com.sterlingng.paylite.di.annotations.PerActivity
import com.sterlingng.paylite.ui.base.MvpPresenter

@PerActivity
interface ForgotMvpContract<V : ForgotMvpView> : MvpPresenter<V>