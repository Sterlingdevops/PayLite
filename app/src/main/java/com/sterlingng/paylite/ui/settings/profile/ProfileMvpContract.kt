package com.sterlingng.paylite.ui.settings.profile

import com.sterlingng.paylite.di.annotations.PerActivity
import com.sterlingng.paylite.ui.base.MvpPresenter

@PerActivity
interface ProfileMvpContract<V : ProfileMvpView> : MvpPresenter<V>