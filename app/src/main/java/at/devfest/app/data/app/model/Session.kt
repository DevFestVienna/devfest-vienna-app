package at.devfest.app.data.app.model

import android.os.Parcel
import android.os.Parcelable

import org.threeten.bp.LocalDateTime

class Session(val id: Int = 0, val room: String? = null, val speakers: List<Speaker>? = null,
              val title: String? = null, val description: String? = null,
              val fromTime: LocalDateTime? = null, val toTime: LocalDateTime? = null,
              val photo: String? = null) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.createTypedArrayList(Speaker.CREATOR),
            parcel.readString(),
            parcel.readString(),
            parcel.readSerializable() as LocalDateTime,
            parcel.readSerializable() as LocalDateTime,
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(room)
        parcel.writeTypedList(speakers)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeSerializable(fromTime)
        parcel.writeSerializable(toTime)
        parcel.writeString(photo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<Session> {
            override fun createFromParcel(parcel: Parcel): Session {
                return Session(parcel)
            }

            override fun newArray(size: Int): Array<Session?> {
                return arrayOfNulls(size)
            }
        }
    }
}