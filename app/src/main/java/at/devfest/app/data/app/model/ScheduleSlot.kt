package at.devfest.app.data.app.model

import android.os.Parcel
import android.os.Parcelable
import org.threeten.bp.LocalDateTime

class ScheduleSlot(val time: LocalDateTime? = null, val sessions: List<Session>? = null) : Parcelable {
    constructor(parcel: Parcel)
            : this(parcel.readSerializable() as LocalDateTime,
            parcel.createTypedArrayList(Session.CREATOR))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeSerializable(time)
        parcel.writeTypedList(sessions)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<ScheduleSlot> {
            override fun createFromParcel(parcel: Parcel): ScheduleSlot {
                return ScheduleSlot(parcel)
            }

            override fun newArray(size: Int): Array<ScheduleSlot?> {
                return arrayOfNulls(size)
            }
        }
    }
}