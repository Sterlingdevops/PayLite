package com.sterlingng.paylite.utils

import android.text.TextUtils
import android.util.Base64
import android.util.Patterns
import java.util.regex.Pattern
import javax.crypto.Cipher
import javax.crypto.Mac
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.text.Charsets.UTF_8


fun String.toSentenceCase(): String {
    val stringBuilder = StringBuilder()
    for (str in split(" ")) {
        stringBuilder.append(str.substring(0, 1).toUpperCase())
        stringBuilder.append(str.substring(1).toLowerCase())
        stringBuilder.append(" ")
    }
    return stringBuilder.toString()
}

fun String.sha256(): String {
    var hash = ""
    try {
        val mac = Mac.getInstance("HmacSHA256")
        val secret = SecretKeySpec(SECRET_KEY.fromBase64(), mac.algorithm)
        mac.init(secret)

        val digest = mac.doFinal(this.toByteArray())
        hash = Base64.encodeToString(digest, Base64.NO_WRAP)
    } catch (e: Exception) {
        Log.e(e.localizedMessage)
    }
    return hash
}

fun String.encryptAES(initVector: String): String {
    var cipherText = ""
    try {
        val bytes = initVector.toByteArray()
        val iv = IvParameterSpec(bytes)
        val skeySpec = SecretKeySpec(SECRET_KEY.fromBase64(), "AES")

        val cipher = Cipher.getInstance("AES/CBC/PKCS7PADDING")
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv)

        val encrypted = cipher.doFinal(this.toByteArray())
//        Log.d("encrypted string: " + Base64.encodeToString(encrypted, Base64.NO_WRAP))

        cipherText = Base64.encodeToString(encrypted, Base64.NO_WRAP)
    } catch (ex: Exception) {
        Log.e(ex, "encryptAES Error")
    }
    return cipherText
}

fun String.decryptAES(initVector: String): String {
    var cleartext = ""
    try {
        val bytes = initVector.toByteArray()
        val iv = IvParameterSpec(bytes)
        val skeySpec = SecretKeySpec(SECRET_KEY.fromBase64(), "AES")

        val cipher = Cipher.getInstance("AES/CBC/PKCS7PADDING")
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv)

        val original = cipher.doFinal(Base64.decode(this, Base64.NO_WRAP))

        cleartext = String(original)
    } catch (ex: Exception) {
        ex.printStackTrace()
    }

    return cleartext
}

fun String.isValidEmail(): Boolean {
    return !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun ByteArray.toBase64String(): String = Base64.encodeToString(this, Base64.NO_WRAP)
fun ByteArray.toBase64(): ByteArray = Base64.encode(this, Base64.NO_WRAP)
fun ByteArray.asString(): String = this.toString(UTF_8)

private val HEX_CHARS = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')
fun ByteArray.asHexString(): String {
    val result = StringBuffer()
    forEach {
        val octet = it.toInt()
        val firstIndex = (octet and 0xF0).ushr(4)
        val secondIndex = octet and 0x0F
        result.append(HEX_CHARS[firstIndex])
        result.append(HEX_CHARS[secondIndex])
    }
    return result.toString()
}

fun String.toNumber(default: Int): Int = try {
    Integer.valueOf(this)
} catch (e: NumberFormatException) {
    default
}

@Throws(IllegalArgumentException::class)
fun String.asByteArray(): ByteArray = this.toByteArray(Charsets.UTF_8)

fun String.fromBase64(): ByteArray = Base64.decode(this.asByteArray(), Base64.DEFAULT)

private val pHex: Pattern = Pattern.compile("[0-9a-fA-F]+")
fun String.isValidHex(): Boolean = (this.length % 2 == 0 && pHex.matcher(this).matches())

@Throws(IllegalArgumentException::class)
fun String.hexToByteArray(): ByteArray {
    val trimmed = this.replace(" ", "")
    if (!trimmed.isValidHex()) throw IllegalArgumentException("Invalid hex string.")
    val data = ByteArray(trimmed.length / 2)
    var i = 0
    while (i < trimmed.length) {
        data[i / 2] = ((Character.digit(trimmed[i], 16) shl 4) +
                Character.digit(trimmed[i + 1], 16)).toByte()
        i += 2
    }
    return data
}