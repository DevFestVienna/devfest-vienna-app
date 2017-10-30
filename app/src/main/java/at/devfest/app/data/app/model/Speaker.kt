package at.devfest.app.data.app.model

import android.os.Parcel
import android.os.Parcelable

class Speaker(val id: Int = 0, val name: String? = null, val title: String? = null,
              val bio: String? = null, val website: String? = null, val twitter: String? = null,
              val github: String? = null, val gplus: String? = null, val xing: String? = null,
              val linkedin: String? = null, val photo: String? = null) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(title)
        parcel.writeString(bio)
        parcel.writeString(website)
        parcel.writeString(twitter)
        parcel.writeString(github)
        parcel.writeString(gplus)
        parcel.writeString(xing)
        parcel.writeString(linkedin)
        parcel.writeString(photo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<Speaker> {
            override fun createFromParcel(parcel: Parcel): Speaker {
                return Speaker(parcel)
            }

            override fun newArray(size: Int): Array<Speaker?> {
                return arrayOfNulls(size)
            }
        }
    }
}