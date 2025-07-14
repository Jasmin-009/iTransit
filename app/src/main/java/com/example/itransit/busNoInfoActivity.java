package com.example.itransit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;


public class busNoInfoActivity extends AppCompatActivity {

    private static final String EXTRA_MESSEGE = "";
    TextView textViewSource, textViewDestination, textViewChangeBus, textViewPrice;
    private RecyclerView mRecyclerView;
    private BusCardAdapter mAdapter;
    Toolbar toolbar;
    FirebaseFirestore fStore;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<BusCardItem> stopList = new ArrayList<>();
    String s1, s2;
    String[] busData;
    String totalInfo;
    String temp, temp1;
    String[] onlyBus;
    String[] onlyPrice;
    String[] changeStation;
    int n;
    Bundle send =new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_no_info);

        //BIND CONTROLS
        textViewSource = findViewById(R.id.preSource);
        textViewDestination = findViewById(R.id.preDestination);
        textViewChangeBus = findViewById(R.id.textViewChangeBus);
        textViewPrice = findViewById(R.id.textViewPrice);
        mRecyclerView = findViewById(R.id.recyclerViewBusNo);
        toolbar = findViewById(R.id.toolbar);
        fStore = FirebaseFirestore.getInstance();
        //--------------------------------------------------------------
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        s1 = extras.getString("k1");
        s2 = extras.getString("k2");
        textViewSource.setText(s1);
        textViewDestination.setText(s2);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Available Bus");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerViewMargin decoration = new RecyclerViewMargin(30, 1);
        mRecyclerView.addItemDecoration(decoration);

        fetchStopList();
        buildRecyclerView();
    }


    private void fetchStopList() {

        DocumentReference documentReference = fStore.collection("ROUTE_PRICE").document(s1);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        Map<String, Object> map = documentSnapshot.getData();
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if (entry.getKey().equals(s2)) {
                                totalInfo = entry.getValue().toString();
                                temp = totalInfo;
                            }
                        }
                        Log.d("myTag", temp);
                        busData = temp.split(",");
                        n = busData.length;
//                        Log.d("myTag", String.valueOf(n));
                        Log.d("myTag", busData[0]);
                        Log.d("myTag", busData[1]);
                        Log.d("myTag", busData[2]);
//                        Log.d("myTag", "-------------------------------------");
//
//                        Log.d("myTag", busData[0].split("=")[0]);
//                        Log.d("myTag", busData[0].split("=")[1]);

                        onlyBus = new String[n];
                        onlyPrice = new String[n];
                        changeStation = new String[n];
                        Log.d("myTag", String.valueOf(n));
                        for (int i = 0; i < n; i++)
                        {
                            if (!busData[i].contains(">"))
                            {
                                onlyBus[i] = busData[i].split("=")[0].replace("{", "").replace(" ", "").replace("}", "");
                                onlyPrice[i] = busData[i].split("=")[1].replace("}", "").replace(" ", "").replace("{","");
                                changeStation[i] = "No Intermediate Station";
                            }
                            else {
                                onlyBus[i] = busData[i].split("=")[0].replace("{","");
                                onlyPrice[i] = busData[i].split("=")[2].replace("}","");
                                changeStation[i] = busData[i].split("=")[1].replace("{","");
                            }
                            Log.d("myTag", "-------------------------------------");
                            Log.d("myTag", onlyBus[i]);
                            Log.d("myTag", onlyPrice[i]);
                            Log.d("myTag", changeStation[i]);
                            Log.d("myTag", "-------------------------------------");
                        }
//
                        Log.d("myTag", "++++++++++++++++++++++++++++++++++++++++++++++++++");
                        for (int l = 0; l < n; l++) {
                            Log.d("myTag", onlyBus[l]);
                            mAdapter.notifyDataSetChanged();
                            stopList.add(new BusCardItem(R.drawable.ic_bus_blue, onlyBus[l], changeStation[l], onlyPrice[l]));
                        }
                    } else {
                        Toast.makeText(busNoInfoActivity.this, "Check Internet Connection.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("myTag", "Get Failed With :" + task.getException());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(busNoInfoActivity.this, "Failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void buildRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new BusCardAdapter(stopList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new StopCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String str = stopList.get(position).getText();
//                Toast.makeText(busNoInfoActivity.this, "SELECTED:" + str, Toast.LENGTH_SHORT).show();
                Intent intent1=new Intent(busNoInfoActivity.this,bookTicketActivity.class);
                send.putString("key1",s1);
                send.putString("key2",s2);
                send.putString("key3",str);
                send.putString("key4",onlyPrice[position]);
                send.putString("key5",changeStation[position]);
                intent1.putExtras(send);
                startActivity(intent1);
            }
        });
    }
}