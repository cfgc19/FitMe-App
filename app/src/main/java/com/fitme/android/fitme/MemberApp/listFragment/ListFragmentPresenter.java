package com.fitme.android.fitme.MemberApp.listFragment;

import android.util.Log;

import com.fitme.android.fitme.com.fitme.android.fitme.model.Athlete;
import com.fitme.android.fitme.com.fitme.android.fitme.model.GymClass;
import com.fitme.android.fitme.com.fitme.android.fitme.model.Trainer;
import com.fitme.android.fitme.com.fitme.android.fitme.model.remote.AthleteService;
import com.fitme.android.fitme.com.fitme.android.fitme.model.remote.GymClassService;

import com.fitme.android.fitme.com.fitme.android.fitme.model.remote.TrainerService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TreeMap;


/**
 * Created by marciarocha on 26/12/17.
 */

public class ListFragmentPresenter implements ListContract.ListFragmentPresenter{

    ListContract.ListFragmentView mView;
    GymClassService gymClassService;

    public ListFragmentPresenter(){
        gymClassService = new GymClassService();
        gymClassService.setListFragmentPresenter(this);
    }

    @Override
    public void attachView(ListContract.ListFragmentView view) {
        mView = view;
    }

    @Override
    public void dettachView() {
        mView = null;
    }

    // - when user clicks on tab
    @Override
    public void onWeekDayClick(String weekday) {

        int day = weekDayToInt(weekday);
        TreeMap weekDays = getCurrentWeek();

        Date currentDate = (Date) weekDays.get(day);

        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);

        int thisday = cal.get(Calendar.DAY_OF_MONTH);
        int thismonth = cal.get(Calendar.MONTH) + 1; //Calendar.MONTH goes 0-11
        int thisyear = cal.get(Calendar.YEAR);
        
        String dateToFind = thisday + "-" + thismonth + "-" + thisyear ;

        gymClassService.getClasses(dateToFind);

    }

    @Override
    public void onSearchComplete (ArrayList<GymClass> gymClasses){

      if(mView != null){
        mView.onScheduleFound(gymClasses);
      }

    }

    public int weekDayToInt(String weekday){

        int day = 0;

        switch (weekday) {
            case "0":
                day = 1;
                break;
            case "1":
                day = 2;
                break;
            case "2":
                day = 3;
                break;
            case "3":
                day = 4;
                break;
            case "4":
                day = 5;
                break;
            case "5":
                day = 6;
                break;
            case "6":
                day = 7;
                break;
        }

        return day;
    }

    public TreeMap getCurrentWeek(){

        Calendar cal = Calendar.getInstance();

        Date today = cal.getTime();

        int currentDay = cal.get(Calendar.DAY_OF_WEEK);

        TreeMap<Integer,Date> currentWeek = new TreeMap<>();
        currentWeek.put(currentDay, today);

        for (int i = 1 ; i < 8; i++) {

            int differenceBetweenDays = Math.abs(currentDay-i);

            cal = Calendar.getInstance();

            if(i < currentDay) {

                cal.add(Calendar.DATE, - differenceBetweenDays);

                Date day = cal.getTime();
                currentWeek.put(i,day);

            } else if (i == currentDay ) {


            } else if (i > currentDay) {
                cal.add(Calendar.DATE, differenceBetweenDays);
                Date day = cal.getTime();
                currentWeek.put(i,day);
            }

        }

        Log.i("CURRENT WEEK -> ", currentWeek.toString());

        return currentWeek;
    }


}
