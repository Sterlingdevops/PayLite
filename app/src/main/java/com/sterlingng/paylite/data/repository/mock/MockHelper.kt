package com.sterlingng.paylite.data.repository.mock

import com.sterlingng.paylite.data.model.Deal
import io.reactivex.Observable
import javax.inject.Inject

class MockHelper @Inject
internal constructor() : MockerInterface {
    override fun mockDeals(): Observable<ArrayList<Deal>> {
        val deals: ArrayList<Deal> = ArrayList()
        deals += Deal("","")
        deals += Deal("","")
        deals += Deal("","")
        deals += Deal("","")
        deals += Deal("","")
        deals += Deal("","")
        deals += Deal("","")
        deals += Deal("","")
        deals += Deal("","")
        deals += Deal("","")
        deals += Deal("","")
        return Observable.just(deals)
    }
}
