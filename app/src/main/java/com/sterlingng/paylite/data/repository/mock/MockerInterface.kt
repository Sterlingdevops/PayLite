package com.sterlingng.paylite.data.repository.mock

import com.sterlingng.paylite.data.model.Category
import com.sterlingng.paylite.data.model.Deal
import io.reactivex.Observable

interface MockerInterface {
    fun mockCategories(): Observable<ArrayList<Category>>
    fun mockDeals(): Observable<ArrayList<Deal>>
}
