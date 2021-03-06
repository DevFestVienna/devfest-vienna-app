package at.devfest.app.ui.schedule.pager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.threeten.bp.format.DateTimeFormatter;

import java.util.Locale;

import at.devfest.app.R;
import at.devfest.app.data.app.model.Schedule;
import at.devfest.app.ui.schedule.day.ScheduleDayFragmentBuilder;

public class SchedulePagerAdapter extends FragmentPagerAdapter {

    private final Schedule schedule;
    private final String dayPattern;
    private final boolean allSessions;

    public SchedulePagerAdapter(Context context, FragmentManager fm, Schedule schedule, boolean allSessions) {
        super(fm);
        this.schedule = schedule;
        this.allSessions = allSessions;
        dayPattern = context.getString(R.string.schedule_pager_day_pattern);
    }

    @Override
    public Fragment getItem(int position) {
        return new ScheduleDayFragmentBuilder(allSessions, schedule.get(position)).build();
    }

    @Override
    public int getCount() {
        return schedule.getSize();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dayPattern, Locale.getDefault());
        return schedule.get(position).getDate().format(formatter);
    }
}
