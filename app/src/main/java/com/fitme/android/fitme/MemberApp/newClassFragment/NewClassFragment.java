package com.fitme.android.fitme.MemberApp.newClassFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;

import com.fitme.android.fitme.MemberApp.profileFragment.PerfilFragment;
import com.fitme.android.fitme.MemberApp.profileFragment.ProfileFragmentContract;
import com.fitme.android.fitme.R;
import com.fitme.android.fitme.com.fitme.android.fitme.model.GymClass;
import com.fitme.android.fitme.com.fitme.android.fitme.model.Trainer;
import com.fitme.android.fitme.validation.TextValidator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class NewClassFragment extends Fragment implements AdapterView.OnClickListener,NewClassFragmentContract.NewClassFragmentView {

    @BindView(R.id.classInfoModality)
    Spinner modalitySpinner;
    @BindView(R.id.classInfoIntensity)
    EditText intensityView;
    @BindView(R.id.classInfoRelaxation)
    EditText relaxationView;
    @BindView(R.id.classInfoWeightLoss)
    EditText weightLossView;
    @BindView(R.id.classInfoBegin)
    EditText beginView;
    @BindView(R.id.classInfoDescription)
    EditText descriptionView;
    @BindView(R.id.classInfoEnd)
    EditText endView;
    @BindView(R.id.classInfoDate)
    EditText dateView;
    @BindView(R.id.classInfoSpots)
    EditText spotsView;
    @BindView(R.id.btn_add_new_class)
    Button add_new_class;

    NewClassFragmentPresenter mPresenter;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;

    private static final String ARG_OBJECT = "user";
    private Object user;

    private OnFragmentInteractionListener mListener;

    public NewClassFragment() {
        // Required empty public constructor
    }

    public static NewClassFragment newInstance(Parcelable object) {
        NewClassFragment fragment = new NewClassFragment();
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

        mPresenter = new NewClassFragmentPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_class, container, false);
        ButterKnife.bind(this, view);

        add_new_class.setEnabled(false);
        intensityView.addTextChangedListener(new com.fitme.android.fitme.validation.TextValidator(intensityView) {
            @Override public void validate(TextView textView, String text) {
                if (!Pattern.matches("([0-9]|[1-9][0-9])", text)) {
                    intensityView.setError("The entered number is not valid (0-99 range)");
                    add_new_class.setEnabled(false);
                }
                else{
                    add_new_class.setEnabled(true);
                }
            }
        });

        weightLossView.addTextChangedListener(new com.fitme.android.fitme.validation.TextValidator(weightLossView) {
            @Override public void validate(TextView textView, String text) {
                if (!Pattern.matches("([0-9]|[1-9][0-9])", text)) {
                    weightLossView.setError("The entered number is not valid (0-99 range)");
                    add_new_class.setEnabled(false);
                }
                else{
                    add_new_class.setEnabled(true);
                }

            }
        });

        relaxationView.addTextChangedListener(new TextValidator(relaxationView) {
            @Override public void validate(TextView textView, String text) {
                if (!Pattern.matches("([0-9]|[1-9][0-9])", text)) {
                    relaxationView.setError("The entered number is not valid (0-99 range)");
                    add_new_class.setEnabled(false);
                }
                else{
                    add_new_class.setEnabled(true);
                }

            }
        });


        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.modalities));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        modalitySpinner.setAdapter(adapter);

        return view;
    }

    @OnClick(R.id.classInfoDate)
    public void getDate(View view) {
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.UK);

        Calendar newCalendar = Calendar.getInstance();

        new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateView.setText(dateFormatter.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @OnClick(R.id.classInfoBegin)
    public void getBeginClass(View view) {

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                beginView.setText(selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true).show();

    }


    @OnClick(R.id.classInfoEnd)
    public void getEndClass(View view){

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String endShow = String.format("%02d:%02d", selectedHour, selectedMinute);
                endView.setText(endShow);

            }
        }, hour, minute, true).show();
    }

    @OnClick(R.id.btn_add_new_class)
    public void addNewClass() {

        String modality = modalitySpinner.getSelectedItem().toString();
        int intensity = Integer.parseInt(intensityView.getText().toString());
        int relaxation = Integer.parseInt(relaxationView.getText().toString());
        int weightLoss = Integer.parseInt(weightLossView.getText().toString());
        String description = descriptionView.getText().toString();
        String beginDate = beginView.getText().toString();
        String endDate = endView.getText().toString();
        String date = dateView.getText().toString();
        int spots = Integer.parseInt(spotsView.getText().toString());

      if (checkIfAnyIsEmpty()){
        showErrorToast();
      }

      else {
        boolean sucessRegister = mPresenter.registerClass(modality, (Trainer) user, date, beginDate, endDate, spots, intensity,
            relaxation, weightLoss, description);
        if (!sucessRegister) {
          Toast toast = Toast.makeText(getActivity(), R.string.class_add_error, Toast.LENGTH_SHORT);
          View view = toast.getView();
          view.setBackgroundResource(R.drawable.textinputborder);
          toast.show();
        } else {
          GymClass gymClass = new GymClass(modality,(Trainer) user, date, beginDate, endDate, spots, intensity,
              relaxation, weightLoss, description, null);

          //List<GymClass> classList = ((Trainer) user).getGymClasses();
          //classList.add(gymClass);
          //((Trainer) user).setGymClasses(classList);

          getActivity().getSupportFragmentManager().popBackStack();

        }
      }
    }


  public void onButtonPressed(Parcelable object) {
    if (mListener != null) {
      mListener.onNewClassFragmentInteraction(object);
    }
  }


  @Override public void onDetach() {
    super.onDetach();
    mListener.onNewClassFragmentInteraction((Parcelable) user);
    mListener = null;
  }

  public interface OnFragmentInteractionListener {
    void onNewClassFragmentInteraction(Parcelable object);
  }

  public boolean checkIfAnyIsEmpty(){
        boolean isAnyEmpty = false;
        boolean view1 = TextUtils.isEmpty(intensityView.getText());
        boolean view2 = TextUtils.isEmpty(relaxationView.getText());
        boolean view3 = TextUtils.isEmpty(weightLossView.getText());
        boolean view4 = TextUtils.isEmpty(beginView.getText());
        boolean view5 = TextUtils.isEmpty(descriptionView.getText());
        boolean view6 = TextUtils.isEmpty(endView.getText());
        boolean view7 = TextUtils.isEmpty(dateView.getText());
        boolean view8 = TextUtils.isEmpty(spotsView.getText());

        if(view1 || view2 || view3 || view4 || view5 || view6 || view7 || view8){
            isAnyEmpty = true;
        }

        return isAnyEmpty;
  }

  public void showErrorToast(){
        Toast toast = Toast.makeText(getActivity(), R.string.class_add_error, Toast.LENGTH_SHORT);
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.textinputborder);
        toast.show();
  }


  @Override public void onClick(View v) {

  }
}
