package at.devfest.app.data.app.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by helmuth on 27/08/16.
 *
 *
 * This class models the entry in Firebase for news announcements
 */

@SuppressLint("ParcelCreator")
@Parcelize
class NewsEntry(val title: String? = null, val text: String? = null, val url: String? = null)
    : Parcelable {
}

