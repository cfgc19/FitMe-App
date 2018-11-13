package com.fitme.android.fitme.Login;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;
import butterknife.OnTextChanged;
import com.afollestad.materialdialogs.MaterialDialog;
import com.fitme.android.fitme.Register.RegisterActivity;
import com.fitme.android.fitme.MemberApp.memberAppActivity.MemberActivity;
import com.fitme.android.fitme.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.fitme.android.fitme.validation.TextValidator;

import static com.fitme.android.fitme.utils.Codes.FIRST_SCREEN_KEY;
import static com.fitme.android.fitme.utils.Codes.USER_KEY;

public class LoginActivity extends AppCompatActivity implements LoginContract.AthleteLoginView {

    @BindView(R.id.password) EditText inputPassword;
    @BindView(R.id.email) EditText inputEmail;
    @BindView(R.id.btn_login) Button loginButton;

    LoginPresenter mLoginPresenter;
    String whosIn; //athlete ou trainer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mLoginPresenter = new LoginPresenter();
        mLoginPresenter.attachView(this);

        Intent intent = getIntent();
        whosIn = intent.getStringExtra(FIRST_SCREEN_KEY);

        Log.i("USER_TYPE -> ", whosIn);

        inputEmail.addTextChangedListener(new TextValidator(inputEmail) {
            @Override
            public void validate(TextView textView, String text) {
                if (!TextValidator.isValidEmail(text)) {
                    inputEmail.setError("The entered email is not valid");
                    loginButton.setEnabled(false);
                } else {
                    loginButton.setEnabled(true);
                }
            }
        });

    }
    @OnClick(R.id.btn_login)
    public void login() {

        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        mLoginPresenter.login(whosIn, email,password);

    }


    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra(FIRST_SCREEN_KEY, whosIn);
        this.startActivity(intent);
    }

    @Override
    public MaterialDialog showLoginDialog() {

        MaterialDialog materialDialog = new MaterialDialog.Builder(getApplicationContext())
                .title(R.string.action_login)
                .content(R.string.Authentication)
                .progress(true,0)
                .build();

        materialDialog.show();

        return materialDialog;
    }

    @Override
    public void stopLoginDialog(MaterialDialog materialDialog) {
        materialDialog.dismiss();
    }

    @Override
    public void showLoginResult(Boolean result, Object obj) {

        if (result) {
            Intent intent = new Intent(this, MemberActivity.class);
            intent.putExtra(USER_KEY , (Parcelable) obj);

            this.startActivity(intent);

        } else {

            Toast toast = Toast.makeText(getApplicationContext(), R.string.errror_login, Toast.LENGTH_SHORT);
            View view = toast.getView();
            view.setBackgroundResource(R.drawable.textinputborder);
            toast.show();
            Log.i("LOG IN ATHLETE -> ", "FAIL");
        }
    }


}
