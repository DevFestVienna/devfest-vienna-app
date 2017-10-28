package at.devfest.app.data.network.model

import org.threeten.bp.LocalDateTime

data class Session(val id: Int = 0, val startAt: LocalDateTime? = null, val duration: Int = 0,
                   val roomId: Int = 0, val speakersId: List<Int>? = null,
                   val title: String? = null, val description: String? = null,
                   val photo: String? = null)