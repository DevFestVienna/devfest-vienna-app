package at.devfest.app.ui.schedule.pager;

import android.arch.lifecycle.LifecycleOwner;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import at.devfest.app.data.app.DataProvider;
import at.devfest.app.ui.BaseFragmentPresenter;

public class SchedulePagerPresenter extends BaseFragmentPresenter<SchedulePagerMvp.View> implements SchedulePagerMvp.Presenter {

    private final DataProvider dataProvider;
    private final LifecycleOwner lifecycleOwner;

    public SchedulePagerPresenter(SchedulePagerMvp.View view, DataProvider dataProvider, LifecycleOwner lifecycleOwner) {
        super(view);
        this.dataProvider = dataProvider;
        this.lifecycleOwner = lifecycleOwner;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadData();
    }

    private void loadData() {
        dataProvider.getLiveSchedule().observe(lifecycleOwner, schedule -> {
            if (schedule != null) {
                view.displaySchedule(schedule);
            }
        });
        dataProvider.getLiveError().observe(lifecycleOwner, error -> {
            if (error != null && error) {
                view.displayLoadingError();
            }
        });
    }
}
