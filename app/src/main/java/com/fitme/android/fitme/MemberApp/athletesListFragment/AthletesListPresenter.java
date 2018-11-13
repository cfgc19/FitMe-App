package com.fitme.android.fitme.MemberApp.athletesListFragment;

import com.fitme.android.fitme.com.fitme.android.fitme.model.remote.AthleteService;
import com.fitme.android.fitme.com.fitme.android.fitme.model.remote.GymClassService;

public class AthletesListPresenter implements AthletesListContract.AthletesListPresenter{

  AthletesListContract.AthletesListView mView;
  GymClassService mGymClassService;
  AthleteService mAthleteService;

  public AthletesListPresenter() {
    mGymClassService = new GymClassService();
    mAthleteService = new AthleteService();
  }

  @Override
  public void attachView(AthletesListContract.AthletesListView view) {
    mView = view;
  }

  @Override
  public void dettachView() {
    mView = null;
  }

}
