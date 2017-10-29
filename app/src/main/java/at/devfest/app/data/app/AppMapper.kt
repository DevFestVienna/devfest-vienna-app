package at.devfest.app.data.app

import at.devfest.app.data.app.model.*
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import java.util.*
import javax.inject.Inject

class AppMapper @Inject
constructor() {

    private class TimeComparator : Comparator<Session> {
        override fun compare(o1: Session?, o2: Session?): Int {
            return o1?.fromTime?.compareTo(o2?.fromTime) ?: 0
        }
    }

    private class RoomComparator : Comparator<Session> {
        override fun compare(o1: Session?, o2: Session?): Int {
            return Room.getFromLabel(o1?.room).id - Room.getFromLabel(o2?.room).id
        }
    }

    fun speakersToMap(from: List<Speaker>): Map<Int, Speaker>
            = from.associateBy { speaker -> speaker.id }

    fun toSpeakersList(speakerIds: List<Int>?, speakersMap: Map<Int, Speaker>): List<Speaker>?
            = speakerIds?.map { id -> speakersMap[id] }?.filterNotNull()?.toList()

    fun toSpeakersIds(speakers: List<Speaker>?): List<Int>?
            = speakers?.map { speaker -> speaker.id }?.toList()

    fun toSchedule(sessions: List<Session>): Schedule {
        // Map and sort Session per start date
        val sorted = sessions.sortedWith(TimeComparator())

        // Gather Sessions per ScheduleSlot
        val scheduleSlots = mapToScheduleSlots(sorted)

        // Gather ScheduleSlots per ScheduleDays
        return mapToScheduleDays(scheduleSlots)
    }

    private fun mapToScheduleSlots(sortedSessions: List<Session>): List<ScheduleSlot> {
        val slots = ArrayList<ScheduleSlot>()

        var previousTime: LocalDateTime? = null
        var previousSessionsList: MutableList<Session>? = null

        var currentTime: LocalDateTime?
        for (currentSession in sortedSessions) {
            currentTime = currentSession.fromTime
            if (previousSessionsList != null) {
                if (currentTime == previousTime) {
                    previousSessionsList.add(currentSession)
                } else {
                    slots.add(ScheduleSlot(previousTime, sortPerRoomId(previousSessionsList)))
                    previousSessionsList = null
                }
            }

            if (previousSessionsList == null) {
                previousTime = currentTime
                previousSessionsList = ArrayList()
                previousSessionsList.add(currentSession)
            }
        }

        if (previousSessionsList != null) {
            slots.add(ScheduleSlot(previousTime, sortPerRoomId(previousSessionsList)))
        }
        return slots
    }

    private fun mapToScheduleDays(scheduleSlots: List<ScheduleSlot>): Schedule {
        val schedule = Schedule()

        var previousDay: LocalDate? = null
        var previousSlotList: MutableList<ScheduleSlot>? = null

        var currentDay: LocalDate
        for (currentSlot in scheduleSlots) {
            currentDay = LocalDate.from(currentSlot.time!!)
            if (previousSlotList != null) {
                if (currentDay == previousDay) {
                    previousSlotList.add(currentSlot)
                } else {
                    schedule.add(ScheduleDay(previousDay, previousSlotList))
                    previousSlotList = null
                }
            }

            if (previousSlotList == null) {
                previousDay = currentDay
                previousSlotList = ArrayList()
                previousSlotList.add(currentSlot)
            }
        }

        if (previousSlotList != null) {
            schedule.add(ScheduleDay(previousDay, previousSlotList))
        }
        return schedule
    }

    private fun sortPerRoomId(list: List<Session>): List<Session> = list.sortedWith(RoomComparator())
}
