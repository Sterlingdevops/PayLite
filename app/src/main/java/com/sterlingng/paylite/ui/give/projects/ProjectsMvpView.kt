package com.sterlingng.paylite.ui.give.projects

import com.sterlingng.paylite.data.model.Project
import com.sterlingng.paylite.ui.base.MvpView

interface ProjectsMvpView : MvpView {
    fun updateProjects(it: ArrayList<Project>)
}
