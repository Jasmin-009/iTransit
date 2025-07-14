package com.example.itransit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class registerActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    private EditText mUserName,mEmail,mPassword,mPhoneNumber,mConfirmPassword;
    Button btnSignUp,btnSignIn;
    FirebaseAuth auth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    private String uid;

    private String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            +"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//        Objects.requireNonNull(getSupportActionBar()).setTitle("SIgnUp Form");

        //BINDING CONTROLS
        mUserName=findViewById(R.id.editTextName);
        mEmail=findViewById(R.id.editTextEmail);
        mPassword=findViewById(R.id.editTextPassword);
        mConfirmPassword=findViewById(R.id.editTextConfirmPassword);
        btnSignUp=findViewById(R.id.buttonSignUp);
        btnSignIn=findViewById(R.id.buttonSignIn);
        mPhoneNumber=findViewById(R.id.editTextPhoneNumber);

        auth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        progressBar=findViewById(R.id.progressBar);

        //If USER IS ALREADY LOGGED IN

        if(auth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }


        //WHEN USER CLICK ON SIGNUP BUTTON
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //GETTING ALL INFORMATION FROM USER INTO STRING
                final String userName=mUserName.getText().toString();
                final String email=mEmail.getText().toString().trim();
                final String phone=mPhoneNumber.getText().toString();
                String password=mPassword.getText().toString();
                String cPassword=mConfirmPassword.getText().toString();
                if (TextUtils.isEmpty(userName)){
                    mUserName.setError("User Name is required.");
                    return;
                }
                if (TextUtils.isEmpty(email)){
                    mEmail.setError("Email ID is required.");
                    return;
                }
                if (!email.matches(emailPattern)){
                    mEmail.setError("Please Enter Valid Email ID.");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    mPassword.setError("Password is required.");
                    return;
                }
                if (password.length()<6){
                    mPassword.setError("Password should be more than 6 letters.");
                    return;
                }
                if (TextUtils.isEmpty(cPassword)){
                    mConfirmPassword.setError("Enter the password again.");
                    return;
                }
                if (!cPassword.equals(password)){
                    mConfirmPassword.setError("Password doesn't match.");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                //REGISTER USER TO FIREBASE
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(registerActivity.this, "User Created", Toast.LENGTH_SHORT).show();

                            //STORING IN FIRESTORE
                            uid=auth.getCurrentUser().getUid();
                            DocumentReference documentReference=fStore.collection("USERS").document(uid);
                            Map<String,Object> user=new HashMap<>();
                            user.put("name",userName);
                            user.put("email",email);
                            user.put("phone",phone);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG,"onSuccess : user Profile is created for" + uid);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG,"onFailure : User profile is failed for" +uid);

                                }
                            });
                            //NAVIGATE TO MAIN ACTIVITY
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else {
                            Toast.makeText(registerActivity.this, "Error !!!"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }


    public void btn_sign_in(View view) {
        startActivity(new Intent(getApplicationContext(),loginActivity.class));

    }
}
