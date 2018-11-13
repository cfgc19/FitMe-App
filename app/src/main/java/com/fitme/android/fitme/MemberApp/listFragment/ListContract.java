package com.fitme.android.fitme.MemberApp.listFragment;

import com.fitme.android.fitme.BasePresenter;
import com.fitme.android.fitme.BaseView;
import com.fitme.android.fitme.com.fitme.android.fitme.model.GymClass;

import java.util.ArrayList;

/**
 * Created by marciarocha on 26/12/17.
 */

public interface ListContract {

    interface ListFragmentView extends BaseView<ListFragmentPresenter> {

        void onScheduleFound(ArrayList<GymClass> gymClasses);

    }

    interface ListFragmentPresenter extends BasePresenter<ListFragmentView> {

        void onWeekDayClick(String weekday);

        void onSearchComplete( ArrayList<GymClass> gymClasses);

    }
}
