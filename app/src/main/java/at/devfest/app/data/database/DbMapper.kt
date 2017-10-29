package at.devfest.app.data.database

import at.devfest.app.core.moshi.LocalDateTimeAdapter
import at.devfest.app.data.app.AppMapper
import at.devfest.app.data.app.model.Room
import at.devfest.app.data.database.model.Session
import at.devfest.app.data.database.model.Speaker
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import org.threeten.bp.temporal.ChronoUnit
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class DbMapper @Inject
constructor(moshi: Moshi, private val appMapper: AppMapper, private val localDateTimeAdapter: LocalDateTimeAdapter) {
    private val intListAdapter: JsonAdapter<List<Int?>>
            = moshi.adapter(Types.newParameterizedType(List::class.java, java.lang.Integer::class.java))

    fun toAppSessions(from: List<Session>, speakersMap: Map<Int, at.devfest.app.data.app.model.Speaker>)
            : List<at.devfest.app.data.app.model.Session> {
        return from.map { session ->
            val fromTime = localDateTimeAdapter.fromText(session.startAt)
            at.devfest.app.data.app.model.Session(session.id, Room.getFromId(session.roomId).label,
                    appMapper.toSpeakersList(deserialize(session.speakersIds), speakersMap),
                    session.title, session.description, fromTime, fromTime.plusMinutes(session.duration.toLong()),
                    session.photo)
        }
    }

    fun fromAppSession(from: at.devfest.app.data.app.model.Session?): Session? {
        return if (from == null) {
            null
        } else Session(from.id, localDateTimeAdapter.toText(from.fromTime),
                ChronoUnit.MINUTES.between(from.fromTime!!, from.toTime).toInt(),
                Room.getFromLabel(from.room).id,
                serialize(appMapper.toSpeakersIds(from.speakers)), from.title, from.description,
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

    private fun serialize(toSerialize: List<Int>?): String? {
        var result: String? = null
        if (toSerialize != null) {
            result = intListAdapter.toJson(toSerialize)
        }
        return result
    }

    private fun deserialize(toDeserialize: String?): List<Int>? {
        var result: List<Int?>? = null
        if (toDeserialize != null) {
            try {
                result = intListAdapter.fromJson(toDeserialize)
            } catch (e: IOException) {
                Timber.e(e, "Error getting speakersIds for String: %s", toDeserialize)
            }

        }
        return result?.filterNotNull()
    }
}
