package com.fitme.android.fitme.MemberApp.profileFragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fitme.android.fitme.R;
import com.fitme.android.fitme.validation.TextValidator;

import com.fitme.android.fitme.com.fitme.android.fitme.model.Athlete;
import com.fitme.android.fitme.com.fitme.android.fitme.model.Trainer;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import java.util.regex.Pattern;


public class PerfilFragment extends Fragment implements AdapterView.OnItemClickListener, ProfileFragmentContract.ProfileFragmentView {

    private static final String ARG_OBJECT = "user";

    private static int RESULT_LOAD_IMAGE = 1;

    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.namePerfil)
    TextView userName;
    @BindView(R.id.cellNumberPerfil)
    EditText userPhone;
    @BindView(R.id.datePerfil)
    TextView dateView;
    @BindView(R.id.passwordPerfil)
    EditText passwordView;
    @BindView(R.id.save_button)
    Button saveInfo;
    @BindView(R.id.employeeNumberPerfil)
    TextView employeeNumberView;
    @BindView(R.id.linearLayoutEmployeeNumber)
    LinearLayout linearLayoutEmployeeNumber;

    ProfileFragmentPresenter mPresenter;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;

    private String userEmail;
    private String userPassword;
    private String userPhoneNumber;
    private String userDateBirth;
    private String userEmployeeNumber;

    private Object user;

    private OnFragmentInteractionListener mListener;

    public PerfilFragment() {
    }

    public static PerfilFragment newInstance(Parcelable object) {
        PerfilFragment fragment = new PerfilFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_OBJECT, object);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = getArguments().getParcelable(ARG_OBJECT);
            Log.i("Got user -> ", user.toString());
        }

        mPresenter = new ProfileFragmentPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        ButterKnife.bind(this, view);

        mPresenter.getUserInfo(user);

        userPhone.addTextChangedListener(new TextValidator(userPhone) {

            @Override
            public void validate(TextView textView, String text) {

                if (!Pattern.matches("\\d{9}", text)) {
                    userPhone.setError("The entered phone number is not valid");
                    saveInfo.setEnabled(false);
                } else {
                    saveInfo.setEnabled(true);
                }
            }
        });


        dateView.addTextChangedListener(new TextValidator(dateView) {
            @Override
            public void validate(TextView textView, String text) {
                if (!TextValidator.isValidDate(text)) {
                    dateView.setError("The entered date of birth is not valid");
                    saveInfo.setEnabled(false);
                } else {
                    saveInfo.setEnabled(true);
                }
            }
        });

        return view;
    }

    @OnClick(R.id.imageView)
    public void chooseImage() {
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

        } else {
            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, RESULT_LOAD_IMAGE);
        }
    }


    @OnClick(R.id.save_button)
    public void saveInfo() {

        String newPhone = userPhone.getText().toString();
        String newPassword = passwordView.getText().toString();

        mPresenter.saveInfo(userEmail, userPhoneNumber, newPhone, userPassword, newPassword);

        userPhoneNumber = newPhone;
        userPassword = newPassword;

        if(user instanceof Trainer){
        ((Trainer) user).setPhone(newPhone);
        ((Trainer) user).setPassword(newPassword);
        }
        else{
        ((Athlete) user).setPhoneNumber(newPhone);
        ((Athlete) user).setPassword(newPassword);
        }

        mListener.onProfileFragmentInteraction((Parcelable) user);

        getActivity().getSupportFragmentManager().popBackStack();
    }


    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;

        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK && null != data) {

            Uri selectedImage = data.getData();
            String filepath = "";
            Cursor cursor = getActivity().getContentResolver().query(selectedImage, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            cursor.moveToFirst();
            filepath = cursor.getString(0);
            cursor.close();

            imageView.setColorFilter(Color.TRANSPARENT);

            Glide.with(getActivity())
                    .load(filepath)
                    .into(imageView);

            mPresenter.saveImage(filepath, userEmail);

          if(user instanceof Trainer){
            ((Trainer) user).setImage(filepath);
          }
          else{
            ((Athlete) user).setImage(filepath);
          }

            Log.i("uri", selectedImage.toString());

        }

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        if (view == dateView) {
            fromDatePickerDialog.show();
        }
        Toast.makeText(getActivity(), "Item" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUserInfo(String email, String phone, String name, String password, String image, String birthDate, String employeeNumber) {

        userEmail = email;

        userName.setText(name);

        userPhone.setText(phone);
        userPhoneNumber = phone;

        passwordView.setText(password);
        userPassword = password;

        dateView.setText(birthDate);
        userDateBirth = birthDate;

        employeeNumberView.setText(employeeNumber);
        userEmployeeNumber = employeeNumber;

        if (image != null) {
            imageView.setColorFilter(Color.TRANSPARENT);
            Log.i(image.toString(), "URI found " + image.toString());
            Glide.with(getActivity())
                    .load(image)
                    .into(imageView);
        }
    }

    @Override
    public void updatePhoneField(String phone) {
        userPhone.setText(phone);
    }

    @Override
    public void updatePasswordField(String password) {
        passwordView.setText(password);
    }

    public interface OnFragmentInteractionListener {
      void onProfileFragmentInteraction(Parcelable object);
    }

    @Override public void makeEmployeeNumberVisible() {
      linearLayoutEmployeeNumber.setVisibility(View.VISIBLE);
    }

    public void onButtonPressed(Parcelable object) {
      if (mListener != null) {
        mListener.onProfileFragmentInteraction(object);
      }
    }

}
