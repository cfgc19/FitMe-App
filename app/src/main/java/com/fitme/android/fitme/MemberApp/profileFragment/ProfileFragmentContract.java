package com.fitme.android.fitme.MemberApp.profileFragment;

import com.fitme.android.fitme.BasePresenter;
import com.fitme.android.fitme.BaseView;

import com.fitme.android.fitme.com.fitme.android.fitme.model.Athlete;
import java.io.ByteArrayOutputStream;

/**
 * Created by marciarocha on 26/12/17.
 */

public interface ProfileFragmentContract {

    interface ProfileFragmentView extends BaseView<ProfileFragmentPresenter> {

        void showUserInfo(String email, String phone, String name, String password, String image, String birthDate, String employeeNumber);

        void updatePhoneField(String phone);

        void updatePasswordField(String password);

        void makeEmployeeNumberVisible();

    }

    interface ProfileFragmentPresenter extends BasePresenter<ProfileFragmentView> {

        void getUserInfo(Object obj);

        void saveImage(String image, String email);

        void saveInfo(String userEmail, String oldPhone, String newPhone, String oldPassword, String newPassword);

        void onPasswordUpdated(String password);

        void onPhoneUpdated(String phone);
    }

}
