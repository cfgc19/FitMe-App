package com.fitme.android.fitme.MemberApp.newClassFragment;

import com.fitme.android.fitme.BasePresenter;
import com.fitme.android.fitme.BaseView;
import com.fitme.android.fitme.com.fitme.android.fitme.model.GymClass;
import com.fitme.android.fitme.com.fitme.android.fitme.model.Trainer;

public interface NewClassFragmentContract {

  interface NewClassFragmentView extends BaseView<NewClassFragmentPresenter> {

  }

  interface NewClassFragmentPresenter extends BasePresenter<NewClassFragmentView> {

    boolean registerClass(String modality, Trainer user, String date, String beginDate, String endDate,
                       int spots, int intensity, int relaxation, int weightLoss,  String description);

  }
}


