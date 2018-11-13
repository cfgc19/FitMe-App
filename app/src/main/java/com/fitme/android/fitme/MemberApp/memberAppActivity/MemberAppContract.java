package com.fitme.android.fitme.MemberApp.memberAppActivity;

import com.fitme.android.fitme.BasePresenter;
import com.fitme.android.fitme.BaseView;
import com.fitme.android.fitme.com.fitme.android.fitme.model.Athlete;
import com.fitme.android.fitme.com.fitme.android.fitme.model.GymClass;
import com.fitme.android.fitme.com.fitme.android.fitme.model.Trainer;

/**
 * Created by marciarocha on 24/12/17.
 */

public class MemberAppContract {

    interface MemberAppView extends BaseView<MemberAppPresenter> {

        void makeFabVisible();

        void showProfile();

        void showSchedule();

        void logOut();


    }

    interface MemberAppPresenter extends BasePresenter<MemberAppView> {

        String getCurrentUser(Object T);

        void onNavegationItemClick(String id);

    }
}


