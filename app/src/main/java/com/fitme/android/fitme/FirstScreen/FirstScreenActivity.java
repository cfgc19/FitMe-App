package com.fitme.android.fitme.FirstScreen;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.fitme.android.fitme.Login.LoginActivity;
import com.fitme.android.fitme.R;

import static com.fitme.android.fitme.utils.Codes.FIRST_SCREEN_KEY;
import static com.fitme.android.fitme.utils.Codes.REQ_WRITE_EXTERNAL_STORAGE;


public class FirstScreenActivity extends AppCompatActivity {
    
    String enter;
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);

        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.i("UPLOADING IMAGE", "asking permission");
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQ_WRITE_EXTERNAL_STORAGE);

        }
    }

    public void enterAthlete(View view) {

        enter = "athlete";

        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(FIRST_SCREEN_KEY, enter);
        this.startActivity(intent);
    }

    public void enterTrainer(View view) {
        enter = "trainer";

        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(FIRST_SCREEN_KEY, enter);
        this.startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQ_WRITE_EXTERNAL_STORAGE:

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                }
        }
    }
}
