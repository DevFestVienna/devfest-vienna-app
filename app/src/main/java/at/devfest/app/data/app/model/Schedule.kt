package at.devfest.app.data.app.model

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

import java.util.ArrayList

@SuppressLint("ParcelCreator")
@Parcelize
class Schedule(val days: ArrayList<ScheduleDay> = ArrayList(0))
    : Parcelable, Iterable<ScheduleDay> {

    val size: Int
        get() = days.size

    fun get(i: Int) : ScheduleDay = days[i]

    fun add(day: ScheduleDay) = days.add(day)

    override fun iterator(): Iterator<ScheduleDay> = days.iterator()

}