package com.sterlingng.paylite.data.repository.mock

import com.sterlingng.paylite.data.model.Deal
import io.reactivex.Observable

interface MockerInterface {
    fun mockDeals(): Observable<ArrayList<Deal>>
}
