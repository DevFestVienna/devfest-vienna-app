package at.devfest.app.data.database

import at.devfest.app.utils.LocalDateTimeAdapter
import at.devfest.app.data.app.AppMapper
import at.devfest.app.data.app.model.Room
import at.devfest.app.data.database.model.Session
import at.devfest.app.data.database.model.Speaker
import org.threeten.bp.temporal.ChronoUnit
import javax.inject.Inject

class DbMapper @Inject
constructor(private val appMapper: AppMapper, private val localDateTimeAdapter: LocalDateTimeAdapter) {

    fun toAppSessions(from: List<Session>, speakersMap: Map<Int, at.devfest.app.data.app.model.Speaker>)
            : List<at.devfest.app.data.app.model.Session> {
        return from.map { session ->
            val fromTime = localDateTimeAdapter.fromText(session.startAt)
            val speakersIds = session.speakersIds.split(",").toList().map { s -> s.toInt() }
            at.devfest.app.data.app.model.Session(session.id, Room.getFromId(session.roomId).label,
                    appMapper.toSpeakersList(speakersIds, speakersMap),
                    session.title, session.description, fromTime, fromTime.plusMinutes(session.duration.toLong()),
                    session.photo)
        }
    }

    fun fromAppSession(from: at.devfest.app.data.app.model.Session?): Session? {
        return if (from == null) {
            null
        } else Session(from.id, localDateTimeAdapter.toText(from.fromTime!!),
                ChronoUnit.MINUTES.between(from.fromTime, from.toTime).toInt(),
                Room.getFromLabel(from.room).id,
                appMapper.toSpeakersIds(from.speakers)?.joinToString(), from.title, from.description,
                from.photo)

    }

    fun fromAppSpeaker(from: at.devfest.app.data.app.model.Speaker?): Speaker? {
        return if (from == null) {
            null
        } else Speaker(from.id, from.name, from.title, from.bio,
                from.website, from.twitter, from.github, from.gplus,
                from.xing, from.linkedin, from.photo)

    }

    fun toAppSpeakers(from: List<Speaker>?): List<at.devfest.app.data.app.model.Speaker>? {
        return if (from == null) {
            null
        } else from.map { speaker ->
            at.devfest.app.data.app.model.Speaker(speaker.id,
                    speaker.name, speaker.title, speaker.bio, speaker.website, speaker.twitter,
                    speaker.github, speaker.gplus, speaker.xing, speaker.linkedin, speaker.photo)
        }

    }
}
