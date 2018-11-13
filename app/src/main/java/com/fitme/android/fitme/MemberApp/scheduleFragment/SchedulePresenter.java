package com.fitme.android.fitme.MemberApp.scheduleFragment;

import android.util.Log;

import android.view.View;
import android.widget.AdapterView;
import com.fitme.android.fitme.MemberApp.scheduleFragment.ScheduleContract.SchedulePresenterInterface;

import com.fitme.android.fitme.com.fitme.android.fitme.model.Athlete;
import com.fitme.android.fitme.com.fitme.android.fitme.model.Trainer;
import com.fitme.android.fitme.com.fitme.android.fitme.model.remote.AthleteService;
import com.fitme.android.fitme.com.fitme.android.fitme.model.remote.GymClassService;
import com.fitme.android.fitme.com.fitme.android.fitme.model.remote.TrainerService;
import java.util.Calendar;

/**
 * Created by marciarocha on 26/12/17.
 */

public class SchedulePresenter implements ScheduleContract.SchedulePresenterInterface {

    ScheduleContract.ScheduleViewInterface mView;
    TrainerService mTrainerService;
    AthleteService mAthleteService;

    public SchedulePresenter(){
      mTrainerService = new TrainerService();
      mTrainerService.setSchedulePresenter(this);
      mAthleteService = new AthleteService();
      mAthleteService.setSchedulePresenter(this);
    }


  @Override
    public void attachView(ScheduleContract.ScheduleViewInterface view) {
        mView = view;
    }

    @Override
    public void dettachView() {
        mView = null;
    }

    @Override
    public void currentWeekDay() {

        Calendar calendar = Calendar.getInstance();
        int currentDay = calendar.get(Calendar.DAY_OF_WEEK);

        mView.setPagerStart(currentDay - 1);
    }
}
