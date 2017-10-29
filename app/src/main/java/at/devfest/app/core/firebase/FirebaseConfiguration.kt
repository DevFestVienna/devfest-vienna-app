package at.devfest.app.core.firebase

import com.google.firebase.remoteconfig.FirebaseRemoteConfig

import at.devfest.app.R
import at.devfest.app.utils.Configuration

/**
 * Created by helmuth on 19/08/16.
 *
 *
 * This class implements a Configuration using Firebase Remote Config.
 */

class FirebaseConfiguration(private val cfg: FirebaseRemoteConfig) : Configuration {

    init {
        // load default values
        cfg.setDefaults(R.xml.defaults)
        // start fetching new values
        cfg.fetch()
    }

    override fun refresh() {
        // activate newly fetched values - if existing
        cfg.activateFetched()
        // and fetch new values in the background
        cfg.fetch()
    }

    override fun getBoolean(key: String): Boolean {
        return cfg.getBoolean(key)
    }

    override fun getLong(key: String): Long {
        return cfg.getLong(key)
    }

    override fun getDouble(key: String): Double {
        return cfg.getDouble(key)
    }

    override fun getString(key: String): String {
        return cfg.getString(key)
    }
}
