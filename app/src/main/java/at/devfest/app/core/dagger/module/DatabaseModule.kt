package at.devfest.app.core.dagger.module

import android.app.Application
import android.database.sqlite.SQLiteOpenHelper

import com.squareup.sqlbrite.BriteDatabase
import com.squareup.sqlbrite.SqlBrite
import com.squareup.sqlbrite.SqlBrite.Logger

import javax.inject.Singleton

import at.devfest.app.data.database.DbOpenHelper
import dagger.Module
import dagger.Provides
import rx.schedulers.Schedulers
import timber.log.Timber

@Module
class DatabaseModule {

    @Provides
    @Singleton
    internal fun provideSQLiteOpenHelper(application: Application): SQLiteOpenHelper {
        return DbOpenHelper(application)
    }

    @Provides
    @Singleton
    internal fun provideSqlBrite(): SqlBrite {
        return SqlBrite.create(Logger { message -> Timber.tag(TAG).v(message) })
    }

    @Provides
    @Singleton
    internal fun provideBriteDatabase(sqlBrite: SqlBrite, helper: SQLiteOpenHelper): BriteDatabase {
        return sqlBrite.wrapDatabaseHelper(helper, Schedulers.immediate())
    }

    companion object {

        private val TAG = "database"
    }
}
