package com.sterlingng.paylite.data.manager


import com.sterlingng.paylite.data.repository.local.interfaces.LocalDataInterface
import com.sterlingng.paylite.data.repository.mock.MockerInterface
import com.sterlingng.paylite.data.repository.remote.api.RemoteServiceApi

/**
 * Created by rtukpe on 14/03/2018.
 */

interface DataManager : RemoteServiceApi, LocalDataInterface, MockerInterface
