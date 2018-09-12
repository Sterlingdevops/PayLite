package com.sterlingng.paylite.utils

import java.security.SecureRandom
import java.util.*

class RandomString @JvmOverloads constructor(length: Int = 16, random: Random = SecureRandom(), symbols: String = alphanum) {
    private val random: Random
    private val symbols: CharArray
    private val buf: CharArray

    init {
        if (length < 1) throw IllegalArgumentException()
        if (symbols.length < 2) throw IllegalArgumentException()
        this.random = random
        this.symbols = symbols.toCharArray()
        this.buf = CharArray(length)
    }

    fun nextString(): String {
        for (idx in buf.indices)
            buf[idx] = symbols[random.nextInt(symbols.size)]
        return String(buf)
    }

    companion object {

        val upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val lower = upper.toLowerCase(Locale.ROOT)
        val digits = "0123456789"
        val alphanum = upper + lower + digits
    }
}