package com.fitme.android.fitme.MemberApp.scheduleFragment;

import com.fitme.android.fitme.BasePresenter;
import com.fitme.android.fitme.BaseView;

/**
 * Created by marciarocha on 26/12/17.
 */

public interface ScheduleContract {

    interface ScheduleViewInterface extends BaseView<ScheduleContract.SchedulePresenterInterface> {

        void setPagerStart(int dayOfWeek);

    }

    interface SchedulePresenterInterface extends BasePresenter<ScheduleViewInterface> {

        void currentWeekDay();

    }
}
