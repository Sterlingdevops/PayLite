package com.sterlingng.paylite.data.model

import android.os.Parcel
import android.os.Parcelable
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import java.util.*

class Notification() : SearchSuggestion {

    var date: Date = Date()
    var text: String = ""
    var imageResId: Int = 0

    constructor(parcel: Parcel) : this() {
        text = parcel.readString()
        imageResId = parcel.readInt()
    }

    constructor(date: Date, text: String, imageResId: Int) : this() {
        this.date = date
        this.text = text
        this.imageResId = imageResId
    }

    override fun getBody(): String {
        return text
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(text)
        parcel.writeInt(imageResId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Notification> {
        override fun createFromParcel(parcel: Parcel): Notification {
            return Notification(parcel)
        }

        override fun newArray(size: Int): Array<Notification?> {
            return arrayOfNulls(size)
        }
    }
}