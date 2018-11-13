package com.fitme.android.fitme.Login;

import com.afollestad.materialdialogs.MaterialDialog;
import com.fitme.android.fitme.BasePresenter;
import com.fitme.android.fitme.BaseView;

/**
 * Created by marciarocha on 21/12/17.
 */

public interface LoginContract {

    interface AthleteLoginView extends BaseView<AthleteLoginPresenter> {

        MaterialDialog showLoginDialog();

        void stopLoginDialog(MaterialDialog materialDialog);

        void showLoginResult(Boolean result, Object obj);
    }

    interface AthleteLoginPresenter extends BasePresenter<AthleteLoginView> {

        void login(String user, String email, String password);

        void onLoginResult(boolean result, Object obj);

        void setLoginPresenter();

        void TrainerServiceSetLoginPresent();

    }
}
