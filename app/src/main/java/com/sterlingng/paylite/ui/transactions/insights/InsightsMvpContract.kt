package com.sterlingng.paylite.ui.transactions.insights

import com.sterlingng.paylite.di.annotations.PerActivity
import com.sterlingng.paylite.ui.base.MvpPresenter

@PerActivity
interface InsightsMvpContract<V : InsightsMvpView> : MvpPresenter<V>
