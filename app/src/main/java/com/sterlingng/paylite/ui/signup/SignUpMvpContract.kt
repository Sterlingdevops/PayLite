package com.sterlingng.paylite.ui.signup

import com.sterlingng.paylite.di.annotations.PerActivity
import com.sterlingng.paylite.ui.base.MvpPresenter

/**
 * Created by rtukpe on 21/03/2018.
 */

@PerActivity
interface SignUpMvpContract<V : SignUpMvpView> : MvpPresenter<V>