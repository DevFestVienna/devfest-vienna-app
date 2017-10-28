package at.devfest.app.ui.schedule.day;

import java.util.List;

import at.devfest.app.data.app.model.ScheduleSlot;

public interface ScheduleDayMvp {

    interface View {
        void initSlotsList(List<ScheduleSlot> slots);

        void refreshSlotsList();
    }
}
