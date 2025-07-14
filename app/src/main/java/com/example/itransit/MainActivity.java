package com.example.itransit;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.shadow.ShadowViewDelegate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    FirebaseAuth auth;
    FirebaseFirestore fStore;
    TextView txtName,txtEmail;
    EditText SearchBox;
    String uid;
    View hView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar();
        SearchBox=findViewById(R.id.editTextSearchBox);
        navigationView=findViewById(R.id.navigation_menu);
        auth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        SearchBox.setFocusableInTouchMode(true);
        SearchBox.setInputType(InputType.TYPE_NULL);
//        txtName=findViewById(R.id.nav_textViewName);
//        txtEmail=findViewById(R.id.nav_textViewEmail);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_favourite:
                        Toast.makeText(MainActivity.this, "Favourites", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_notification:
                        Toast.makeText(MainActivity.this, "Notification", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_transactionHistory:
                        Toast.makeText(MainActivity.this, "Transaction History", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_feedback:
                        Toast.makeText(MainActivity.this, "Feedback", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_share:
                        Toast.makeText(MainActivity.this, "Share", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_contactUs:
                        Toast.makeText(MainActivity.this, "Contact US", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_signOut:
                        Toast.makeText(MainActivity.this, "Sign Out", Toast.LENGTH_SHORT).show();
                        auth.signOut();
                        startActivity(new Intent(getApplicationContext(),registerActivity.class));
                        break;
                }
                return false;
            }
        });
        hView=navigationView.getHeaderView(0);
        txtName=hView.findViewById(R.id.nav_textViewName);
        txtEmail=hView.findViewById(R.id.nav_textViewEmail);
//        SETTING NAME AND EMAIL IN NAVIGATION DRAWER
        uid=auth.getCurrentUser().getUid();
        DocumentReference documentReference=fStore.collection("USERS").document(uid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
//                txtName.setText(documentSnapshot.getString("name"));
//                txtEmail.setText(documentSnapshot.getString("email"));
//                Toast.makeText(MainActivity.this, "User:" + documentSnapshot.getString("name"), Toast.LENGTH_SHORT).show();
                txtName.setText(documentSnapshot.getString("name"));
                txtEmail.setText(documentSnapshot.getString("email"));
            }
        });
        UIUtil.hideKeyboard(this);
        SearchBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),BusNumberSearchActivity.class));
            }
        });


    }

    private void setUpToolbar(){
        drawerLayout=findViewById(R.id.drawerLayout);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();;
    }

    public void btnMenu(View view) {
    }

    public void btnEticket(View view) {
        startActivity(new Intent(getApplicationContext(),eTicketStopActivity.class));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
