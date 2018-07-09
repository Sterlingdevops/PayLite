package com.sterlingng.paylite.ui.charity

import com.sterlingng.paylite.di.annotations.PerActivity
import com.sterlingng.paylite.ui.base.MvpPresenter

@PerActivity
interface CharityMvpContract<V : CharityMvpView> : MvpPresenter<V>
