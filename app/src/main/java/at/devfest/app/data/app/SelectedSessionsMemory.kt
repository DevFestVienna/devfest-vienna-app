package at.devfest.app.data.app

import org.threeten.bp.LocalDateTime
import java.util.concurrent.ConcurrentHashMap

import javax.inject.Inject
import javax.inject.Singleton

import at.devfest.app.data.app.model.Session

@Singleton
class SelectedSessionsMemory @Inject
constructor() {

    private val selectedSessions = ConcurrentHashMap<LocalDateTime, Int>()

    fun isSelected(session: Session): Boolean {
        val sessionId = selectedSessions[session.fromTime]
        return sessionId != null && session.id == sessionId
    }

    fun setSelectedSessions(selectedSessions: Map<LocalDateTime, Int>) {
        this.selectedSessions.clear()
        this.selectedSessions.putAll(selectedSessions)
    }

    operator fun get(slotTime: LocalDateTime): Int? {
        return selectedSessions[slotTime]
    }

    fun toggleSessionState(session: at.devfest.app.data.app.model.Session, insert: Boolean) {
        selectedSessions.remove(session.fromTime)
        if (insert && session.fromTime != null) {
            selectedSessions.put(session.fromTime, session.id)
        }
    }
}
