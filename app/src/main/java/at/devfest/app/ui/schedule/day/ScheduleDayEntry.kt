package at.devfest.app.ui.schedule.day

import android.support.v7.widget.CardView
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.squareup.picasso.Picasso

import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

import at.devfest.app.R
import at.devfest.app.data.app.model.ScheduleSlot
import at.devfest.app.data.app.model.Session
import at.devfest.app.ui.core.recyclerview.BaseViewHolder
import butterknife.BindView
import butterknife.ButterKnife

class ScheduleDayEntry(parent: ViewGroup, private val picasso: Picasso,
                       private val listener: OnSessionClickListener)
    : BaseViewHolder(parent, R.layout.schedule_day_entry) {
    init {
        ButterKnife.bind(this, itemView)
    }
    @BindView(R.id.schedule_day_entry_time)
    lateinit var time: TextView
    @BindView(R.id.schedule_day_entry_break_card)
    lateinit var breakCard: CardView
    @BindView(R.id.schedule_day_entry_break_text)
    lateinit var breakText: TextView
    @BindView(R.id.schedule_day_entry_break_time)
    lateinit var breakTime: TextView
    @BindView(R.id.schedule_day_entry_browse_card)
    lateinit var browseCard: CardView
    @BindView(R.id.schedule_day_entry_session_card)
    lateinit var sessionCard: CardView
    @BindView(R.id.schedule_day_entry_session_title)
    lateinit var sessionTitle: TextView
    @BindView(R.id.schedule_day_entry_session_time)
    lateinit var sessionTime: TextView
    @BindView(R.id.schedule_day_entry_session_room)
    lateinit var sessionRoom: TextView
    @BindView(R.id.schedule_day_entry_session_selected_state)
    lateinit var sessionSelectedState: ImageView
    @BindView(R.id.schedule_day_entry_slot_speakers)
    lateinit var sessionSpeakers: ViewGroup

    fun bindFreeSlot(slot: ScheduleSlot) {
        breakCard.visibility = View.GONE
        sessionCard.visibility = View.GONE

        bindTime(slot, true)
        browseCard.visibility = View.VISIBLE
        browseCard.setOnClickListener { _ -> listener.onFreeSlotClicked(slot) }
    }

    fun bindBreakSlot(slot: ScheduleSlot, session: Session, showTime: Boolean) {
        browseCard.visibility = View.GONE
        sessionCard.visibility = View.GONE

        bindTime(slot, showTime)
        breakCard.visibility = View.VISIBLE
        breakText.text = session.title
        breakTime.text = formatSessionTime(session)
    }

    fun bindSelectedSession(slot: ScheduleSlot, session: Session, showTime: Boolean, isSelected: Boolean) {
        breakCard.visibility = View.GONE
        browseCard.visibility = View.GONE

        bindTime(slot, showTime)
        sessionCard.visibility = View.VISIBLE
        sessionTitle.text = session.title
        sessionTime.text = formatSessionTime(session)
        bindRoom(session, sessionRoom)
        bindSessionSpeakers(session)

        val selectedRes = if (isSelected) R.drawable.sessions_list_selected else R.drawable.sessions_list_default
        // As of Android Support Library 23.3.0, support vector drawables can only be loaded
        // via app:srcCompat or setImageResource().
        // sessionSelectedState.setImageDrawable(ContextCompat.getDrawable(sessionSelectedState.getContext(), selectedRes));
        sessionSelectedState.setImageResource(selectedRes)

        sessionCard.setOnClickListener { _ -> listener.onSelectedSessionClicked(session) }
    }

    private fun formatTime(time: LocalDateTime?): String {
        val formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
        return time!!.format(formatter)
    }

    private fun bindTime(slot: ScheduleSlot, showTime: Boolean) {
        if (showTime) {
            val timeStr = formatTime(slot.time)
            time.visibility = View.VISIBLE
            time.text = timeStr
        } else {
            time.visibility = View.INVISIBLE
        }
    }

    private fun formatSessionTime(session: Session): String {
        val fromTime = session.fromTime
        val toTime = session.toTime
        return itemView.context.getString(R.string.schedule_day_entry_session_time_format, formatTime(fromTime), formatTime(toTime))
    }

    private fun bindRoom(session: Session, view: TextView?) {
        val room = session.room
        if (TextUtils.isEmpty(room)) {
            view!!.visibility = View.GONE
        } else {
            view!!.text = room
            view.visibility = View.VISIBLE
        }
    }

    private fun bindSessionSpeakers(session: Session) {
        sessionSpeakers.removeAllViews()

        val speakers = session.speakers
        if (speakers != null) {
            speakers.map { speaker -> ScheduleDayEntrySpeaker(sessionSpeakers.context, speaker, picasso) }
                    .forEach { entry -> sessionSpeakers.addView(entry) }
        }
    }

    interface OnSessionClickListener {
        fun onFreeSlotClicked(slot: ScheduleSlot)

        fun onSelectedSessionClicked(session: Session)
    }
}
