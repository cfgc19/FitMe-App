package com.fitme.android.fitme.validation;


import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ricardo on 27/12/2017.
 */

public abstract class TextValidator implements TextWatcher {
    private final TextView textView;

    public TextValidator(TextView textView) {
        this.textView = textView;
    }

    public abstract void validate(TextView textView, String text);

    @Override
    final public void afterTextChanged(Editable s) {
        String text = textView.getText().toString();
        validate(textView, text);
    }

    @Override
    final public void
    beforeTextChanged(CharSequence s, int start, int count, int after) {
         /* Needs to be implemented, but we are not using it. */
    }

    @Override
    final public void
    onTextChanged(CharSequence s, int start, int before, int count) {
         /* Needs to be implemented, but we are not using it. */
    }

    public final static boolean isValidEmail(String target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher((CharSequence)target).matches();
        }
    }

    public final static boolean isValidDate(String date)
    {


        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");

        Date testDate = null;

        try
        {
            testDate = sdf.parse(date);
        }

        catch (ParseException e)
        {
            return false;
        }

        if (!sdf.format(testDate).equals(date))
        {
            return false;
        }
        return true;

    }

}