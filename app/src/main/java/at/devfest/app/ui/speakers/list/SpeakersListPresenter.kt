package at.devfest.app.ui.speakers.list

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.View
import at.devfest.app.data.app.DataProvider
import at.devfest.app.ui.BaseFragmentPresenter

class SpeakersListPresenter(view: SpeakersListMvp.View, private val dataProvider: DataProvider, private val lifecycleOwner: LifecycleOwner) : BaseFragmentPresenter<SpeakersListMvp.View>(view), SpeakersListMvp.Presenter {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
    }

    private fun loadData() {
        dataProvider.liveSpeakers.observe(lifecycleOwner, Observer { speakers ->
            if (speakers != null) {
                speakers.sortWith(Comparator { lhs, rhs -> lhs.name!!.compareTo(rhs.name!!) })
                view.displaySpeakers(speakers)
            }
        })
        dataProvider.liveError.observe(lifecycleOwner, Observer { error ->
            if (error != null && error) {
                view.displayLoadingError()
            }
        })
    }
}
