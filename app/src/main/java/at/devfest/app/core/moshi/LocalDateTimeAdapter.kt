package at.devfest.app.core.moshi

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

import java.util.Locale

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDateTimeAdapter @Inject
constructor() {

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.US)

    @ToJson
    fun toText(dateTime: LocalDateTime): String {
        return dateTime.format(formatter)
    }

    @FromJson
    fun fromText(text: String): LocalDateTime {
        return LocalDateTime.parse(text, formatter)
    }
}
