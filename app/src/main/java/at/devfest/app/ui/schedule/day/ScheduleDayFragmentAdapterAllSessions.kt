package at.devfest.app.ui.schedule.day

import android.support.v4.util.Pair
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

import com.squareup.picasso.Picasso

import at.devfest.app.data.app.SelectedSessionsMemory
import at.devfest.app.data.app.model.Room
import at.devfest.app.data.app.model.ScheduleSlot
import at.devfest.app.data.app.model.Session

class ScheduleDayFragmentAdapterAllSessions(slots: List<ScheduleSlot>, private val picasso: Picasso, private val selectedSessionsMemory: SelectedSessionsMemory, private val listener: ScheduleDayEntry.OnSessionClickListener) : RecyclerView.Adapter<ScheduleDayEntry>() {

    private val sessions: List<Pair<Session, ScheduleSlot>>

    init {
        sessions = toSessionsSlotsPair(slots)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleDayEntry {
        return ScheduleDayEntry(parent, picasso, listener)
    }

    override fun onBindViewHolder(holder: ScheduleDayEntry, position: Int) {
        val pair = sessions[position]
        val session = pair.first
        val slot = pair.second

        if (session.room == Room.NONE.label) {
            holder.bindBreakSlot(slot, session, shouldShowTime(slot, position))
        } else {
            holder.bindSelectedSession(slot, session, shouldShowTime(slot, position), selectedSessionsMemory.isSelected(session))
        }
    }

    override fun getItemCount(): Int {
        return sessions.size
    }

    private fun toSessionsSlotsPair(slots: List<ScheduleSlot>): List<Pair<Session, ScheduleSlot>> {
        return slots.flatMap { scheduleSlot ->
            scheduleSlot.sessions!!.map { session -> Pair(session, scheduleSlot) }
        }
    }

    private fun shouldShowTime(slot: ScheduleSlot, position: Int): Boolean {
        return position == 0 || slot.time != sessions[position - 1].second.time
    }
}
