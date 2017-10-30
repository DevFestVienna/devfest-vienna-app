package at.devfest.app.data.firebase.model

import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

/**
 * Created by helmuth on 29.10.17.
 */

data class Session(val id: Int = 0, val startAt: String? = null, val duration: Int = 0,
                   val roomId: Int = 0, val speakers: List<Int>? = null,
                   val title: String? = null, val description: String? = null,
                   val track: String? = null,
                   val thumbnailUrl: String? = null) {
    private val dateTimeFormatter: DateTimeFormatter
            = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

    val startAtTime: LocalDateTime
        get() = LocalDateTime.parse(startAt, dateTimeFormatter)

    val endAtTime: LocalDateTime
        get() = startAtTime.plusMinutes(duration.toLong())
}