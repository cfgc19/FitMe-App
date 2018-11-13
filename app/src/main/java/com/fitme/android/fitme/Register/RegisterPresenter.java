package com.fitme.android.fitme.Register;

import android.util.Log;

import com.fitme.android.fitme.com.fitme.android.fitme.model.Athlete;
import com.fitme.android.fitme.com.fitme.android.fitme.model.Trainer;
import com.fitme.android.fitme.com.fitme.android.fitme.model.remote.AthleteService;
import com.fitme.android.fitme.com.fitme.android.fitme.model.remote.TrainerService;

/**
 * Created by marciarocha on 21/12/17.
 */

public class RegisterPresenter implements RegisterContract.RegisterPresenter {

    RegisterContract.RegisterView mRegisterView;
    AthleteService mAthleteService;
    TrainerService mTrainerService;

    public RegisterPresenter(AthleteService athleteService, TrainerService trainerService) {
        mAthleteService = athleteService;
        mTrainerService = trainerService;
    }

    @Override
    public void attachView(RegisterContract.RegisterView view) {
        mRegisterView = view;
    }

    @Override
    public void dettachView() {
        mRegisterView = null;
    }

    @Override
    public void register(String user, String name, String email, String phoneNumber, String password, String employeeNumber, String dateBirth) {

        boolean result = false;

        if(user.isEmpty() || name.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()|| password.isEmpty() || dateBirth.isEmpty() ) {

            result = false;
        } else {

            if (user.equals("trainer")) {

                if (employeeNumber.isEmpty()) {
                    result = false;
                }
                else {

                    Trainer trainer = new Trainer(employeeNumber, name, email, password, dateBirth, null);
                    mTrainerService.addTrainer(trainer);

                    result = mTrainerService.checkRegisterOperationResult();
                }


            }
            else if (user.equals("athlete")) {

                Athlete athlete = new Athlete(email, name, phoneNumber, password, dateBirth,  null);
                mAthleteService.addUser(athlete);

                result = mAthleteService.checkRegisterOperationResult();

            }

        }


        mRegisterView.onRegistrationResult(result);


    }




}
