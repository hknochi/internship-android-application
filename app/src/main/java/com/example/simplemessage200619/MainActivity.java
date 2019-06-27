package com.example.simplemessage200619;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

public class MainActivity extends AppCompatActivity {
    private static final String TAG2 = "MainActivity";
    public static final String EXTRA_MESSAGE2 = "com.example.SimpleMessage200619.nickname";
    public static final String MESSAGES_LIST = "TEST";

    private TextWatcher watcher;
    private TextView textView;
    private Handler handler = new Handler();
    private Intent globalIntent;


    private Runnable sendUpdatesToUI = new Runnable() {
        public void run() {
            ScrollView scroller = findViewById(R.id.scrollView2);
            scroller.fullScroll(View.FOCUS_DOWN);
            reloadMessages();
           // Log.d("HANDLER", "HELLO FROM HANDLER");
            textView = (TextView) findViewById(R.id.Chatspace);
            textView.setText(globalIntent.getStringExtra(MESSAGES_LIST));
            handler.postDelayed(this, 1000); // 10 seconds
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        globalIntent = getIntent();

        textView = (TextView) findViewById(R.id.Chatspace);
        handler.removeCallbacks(sendUpdatesToUI);
    }

    public void sendRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        //RestController.getBooks2(getApplicationContext(), null);
    }

    public void sendLoginRequest(View view) {
        EditText nickname = (EditText) findViewById(R.id.nickname2);
        EditText Pass1 = (EditText) findViewById(R.id.password2);

        Intent intent = getIntent();
        UserLogin userLogin = new UserLogin();
        setContentView(R.layout.activity_main);


        userLogin.username = nickname.getText().toString();
        intent.putExtra(MainActivity.EXTRA_MESSAGE2, userLogin.username);

        userLogin.password = Pass1.getText().toString();

       // Log.e(TAG2, userLogin.toString());
        Consumer<ResponseStatus> myConsumer = new Consumer<ResponseStatus>() {
            @Override
            public void accept(ResponseStatus responseStatus) {
                if (responseStatus.getStatus() == 1) {
                    handler.postDelayed(sendUpdatesToUI, 0); // 10 seconds
                    setContentView(R.layout.activity_chat);
                } else {
                    setContentView(R.layout.activity_main);
                }
            }
        };
        RestController.loginUser(getApplicationContext(), userLogin, myConsumer);
    }


    public void sendMsg(View view) {
        final Intent intent = getIntent();
        EditText msg = (EditText) findViewById(R.id.textInput);

        //intent.putExtra("TEST", tname);
      //  Log.d("ADASDSD", msg.getText().toString());
        Message message = new Message();

        message.msgContent = msg.getText().toString();
        msg.setText("");
        message.transmitterUsername = intent.getStringExtra(MainActivity.EXTRA_MESSAGE2);
        message.receiverUsername = "usr";
      //  Log.d("Message", message.toString());
        Consumer<ResponseStatus> myConsumer = new Consumer<ResponseStatus>() {
            @Override
            public void accept(ResponseStatus responseStatus) {
                if (responseStatus.getStatus() == 1) {
                    reloadMessages();
                } else {
                    reloadMessages();
                }
            }
        };
        RestController.message(getApplicationContext(), message, myConsumer);
    }
    /*public void sendLogout (View view){
        Intent intent5= new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent5);
    }*/

    public void reloadMessages() {
        Consumer<List<Message>> myConsumer = new Consumer<List<Message>>() {
            @Override
            public void accept(List<Message> messages) {
                StringBuilder msg = new StringBuilder();
                if (!messages.isEmpty()) {
                    for (int i = 0; i < messages.size(); i++) {
                        String timeStamp = new SimpleDateFormat("dd.MM.yy  HH.mm").format(messages.get(i).sendTime);

                        msg.append("@" + messages.get(i).transmitterUsername + ":      "+timeStamp +"\n" + messages.get(i).getMsgContent() + "\n\n");
                    }
                    globalIntent.putExtra(MESSAGES_LIST, msg.toString());
                } else {
                    globalIntent.putExtra(MESSAGES_LIST, "Konnte Nachrichten nicht laden");
                }
                sendBroadcast(globalIntent);
            }
        };
        RestController.messages(getApplicationContext(), myConsumer);
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public Intent getGlobalIntent() {
        return globalIntent;
    }

    public void setGlobalIntent(Intent globalIntent) {
        this.globalIntent = globalIntent;
    }

    public Runnable getSendUpdatesToUI() {
        return sendUpdatesToUI;
    }

    public void setSendUpdatesToUI(Runnable sendUpdatesToUI) {
        this.sendUpdatesToUI = sendUpdatesToUI;
    }
}

