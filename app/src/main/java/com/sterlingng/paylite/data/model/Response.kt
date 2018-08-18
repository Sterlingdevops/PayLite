package com.sterlingng.paylite.data.model

import com.sterlingng.paylite.utils.AppUtils.gson

class Response {
    val status: String?
    val message: String?
    val data: Any?

    constructor () {
        this.status = ""
        this.message = ""
        this.data = Any()
    }

    constructor(code: String?, message: String?, data: Any?) {
        this.data = data
        this.status = code
        this.message = message
    }

    override fun toString(): String {
        return gson.toJson(this)
    }
}