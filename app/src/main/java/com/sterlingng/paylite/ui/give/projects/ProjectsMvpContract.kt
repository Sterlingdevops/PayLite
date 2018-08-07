package com.sterlingng.paylite.ui.give.projects

import com.sterlingng.paylite.ui.base.MvpPresenter

interface ProjectsMvpContract<V : ProjectsMvpView> : MvpPresenter<V> {
    fun loadProjects()
}