package com.sterlingng.paylite.data.repository.mock

import com.sterlingng.paylite.data.model.Category
import com.sterlingng.paylite.data.model.Charity
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

    override fun mockCharities(): Observable<ArrayList<Charity>> {
        val charities: ArrayList<Charity> = ArrayList()
        charities += Charity("Red Cross Society", "Health")
        charities += Charity("S.O.S Village", "Orphanage")
        charities += Charity("Sacred Heart", "NGO")
        charities += Charity("S.T.E.R", "women and children")
        charities += Charity("Lifebank", "Donation / disaster relief")
        charities += Charity("Trauma Care", "Health")
        return Observable.just(charities)
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
