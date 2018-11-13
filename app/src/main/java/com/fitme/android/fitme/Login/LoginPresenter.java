package com.fitme.android.fitme.Login;

import android.util.Log;

import com.fitme.android.fitme.com.fitme.android.fitme.model.remote.AthleteService;
import com.fitme.android.fitme.com.fitme.android.fitme.model.remote.TrainerService;

/**
 * Created by marciarocha on 21/12/17.
 */

public class LoginPresenter implements LoginContract.AthleteLoginPresenter {

    AthleteService mAthleteService;
    TrainerService mTrainerService;
    LoginContract.AthleteLoginView mView;
    LoginPresenter loginPresenter;

    public LoginPresenter() {
        mAthleteService = new AthleteService();
        setLoginPresenter();

        mTrainerService = new TrainerService();
        TrainerServiceSetLoginPresent();
    }

    @Override
    public void attachView(LoginContract.AthleteLoginView view) {
        mView = view;
    }

    @Override
    public void dettachView() {
        mView = null;
    }

    @Override
    public void login(String user, String email, String password) {
        Log.i("LOGIN ->" , "user wants to login");

        if (user.equals("athlete")) {
            Log.i("LOGIN ->" , "calling Athlete Service");
            mAthleteService.login(email, password);
        }

        if (user.equals("trainer")) {
            Log.i("LOGIN ->" , "calling Trainer Service");
            mTrainerService.login(email, password);
        }

    }

    @Override
    public void onLoginResult(boolean result, Object obj){
        mView.showLoginResult(result, obj);
    }



    //TODO remove this interface methods - should call directly in constructor - no need for this
    @Override
    public void setLoginPresenter(){
        mAthleteService.setLoginPresenter(this);
    }

    @Override
    public void TrainerServiceSetLoginPresent() {
        mTrainerService.setLoginPresenter(this);
    }
}
