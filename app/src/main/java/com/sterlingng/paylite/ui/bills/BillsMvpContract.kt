package com.sterlingng.paylite.ui.bills

import com.sterlingng.paylite.di.annotations.PerActivity
import com.sterlingng.paylite.ui.base.MvpPresenter

@PerActivity
interface BillsMvpContract<V : BillsMvpView> : MvpPresenter<V>
