package com.sterlingng.paylite.ui.fund

import com.sterlingng.paylite.di.annotations.PerActivity
import com.sterlingng.paylite.ui.base.MvpPresenter

@PerActivity
interface FundMvpContract<V : FundMvpView> : MvpPresenter<V>
