package at.devfest.app.data.app.model

import android.os.Parcel
import android.os.Parcelable

import java.util.ArrayList

class Schedule(val days: ArrayList<ScheduleDay> = ArrayList(0))
    : Parcelable, Iterable<ScheduleDay> {

    val size: Int
        get() = days.size

    constructor(parcel: Parcel)
            : this(parcel.createTypedArrayList(ScheduleDay.CREATOR))

    fun get(i: Int) : ScheduleDay = days[i]

    fun add(day: ScheduleDay) = days.add(day)

    override fun iterator(): Iterator<ScheduleDay> = days.iterator()
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(days)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField val CREATOR = object : Parcelable.Creator<Schedule> {
            override fun createFromParcel(parcel: Parcel): Schedule {
                return Schedule(parcel)
            }

            override fun newArray(size: Int): Array<Schedule?> {
                return arrayOfNulls(size)
            }
        }
    }

}