package com.fitme.android.fitme.MemberApp.classInfoFragment;

import com.fitme.android.fitme.MemberApp.newClassFragment.NewClassFragmentContract;
import com.fitme.android.fitme.com.fitme.android.fitme.model.Athlete;
import com.fitme.android.fitme.com.fitme.android.fitme.model.GymClass;
import com.fitme.android.fitme.com.fitme.android.fitme.model.remote.AthleteService;
import com.fitme.android.fitme.com.fitme.android.fitme.model.remote.GymClassService;
import com.fitme.android.fitme.com.fitme.android.fitme.model.remote.TrainerService;

public class ClassFragmentPresenter implements ClassFragmentContract.ClassFragmentPresenter{

  ClassFragmentContract.ClassFragmentView mView;
  GymClassService mGymClassService;
  AthleteService mAthleteService;

  public ClassFragmentPresenter() {
    mGymClassService = new GymClassService();
    mAthleteService = new AthleteService();
    mAthleteService.setClassFragmentPresenter(this);
  }

  @Override
  public void attachView(ClassFragmentContract.ClassFragmentView view) {
    mView = view;
  }

  @Override
  public void dettachView() {
    mView = null;
  }

  @Override
  public void subscribeClass(GymClass gymClass, Athlete athlete){
    mAthleteService.updateListClass(athlete.getEmail(), gymClass);
    mGymClassService.updateListAthlets(athlete, gymClass);
  }

  @Override
  public void unsubscribeClass(GymClass gymClass, Athlete athlete){
    mAthleteService.removeClassfromList(athlete.getEmail(), gymClass);
    mGymClassService.removeAthletfromList(athlete, gymClass);
  }

  @Override public void updateAthlete() {
    mView.checkedupdateAthlete();
  }
}
