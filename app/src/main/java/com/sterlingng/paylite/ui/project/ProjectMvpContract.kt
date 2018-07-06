package com.sterlingng.paylite.ui.project

import com.sterlingng.paylite.di.annotations.PerActivity
import com.sterlingng.paylite.ui.base.MvpPresenter

@PerActivity
interface ProjectMvpContract<V : ProjectMvpView> : MvpPresenter<V>
