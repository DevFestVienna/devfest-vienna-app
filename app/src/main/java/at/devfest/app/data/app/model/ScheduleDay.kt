package at.devfest.app.data.app.model

import android.os.Parcel
import android.os.Parcelable
import org.threeten.bp.LocalDate

class ScheduleDay(val date: LocalDate? = null, val slots: List<ScheduleSlot>? = null) : Parcelable {
    constructor(parcel: Parcel)
            : this(parcel.readSerializable() as LocalDate,
            parcel.createTypedArrayList(ScheduleSlot.CREATOR))

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeSerializable(date)
        dest?.writeTypedList(slots)
    }

    val isToday: Boolean
        get() = date?.equals(LocalDate.now()) ?: false

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<ScheduleDay> {
            override fun createFromParcel(parcel: Parcel): ScheduleDay {
                return ScheduleDay(parcel)
            }

            override fun newArray(size: Int): Array<ScheduleDay?> {
                return arrayOfNulls(size)
            }
        }
    }
}