package com.example.itransit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import static com.example.itransit.Search.EXTRA_MESSEGE;

public class eTicketStopActivity extends AppCompatActivity {

    private static final String DATA = "";
    private EditText startStopName,endStopName;
    ProgressBar progressBar;
    Button buttonGetRoute;
    FirebaseFirestore fStore;
    Toolbar toolbar;
    String msg,msg1;
    private final static int MY_REQUEST_CODE1 = 1;
    private final static int MY_REQUEST_CODE0 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_ticket_stop);

        //BINDING CONTROL
        startStopName=findViewById(R.id.editTextStartStop);
        endStopName=findViewById(R.id.editTextEndStop);
        startStopName.setText("");
        endStopName.setText("");
        buttonGetRoute=findViewById(R.id.btnGetBusRoute);
        fStore=FirebaseFirestore.getInstance();
        toolbar=findViewById(R.id.toolbar);
        progressBar=findViewById(R.id.progressBarLoading);
        final Bundle extras = new Bundle();

        startStopName.setInputType(InputType.TYPE_NULL);
        endStopName.setInputType(InputType.TYPE_NULL);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Search Stops");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        startStopName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(eTicketStopActivity.this, Search.class);
                startActivityForResult(intent, MY_REQUEST_CODE1);
            }

        });

        endStopName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(eTicketStopActivity.this, Search.class);
                startActivityForResult(intent, MY_REQUEST_CODE0);
            }
        });

        //FIREBASE FUNCTION REMAINING
        buttonGetRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(startStopName.getText())){
                    startStopName.setError("Select Your Starting Point.");
                    startStopName.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(endStopName.getText())){
                    endStopName.setError("Select Your Destination Point.");
                    endStopName.requestFocus();
                    return;
                }
                else {
                    progressBar.setVisibility(View.VISIBLE);
                    Intent intent=new Intent(eTicketStopActivity.this,busNoInfoActivity.class);
                    msg=startStopName.getText().toString();
                    msg1=endStopName.getText().toString();
                    extras.putString("k1",msg);
                    extras.putString("k2",msg1);
                    intent.putExtras(extras);
                    startActivity(intent);
//                    Toast.makeText(eTicketStopActivity.this, "START STOP:" + startStopName.getText()+"\n"
//                            + "END STOP :" + endStopName.getText(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == MY_REQUEST_CODE1) {
                if (data != null)
                startStopName.setText(data.getStringExtra(EXTRA_MESSEGE));
            }
            else {
                if (data != null)
                    endStopName.setText(data.getStringExtra(EXTRA_MESSEGE));
            }
        }
    }
}
