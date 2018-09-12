package com.sterlingng.paylite.utils

import java.security.MessageDigest
import kotlin.text.Charsets.UTF_8


fun String.hmac(key: String): String {
    val md = MessageDigest.getInstance("SHA-256")
    md.update(this.toByteArray(UTF_8))
    val digest = md.digest()
//    return String.format("%064x", BigInteger(1, digest))
    return  digest.toBase64String()
}