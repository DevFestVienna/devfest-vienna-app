package at.devfest.app.data.app.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by helmuth on 27/08/16.
 *
 *
 * This class models the entry in Firebase for news announcements
 */

class NewsEntry(val title: String? = null, val text: String? = null, val url: String? = null)
    : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(text)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<NewsEntry> {
            override fun createFromParcel(parcel: Parcel): NewsEntry {
                return NewsEntry(parcel)
            }

            override fun newArray(size: Int): Array<NewsEntry?> {
                return arrayOfNulls(size)
            }
        }
    }
}

