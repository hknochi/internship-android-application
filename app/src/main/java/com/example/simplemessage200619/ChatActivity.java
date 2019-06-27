package com.example.simplemessage200619;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;
import java.util.function.Consumer;

public class ChatActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // Log.d("ASDASD", "MY MESSAGE STARTS from chatactivirtix");
        setContentView(R.layout.activity_chat);
        MainActivity main = new MainActivity();
        main.getHandler().post(main.getSendUpdatesToUI());
    }
}
