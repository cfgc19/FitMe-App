package com.fitme.android.fitme.MemberApp.classInfoFragment;

import com.fitme.android.fitme.BasePresenter;
import com.fitme.android.fitme.BaseView;
import com.fitme.android.fitme.com.fitme.android.fitme.model.Athlete;
import com.fitme.android.fitme.com.fitme.android.fitme.model.GymClass;

public interface ClassFragmentContract {

  interface ClassFragmentView extends BaseView<ClassFragmentPresenter> {

    void checkedupdateAthlete();
  }

  interface ClassFragmentPresenter extends BasePresenter<ClassFragmentView> {
    void subscribeClass(GymClass gymClass, Athlete athlete);

    void unsubscribeClass(GymClass gymClass, Athlete athlete);

    void updateAthlete();

  }
}
