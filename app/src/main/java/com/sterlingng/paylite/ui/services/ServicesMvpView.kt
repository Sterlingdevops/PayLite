package com.sterlingng.paylite.ui.services

import com.sterlingng.paylite.data.model.VAService
import com.sterlingng.paylite.ui.base.MvpView

interface ServicesMvpView : MvpView {
    fun onServicesLoaded(services: ArrayList<VAService>)
    fun logout()
}