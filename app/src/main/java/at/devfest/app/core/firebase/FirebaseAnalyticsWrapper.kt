package at.devfest.app.core.firebase

import android.os.Bundle
import at.devfest.app.utils.Analytics
import com.google.firebase.analytics.FirebaseAnalytics

/**
 * Created by helmuth on 19/08/16.
 */

class FirebaseAnalyticsWrapper(private val firebaseAnalytics: FirebaseAnalytics) : Analytics {


    internal fun logViewItem(id: Int, name: String, category: String) {
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, Integer.toString(id))
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name)
        bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, category)
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle)
    }

    override fun logViewSession(id: Int, title: String) {
        logViewItem(id, title, "session")
    }

    override fun logViewSpeaker(id: Int, name: String) {
        logViewItem(id, name, "speaker")
    }

    override fun logViewSessionSpeaker(id: Int, name: String) {
        logViewItem(id, name, "session_speaker")
    }

    override fun logViewScreen(screen: String) {
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, screen)
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM_LIST, bundle)
    }

    override fun logSelectSession(id: Int) {
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "presentation")
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, Integer.toString(id))
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
    }

    override fun setNotificationFlag(`val`: Boolean) {
        firebaseAnalytics.setUserProperty("notify_sessions", java.lang.Boolean.toString(`val`))
    }
}
