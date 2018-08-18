package com.sterlingng.paylite.rx

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject

class RxBus @Inject constructor() : EventBus {

    private val bus: Subject<Any> = PublishSubject.create<Any>().toSerialized()

    override fun cleanup() {

    }

    override fun post(event: Any) {
        if (this.bus.hasObservers()) {
            this.bus.onNext(event)
        }
    }

    override fun <T> observe(eventClass: Class<T>): Observable<T> {
        return bus.filter { _: Any -> true } // Filter out null objects, better safe than sorry
                .filter(eventClass::isInstance) // We're only interested in a specific event class
                .cast(eventClass) // Cast it for easier usage
    }
}
