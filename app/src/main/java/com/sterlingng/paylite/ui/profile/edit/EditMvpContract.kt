package com.sterlingng.paylite.ui.profile.edit

import com.sterlingng.paylite.di.annotations.PerActivity
import com.sterlingng.paylite.ui.base.MvpPresenter

@PerActivity
interface EditMvpContract<V : EditMvpView> : MvpPresenter<V>
