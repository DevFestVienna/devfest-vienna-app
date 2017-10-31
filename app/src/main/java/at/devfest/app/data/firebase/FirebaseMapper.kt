package at.devfest.app.data.firebase

import at.devfest.app.data.app.AppMapper
import at.devfest.app.data.app.model.Room
import at.devfest.app.data.firebase.model.Session
import at.devfest.app.data.firebase.model.Speaker
import javax.inject.Inject

/**
 * Created by helmuth on 29.10.17.
 */

class FirebaseMapper @Inject
constructor(private val appMapper: AppMapper) {

    fun toAppSpeakers(from: List<Speaker?>?): List<at.devfest.app.data.app.model.Speaker>? {
        return if (from == null) {
            null
        } else from
                .filterNotNull()
                .map { speaker ->
                    at.devfest.app.data.app.model.Speaker(
                            speaker.id, speaker.name, speaker.title,
                            speaker.bio, speaker.website, speaker.twitter,
                            speaker.github, speaker.gplus, speaker.xing,
                            speaker.linkedin, speaker.thumbnailUrl, speaker.thumbnail)
                }

    }

    fun toAppSessions(from: List<Session?>?, speakersMap: Map<Int, at.devfest.app.data.app.model.Speaker>)
            : List<at.devfest.app.data.app.model.Session>? {
        return if (from == null) {
            null
        } else from
                .filterNotNull()
                .map { session ->
                    at.devfest.app.data.app.model.Session(
                            session.id,
                            Room.getFromId(session.roomId).label,
                            appMapper.toSpeakersList(session.speakers, speakersMap),
                            session.title, session.description,
                            session.startAtTime, session.endAtTime,
                            session.thumbnailUrl, session.thumbnail)
                }

    }

}

