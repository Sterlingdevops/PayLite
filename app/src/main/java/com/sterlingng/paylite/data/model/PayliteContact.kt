package com.sterlingng.paylite.data.model

import android.os.Parcel
import android.os.Parcelable
import com.sterlingng.paylite.data.model.realms.ContactRealm
import com.sterlingng.paylite.utils.AppUtils.gson

class PayliteContact : Parcelable {
    var id: String = ""
    var name: String = ""
    var phone: String = ""
    var email: String = ""
    var image: Int = 0

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        name = parcel.readString()
        phone = parcel.readString()
        email = parcel.readString()
        image = parcel.readInt()
    }

    constructor(name: String, image: Int) {
        this.name = name
        this.image = image
    }

    constructor()

    fun asContactRealm(): ContactRealm {
        val contactRealm = ContactRealm()
        contactRealm.email = email
        contactRealm.phone = phone
        contactRealm.name = name
        contactRealm.id = id
        return contactRealm
    }

    override fun toString(): String {
        return gson.toJson(this)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(phone)
        parcel.writeString(email)
        parcel.writeInt(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PayliteContact> {
        override fun createFromParcel(parcel: Parcel): PayliteContact {
            return PayliteContact(parcel)
        }

        override fun newArray(size: Int): Array<PayliteContact?> {
            return arrayOfNulls(size)
        }
    }
}