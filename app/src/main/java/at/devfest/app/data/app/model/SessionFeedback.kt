package at.devfest.app.data.app.model

/**
 * Created by helmuth on 23/08/16.
 */

data class SessionFeedback(val session: Session? = null, val sessionRating: Float? = null,
                           val speakerRating: Float? = null, val contentLevel: Float? = null)