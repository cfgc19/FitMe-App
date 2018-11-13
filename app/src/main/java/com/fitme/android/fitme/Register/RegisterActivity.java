package com.fitme.android.fitme.Register;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import android.widget.TextView;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import com.fitme.android.fitme.R;
import com.fitme.android.fitme.com.fitme.android.fitme.model.remote.AthleteService;
import com.fitme.android.fitme.com.fitme.android.fitme.model.remote.TrainerService;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.fitme.android.fitme.validation.TextValidator;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Pattern;

import static com.fitme.android.fitme.utils.Codes.FIRST_SCREEN_KEY;


public class RegisterActivity extends AppCompatActivity implements RegisterContract.RegisterView {

    static final String TAG = RegisterActivity.class.getSimpleName();

    @BindView(R.id.name) EditText inputName;
    @BindView(R.id.email) EditText inputEmail;
    @BindView(R.id.phone) EditText inputPhone;
    @BindView(R.id.password) EditText inputPassword;
    @BindView(R.id.employeeNumber) EditText inputEmployeeNumber;
    @BindView(R.id.dateBirth) EditText inputDateBirth;
    @BindView(R.id.btn_save) Button registerButton;

    RegisterPresenter mPresenter;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;

    String whosIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        Intent intent = getIntent();

        whosIn = intent.getStringExtra(FIRST_SCREEN_KEY);

        if (whosIn.equals("trainer")) {
            inputEmployeeNumber.setVisibility(View.VISIBLE);
        }
      inputEmail.addTextChangedListener(new TextValidator(inputEmail) {
        @Override
        public void validate(TextView textView, String text) {
          if (!TextValidator.isValidEmail(text)) {
            inputEmail.setError("The entered email is not valid");
            registerButton.setEnabled(false);
          } else {
            registerButton.setEnabled(true);
          }
        }
      });


      inputPhone.addTextChangedListener(new TextValidator(inputPhone) {
        @Override
        public void validate(TextView textView, String text) {

          if (!Pattern.matches("\\d{9}", text)) {
            inputPhone.setError("The entered phone number is not valid");
            registerButton.setEnabled(false);
          } else {
            registerButton.setEnabled(true);
          }

        }
      });

      inputEmployeeNumber.addTextChangedListener(new TextValidator(inputEmployeeNumber) {
        @Override
        public void validate(TextView textView, String text) {
          if (!Pattern.matches("\\d{5}", text)) {
            inputEmployeeNumber.setError("The entered employee number is not valid");
            registerButton.setEnabled(false);
          } else {
            registerButton.setEnabled(true);
          }
        }
      });

        mPresenter = new RegisterPresenter(new AthleteService(), new TrainerService());
        mPresenter.attachView(this);

    }

    @OnClick(R.id.dateBirth)
    public void getDate(View view){
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.UK);

        Calendar newCalendar = Calendar.getInstance();
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                inputDateBirth.setText(dateFormatter.format(newDate.getTime()));
            }}, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    public void saveInfo(View view) {


        String name = inputName.getText().toString();
        String email = inputEmail.getText().toString();
        String phone = inputPhone.getText().toString();
        String password = inputPassword.getText().toString();
        String employeeNo = inputEmployeeNumber.getText().toString();
        String dateBirth = inputDateBirth.getText().toString();

        mPresenter.register(whosIn, name, email, phone, password,employeeNo, dateBirth);
    }

    public void signIn(View view){
      finish(); // voltar para o login
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.dettachView();
    }

    @Override
    public void onRegistrationResult(boolean result) {

        if (result) {
            finish();
        }

    }

}
