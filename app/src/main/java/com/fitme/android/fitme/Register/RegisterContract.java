package com.fitme.android.fitme.Register;

import com.fitme.android.fitme.BasePresenter;
import com.fitme.android.fitme.BaseView;

/**
 * Created by marciarocha on 21/12/17.
 */

public interface RegisterContract {

    interface RegisterView extends BaseView<RegisterPresenter> {

        void onRegistrationResult(boolean result);
    }

    interface RegisterPresenter extends BasePresenter<RegisterView> {

        void register(String user, String name, String email, String phoneNumber, String password, String employeeNumber, String birthDate);

    }

}
