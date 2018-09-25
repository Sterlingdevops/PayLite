/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.sterlingng.paylite.utils

import com.orhanobut.logger.Logger
import com.sterlingng.paylite.BuildConfig

import timber.log.Timber

/**
 * Created by amitshekhar on 15/02/17.
 */

object Log {

    fun init() {
        if (BuildConfig.DEBUG) {
            // Set methodOffset to 5 in order to hide internal method calls
            Timber.plant(object : Timber.DebugTree() {
                override fun log(priority: Int, tag: String, message: String, t: Throwable?) {
                    Logger.log(priority, tag, message, t)
                }
            })
        }
    }

    fun d(s: String, vararg objects: Any) {
        Timber.d(s, *objects)
    }

    fun d(throwable: Throwable, s: String, vararg objects: Any) {
        Timber.d(throwable, s, *objects)
    }

    fun i(s: String, vararg objects: Any) {
        Timber.i(s, *objects)
    }

    fun i(throwable: Throwable, s: String, vararg objects: Any) {
        Timber.i(throwable, s, *objects)
    }

    fun w(s: String, vararg objects: Any) {
        Timber.w(s, *objects)
    }

    fun w(throwable: Throwable, s: String, vararg objects: Any) {
        Timber.w(throwable, s, *objects)
    }

    fun e(s: String, vararg objects: Any) {
        Timber.e(s, *objects)
    }

    fun e(throwable: Throwable, s: String, vararg objects: Any) {
        Timber.e(throwable, s, *objects)
    }

}
