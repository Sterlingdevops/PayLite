package com.sterlingng.paylite.data.model

import android.os.Parcel
import android.os.Parcelable
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion

class Charity(val name: String, val category: String) : SearchSuggestion {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(name)
        dest?.writeString(category)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun getBody(): String {
        return name
    }

    companion object CREATOR : Parcelable.Creator<Charity> {
        override fun createFromParcel(parcel: Parcel): Charity {
            return Charity(parcel)
        }

        override fun newArray(size: Int): Array<Charity?> {
            return arrayOfNulls(size)
        }
    }
}