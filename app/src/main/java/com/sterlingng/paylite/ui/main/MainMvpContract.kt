package com.sterlingng.paylite.ui.main

import com.sterlingng.paylite.di.annotations.PerActivity
import com.sterlingng.paylite.ui.base.MvpPresenter

/**
 * Created by rtukpe on 21/03/2018.
 */

@PerActivity
interface MainMvpContract<V : MainMvpView> : MvpPresenter<V>
