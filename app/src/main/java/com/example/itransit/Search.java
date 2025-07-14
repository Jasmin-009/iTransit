package com.example.itransit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class Search extends AppCompatActivity {

    public static final String EXTRA_MESSEGE = "";
    ImageButton btnBack;
    EditText editTextSearchStop;
    private RecyclerView mRecyclerView;
    private StopCardAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<StopCardItem> stopList = new ArrayList<>();

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        btnBack = findViewById(R.id.imageButtonBack);
        editTextSearchStop = findViewById(R.id.editTextSearchStopName);
        mRecyclerView = findViewById(R.id.recyclerView);


        createStopList();
        buildRecyclerView();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), eTicketStopActivity.class));
            }
        });
    }

    private void createStopList() {
        stopList.add(new StopCardItem(R.drawable.ic_bus_blue, "RAILWAY STATION TERMINAL"));
        stopList.add(new StopCardItem(R.drawable.ic_bus_blue, "DELHI GATE"));
        stopList.add(new StopCardItem(R.drawable.ic_bus_blue, "MOTI TALKIES"));
        stopList.add(new StopCardItem(R.drawable.ic_bus_blue, "MAHIDHARPURA POST OFFICE"));
        stopList.add(new StopCardItem(R.drawable.ic_bus_blue, "BHAGAL"));
        stopList.add(new StopCardItem(R.drawable.ic_bus_blue, "LIMDA CHAWK"));
        stopList.add(new StopCardItem(R.drawable.ic_bus_blue, "BHAGATALAV"));
        stopList.add(new StopCardItem(R.drawable.ic_bus_blue, "CHOWK BAZAR"));
        stopList.add(new StopCardItem(R.drawable.ic_bus_blue, "CHOWK TERMINAL"));
        stopList.add(new StopCardItem(R.drawable.ic_bus_blue, "MAKKAI POOL"));
        stopList.add(new StopCardItem(R.drawable.ic_bus_blue, "DUTCH GARDEN"));
        stopList.add(new StopCardItem(R.drawable.ic_bus_blue, "NANPURA POST OFFICE"));
        stopList.add(new StopCardItem(R.drawable.ic_bus_blue, "BAHUMALI"));
        stopList.add(new StopCardItem(R.drawable.ic_bus_blue, "GIRLS POLYTECHNIC"));
        stopList.add(new StopCardItem(R.drawable.ic_bus_blue, "VANITA VISHRAM"));
        stopList.add(new StopCardItem(R.drawable.ic_bus_blue, "K.P COMMERCE COLLEGE"));
        stopList.add(new StopCardItem(R.drawable.ic_bus_blue, "SEVENTH DAY SCHOOL"));
        stopList.add(new StopCardItem(R.drawable.ic_bus_blue, "ADARSH SOCIETY"));
    }

    private void buildRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new StopCardAdapter(stopList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new StopCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String str=stopList.get(position).getText();
                Intent intent=new Intent(Search.this,eTicketStopActivity.class);
                intent.putExtra(EXTRA_MESSEGE,str);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

}
