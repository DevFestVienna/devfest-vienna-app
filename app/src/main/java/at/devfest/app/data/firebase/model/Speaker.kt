package at.devfest.app.data.firebase.model

/**
 * Created by helmuth on 29.10.17.
 */
data class Speaker(val id: Int = 0, val name: String? = null, val lastname: String? = null,
                   val title: String? = null, val bio: String? = null, val website: String? = null,
                   val twitter: String? = null, val gplus: String? = null,
                   val xing: String? = null, val linkedin: String? = null,
                   val github: String? = null, val thumbnailUrl: String? = null)