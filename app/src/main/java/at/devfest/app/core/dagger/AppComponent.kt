package at.devfest.app.core.dagger

import at.devfest.app.DevFestApp
import at.devfest.app.core.dagger.module.*
import at.devfest.app.receiver.BootReceiver
import at.devfest.app.receiver.reminder.ReminderReceiver
import at.devfest.app.ui.drawer.DrawerActivity
import at.devfest.app.ui.home.HomeFragment
import at.devfest.app.ui.schedule.day.ScheduleDayFragment
import at.devfest.app.ui.schedule.pager.SchedulePagerFragment
import at.devfest.app.ui.sessions.details.SessionDetailsActivity
import at.devfest.app.ui.sessions.details.SessionFeedbackDialogFragment
import at.devfest.app.ui.sessions.list.SessionsListActivity
import at.devfest.app.ui.settings.SettingsFragment
import at.devfest.app.ui.speakers.details.SpeakerDetailsDialogFragment
import at.devfest.app.ui.speakers.list.SpeakersListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, DataModule::class, DatabaseModule::class, UtilsModule::class))
interface AppComponent {

    fun inject(fragment: HomeFragment)

    fun inject(activity: DrawerActivity)

    fun inject(fragment: SchedulePagerFragment)

    fun inject(fragment: ScheduleDayFragment)

    fun inject(activity: SessionsListActivity)

    fun inject(fragment: SessionFeedbackDialogFragment)

    fun inject(fragments: SpeakersListFragment)

    fun inject(activity: SessionDetailsActivity)

    fun inject(fragment: SpeakerDetailsDialogFragment)

    fun inject(fragment: SettingsFragment)

    fun inject(receiver: BootReceiver)

    fun inject(receiver: ReminderReceiver)

    class Initializer private constructor() {

        init {
            throw UnsupportedOperationException()
        }

        companion object {

            fun init(app: DevFestApp): AppComponent {
                return DaggerAppComponent.builder()
                        .appModule(AppModule(app))
                        .dataModule(DataModule())
                        .databaseModule(DatabaseModule())
                        .utilsModule(UtilsModule())
                        .build()
            }
        }
    }
}
