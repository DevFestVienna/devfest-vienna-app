package at.devfest.app.data.network

import at.devfest.app.data.app.AppMapper
import at.devfest.app.data.app.model.Room
import at.devfest.app.data.network.model.Session
import at.devfest.app.data.network.model.Speaker
import javax.inject.Inject

class NetworkMapper @Inject
constructor(private val appMapper: AppMapper) {

    fun toAppSpeakers(from: List<Speaker?>?): List<at.devfest.app.data.app.model.Speaker>? {
        return if (from == null) {
            null
        } else from
                .filterNotNull()
                .map { (id, name, title, bio, website, twitter, gplus, xing, linkedin, github, photo) ->
                    at.devfest.app.data.app.model.Speaker(
                            id, name, title,
                            bio, website, twitter,
                            github, gplus, xing,
                            linkedin, photo)
                }

    }

    fun toAppSessions(from: List<Session?>?, speakersMap: Map<Int, at.devfest.app.data.app.model.Speaker>)
            : List<at.devfest.app.data.app.model.Session>? {
        return if (from == null) {
            null
        } else from
                .filterNotNull()
                .map { (id, startAt, duration, roomId, speakersId, title, description, photo) ->
                    at.devfest.app.data.app.model.Session(
                            id,
                            Room.getFromId(roomId).label,
                            appMapper.toSpeakersList(speakersId, speakersMap),
                            title, description,
                            startAt, startAt?.plusMinutes(duration.toLong()),
                            photo)
                }

    }
}
