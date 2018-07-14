package com.sterlingng.paylite.ui.add

import com.sterlingng.paylite.di.annotations.PerActivity
import com.sterlingng.paylite.ui.base.MvpPresenter

@PerActivity
interface AddMvpContract<V : AddMvpView> : MvpPresenter<V>
