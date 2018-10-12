package com.sterlingng.paylite.data.model

import android.os.Parcel
import android.os.Parcelable
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import java.util.*

class Contact(var id: String, var name: String) : SearchSuggestion {

    var emails: ArrayList<ContactEmail> = ArrayList()
    var numbers: ArrayList<ContactPhone> = ArrayList()

    constructor(parcel: Parcel) : this(parcel.readString(), parcel.readString())

    override fun toString(): String {
        var result = name
        if (numbers.size > 0) {
            val number = numbers[0]
            result += " (" + number.number + " - " + number.type + ")"
        }
        if (emails.size > 0) {
            val (address, type) = emails[0]
            result += " [$address - $type]"
        }
        return result
    }

    override fun getBody(): String {
        return name
    }

    fun addEmail(address: String, type: String) {
        emails.add(ContactEmail(address, type))
    }

    fun addNumber(number: String, type: String) {
        numbers.add(ContactPhone(number, type))
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Contact> {
        override fun createFromParcel(parcel: Parcel): Contact {
            return Contact(parcel)
        }

        override fun newArray(size: Int): Array<Contact?> {
            return arrayOfNulls(size)
        }
    }
}