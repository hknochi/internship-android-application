package com.example.simplemessage200619;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.function.Consumer;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    public static final String EXTRA_MESSAGE = "com.example.SimpleMessage200619.nickname";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1950; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);

        Spinner spinYear = (Spinner)findViewById(R.id.yearspin);
        spinYear.setAdapter(adapter);
    }

    public void sendRegisterRequest(View view) {



        EditText nickname = (EditText) findViewById(R.id.editnickname);
        EditText Pass1 = (EditText) findViewById(R.id.password);
        EditText FN1 = (EditText) findViewById(R.id.firstname);
        EditText LN1 = (EditText) findViewById(R.id.name);
        Spinner GE1 = (Spinner) findViewById(R.id.gender_spinner);
        Spinner BY1 = (Spinner) findViewById(R.id.yearspin);
        String text=BY1.getSelectedItem().toString();
        Integer yb = Integer.valueOf(text);

        Intent intent = getIntent();
        String message = intent.getStringExtra(RegisterActivity.EXTRA_MESSAGE);
        UserProfile userProfile = new UserProfile();
        setContentView(R.layout.activity_register);

        userProfile.username=nickname.getText().toString();
        userProfile.password= Pass1.getText().toString();
        userProfile.firstName = FN1.getText().toString();
        userProfile.lastName = LN1.getText().toString();
        userProfile.gender = GE1.getSelectedItem().toString();
        userProfile.yearOfBirth = yb;
      //  Log.e(TAG, userProfile.toString());
        Consumer<ResponseStatus> myConsumer = new Consumer<ResponseStatus>() {
            @Override
            public void accept(ResponseStatus responseStatus) {
                if (responseStatus.getStatus() == 1) {
                    setContentView(R.layout.activity_main);
                } else {
                    setContentView(R.layout.activity_register);
                }
            }
        };
        RestController.registerUser(getApplicationContext(), userProfile, myConsumer);
    }



    public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)
        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        }
    }


}
