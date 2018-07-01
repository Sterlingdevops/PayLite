package com.sterlingng.paylite.rx

import io.reactivex.Scheduler

/**
 * Created by rtukpe on 13/03/2018.
 */

interface SchedulerProvider {

    fun ui(): Scheduler

    fun computation(): Scheduler

    fun io(): Scheduler
}
