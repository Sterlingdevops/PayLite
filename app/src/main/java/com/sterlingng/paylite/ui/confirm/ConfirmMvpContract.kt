package com.sterlingng.paylite.ui.confirm

import com.sterlingng.paylite.di.annotations.PerActivity
import com.sterlingng.paylite.ui.base.MvpPresenter

/**
 * Created by rtukpe on 21/03/2018.
 */

@PerActivity
interface ConfirmMvpContract<V : ConfirmMvpView> : MvpPresenter<V>
