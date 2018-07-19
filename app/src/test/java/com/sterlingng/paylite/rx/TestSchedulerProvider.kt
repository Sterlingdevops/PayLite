package com.sterlingng.paylite.rx

import io.reactivex.Scheduler

/**
 * Created by rtukpe on 5/8/2017.
 */

class TestSchedulerProvider(private val scheduler: Scheduler) : SchedulerProvider {

    override fun ui(): Scheduler {
        return scheduler
    }

    override fun computation(): Scheduler {
        return scheduler
    }

    override fun io(): Scheduler {
        return scheduler
    }
}
