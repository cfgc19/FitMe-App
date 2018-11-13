
package com.fitme.android.fitme.MemberApp.profileFragment;

import android.util.Base64;

import com.fitme.android.fitme.com.fitme.android.fitme.model.Athlete;
import com.fitme.android.fitme.com.fitme.android.fitme.model.Trainer;
import com.fitme.android.fitme.com.fitme.android.fitme.model.remote.AthleteService;
import com.fitme.android.fitme.com.fitme.android.fitme.model.remote.TrainerService;

import java.io.ByteArrayOutputStream;

/**
 * Created by marciarocha on 26/12/17.
 */

public class ProfileFragmentPresenter implements ProfileFragmentContract.ProfileFragmentPresenter {

    ProfileFragmentContract.ProfileFragmentView mView;
    AthleteService mAthleteService;
    TrainerService mTrainerService;
    String userType;

    public ProfileFragmentPresenter(){
        mAthleteService = new AthleteService();
        mAthleteService.setProfileFragmentPresenter(this);

        mTrainerService = new TrainerService();
        mTrainerService.setProfilePresenter(this);
    }

    @Override
    public void attachView(ProfileFragmentContract.ProfileFragmentView view) {
        mView = view;
    }

    @Override
    public void dettachView() {
        mView = null;
    }

    @Override
    public void getUserInfo(Object obj) {

        //TODO - STORE AND GET USER PICTURE

        if (obj instanceof Trainer) {
            userType = "trainer";

            String trainerName = ((Trainer) obj).getName();
            String trainerEmail = ((Trainer) obj).getEmail();
            String trainerPassword = ((Trainer) obj).getPassword();
            String trainerPhone = ((Trainer) obj).getPhone();
            String image = ((Trainer) obj).getImage();
            String dateBirth = ((Trainer) obj).getBirthDate();
            String employeeNumber = ((Trainer) obj).getCredential();

            mView.makeEmployeeNumberVisible();
            mView.showUserInfo(trainerEmail, trainerPhone, trainerName, trainerPassword, image, dateBirth , employeeNumber);

        }

        if (obj instanceof Athlete) {

            userType = "athlete";

            String athleteName = ((Athlete) obj).getName();
            String athleteEmail = ((Athlete) obj).getEmail();
            String athletePassword = ((Athlete) obj).getPassword();
            String athletePhone = ((Athlete) obj).getPhoneNumber();
            String image = ((Athlete) obj).getImage();
            String dateBirth = ((Athlete) obj).getBirthDate();

            mView.showUserInfo(athleteEmail, athletePhone, athleteName, athletePassword, image, dateBirth, "");


        }

    }

    @Override
    public void saveImage(String image, String email) {
        if (userType.equalsIgnoreCase("trainer")) {
            mTrainerService.setImage(image, email);
        }
        else if (userType.equalsIgnoreCase("athlete")) {
            mAthleteService.setImage(image, email);
        }
    }

    @Override
    public void saveInfo(String userEmail, String oldPhone, String newPhone, String oldPassword, String newPassword) {
      if (userType.equalsIgnoreCase("trainer")) {
            if(oldPhone == null) {
                mTrainerService.updatePhone(newPhone, userEmail);
            }
            else {
                if (!oldPhone.equals(newPhone)) {
                    mTrainerService.updatePhone(newPhone, userEmail);
                }
            }

            if(!oldPassword.equals(newPassword)) {
                mTrainerService.updatePassword(newPassword, userEmail);
            }

        } else if (userType.equalsIgnoreCase("athlete")) {

            if (!oldPhone.equals(newPhone)) {
                mAthleteService.updatePhone(newPhone, userEmail);
            }

            if(!oldPassword.equals(newPassword)) {
                mAthleteService.updatePassword(newPassword, userEmail);
            }
        }
    }

    @Override
    public void onPasswordUpdated(String password) {
        mView.updatePasswordField(password);
    }

    @Override
    public void onPhoneUpdated(String phone) {
        mView.updatePhoneField(phone);

    }


}
