package com.sterlingng.paylite.data.model

import android.os.Parcel
import android.os.Parcelable
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import com.sterlingng.paylite.utils.AppUtils.gson

class Project(val name: String, val category: String) : SearchSuggestion {
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

    override fun toString(): String {
        return gson.toJson(this)
    }

    companion object CREATOR : Parcelable.Creator<Project> {
        override fun createFromParcel(parcel: Parcel): Project {
            return Project(parcel)
        }

        override fun newArray(size: Int): Array<Project?> {
            return arrayOfNulls(size)
        }
    }
}