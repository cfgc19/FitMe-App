package com.fitme.android.fitme.MemberApp.memberAppActivity;

import android.util.Log;
import com.fitme.android.fitme.com.fitme.android.fitme.model.Athlete;
import com.fitme.android.fitme.com.fitme.android.fitme.model.GymClass;
import com.fitme.android.fitme.com.fitme.android.fitme.model.Trainer;
import com.fitme.android.fitme.com.fitme.android.fitme.model.remote.AthleteService;
import com.fitme.android.fitme.com.fitme.android.fitme.model.remote.GymClassService;
import com.fitme.android.fitme.com.fitme.android.fitme.model.remote.TrainerService;

/**
 * Created by marciarocha on 24/12/17.
 */

public class MemberAppPresenter implements MemberAppContract.MemberAppPresenter {

    Athlete currentAthlete;
    Trainer currentTrainer;
    AthleteService mAthleteService;
    TrainerService mTrainerService;
    MemberAppContract.MemberAppView mView;
    GymClassService mGymClassService;

    public MemberAppPresenter() {
        mGymClassService = new GymClassService();
        mAthleteService = new AthleteService();

        mTrainerService = new TrainerService();
        mTrainerService.setMemberPresenter(this);
        mAthleteService.setMemberAppPresenter(this);
    }

    @Override
    public void attachView(MemberAppContract.MemberAppView view) {
        mView = view;
    }

    @Override
    public void dettachView() {
        mView = null;
    }

    @Override
    public String getCurrentUser(Object T) {
        String currentUserName = "";

        if(T instanceof Athlete) {
            currentAthlete = (Athlete) T;
            currentUserName = currentAthlete.getName();
        }

        else if (T instanceof Trainer) {
            currentTrainer = (Trainer) T;
            currentUserName = currentTrainer.getName();
            mView.makeFabVisible();
        }
        mView.showSchedule();
        return currentUserName;
    }

    @Override
    public void onNavegationItemClick(String id) {

        if (id.equalsIgnoreCase("profile")) {
            mView.showProfile();

        } else if (id.equalsIgnoreCase("schedule")) {
            mView.showSchedule();

        } else if (id.equalsIgnoreCase("logout")) {
            mView.logOut();
        }
    }

}
