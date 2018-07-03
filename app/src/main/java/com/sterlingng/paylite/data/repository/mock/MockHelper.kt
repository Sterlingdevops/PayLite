package com.sterlingng.paylite.data.repository.mock

import com.sterlingng.paylite.data.model.Category
import com.sterlingng.paylite.data.model.Deal
import io.reactivex.Observable
import javax.inject.Inject

class MockHelper @Inject
internal constructor() : MockerInterface {

    override fun mockCategories(): Observable<ArrayList<Category>> {
        val categories: ArrayList<Category> = ArrayList()
        categories += Category("Irrigation 2018", "Agriculture")
        categories += Category("Children’s day", "Health")
        categories += Category("Irrigation 2018", "Agriculture")
        categories += Category("Children’s day", "Health")
        categories += Category("Irrigation 2018", "Agriculture")
        categories += Category("Children’s day", "Health")
        categories += Category("Irrigation 2018", "Agriculture")
        categories += Category("Children’s day", "Health")
        categories += Category("Irrigation 2018", "Agriculture")
        categories += Category("Children’s day", "Health")
        categories += Category("Irrigation 2018", "Agriculture")
        categories += Category("Children’s day", "Health")
        categories += Category("Irrigation 2018", "Agriculture")
        categories += Category("Children’s day", "Health")
        return Observable.just(categories)
    }

    override fun mockDeals(): Observable<ArrayList<Deal>> {
        val deals: ArrayList<Deal> = ArrayList()
        deals += Deal("", "")
        deals += Deal("", "")
        deals += Deal("", "")
        deals += Deal("", "")
        deals += Deal("", "")
        deals += Deal("", "")
        deals += Deal("", "")
        deals += Deal("", "")
        deals += Deal("", "")
        deals += Deal("", "")
        deals += Deal("", "")
        return Observable.just(deals)
    }
}
