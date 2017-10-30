package at.devfest.app.ui.schedule.day

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import at.devfest.app.data.app.SelectedSessionsMemory
import at.devfest.app.data.app.model.Room
import at.devfest.app.data.app.model.ScheduleSlot
import at.devfest.app.data.app.model.Session
import com.squareup.picasso.Picasso
import org.threeten.bp.LocalDateTime

class ScheduleDayFragmentAdapterMySessions(
        private val slots: List<ScheduleSlot>,
        private val selectedSessionsMemory: SelectedSessionsMemory,
        private val picasso: Picasso,
        private val listener: ScheduleDayEntry.OnSessionClickListener)
    : RecyclerView.Adapter<ScheduleDayEntry>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleDayEntry {
        return ScheduleDayEntry(parent, picasso, listener)
    }

    override fun onBindViewHolder(holder: ScheduleDayEntry, position: Int) {
        val slot = slots[position]
        val slotSessions = slot.sessions
        val selectedSession = findSelectedSession(slot.time!!, slotSessions)

        if (selectedSession == null) {
            if (slotSessions!!.size > 1) {
                holder.bindFreeSlot(slot)
            } else {
                val session = slotSessions[0]
                if (session.room == Room.NONE.label) {
                    holder.bindBreakSlot(slot, session, true)
                } else {
                    // holder.bindSelectedSession(slot, session, true, selectedSessionsMemory.isSelected(session));
                    holder.bindFreeSlot(slot)
                }
            }
        } else {
            holder.bindSelectedSession(slot, selectedSession, true, selectedSessionsMemory.isSelected(selectedSession))
        }
    }

    override fun getItemCount(): Int {
        return slots.size
    }

    private fun findSelectedSession(slotTime: LocalDateTime, slotSessions: List<Session>?): Session? {
        val selectedSessionId = selectedSessionsMemory.get(slotTime)
        return slotSessions?.firstOrNull { session -> session.id == selectedSessionId }
    }
}
