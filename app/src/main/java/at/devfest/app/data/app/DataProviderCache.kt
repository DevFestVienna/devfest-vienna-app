package at.devfest.app.data.app

import at.devfest.app.data.app.model.Session
import at.devfest.app.data.app.model.Speaker
import org.threeten.bp.LocalDateTime
import timber.log.Timber

class DataProviderCache {

    var sessions: List<Session>? = null
        get() {
            if (sessionsFetchedTime != null &&
                    sessionsFetchedTime!!.plusMinutes(CACHE_DURATION_MN).isAfter(LocalDateTime.now())) {
                Timber.d("Get sessions from cache")
                return field
            }
            return null
        }
        set(sessions) {
            field = sessions
            sessionsFetchedTime = LocalDateTime.now()
        }
    var sessionsFetchedTime: LocalDateTime? = null

    var speakers: List<Speaker>? = null
        get() {
            if (speakersFetchedTime != null &&
                    speakersFetchedTime!!.plusMinutes(CACHE_DURATION_MN).isAfter(LocalDateTime.now())) {
                Timber.d("Get speakers from cache")
                return field
            }
            return null
        }
        set(speakers) {
            field = speakers
            speakersFetchedTime = LocalDateTime.now()
        }

    var speakersFetchedTime: LocalDateTime? = null

    companion object {
        private val CACHE_DURATION_MN: Long = 10
    }
}
