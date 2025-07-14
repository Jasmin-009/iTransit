package com.example.itransit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.example.itransit.Search.EXTRA_MESSEGE;

public class bookTicketActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView source,destination,busNo,price,changeAt;
    Button btnBook;
    ImageView qrImage;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fStore;
    String uid;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;
    double k=Math.random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ticket);

        //BIND CONTROL
        toolbar=findViewById(R.id.toolbar1);
        source=findViewById(R.id.sourceName);
        destination=findViewById(R.id.DestinationName);
        busNo=findViewById(R.id.busNo);
        price=findViewById(R.id.price);
        changeAt=findViewById(R.id.changeAt);
        btnBook=findViewById(R.id.bookTicket);
        qrImage=findViewById(R.id.qrCodeImage);
        //Getting Current Date
        calendar=Calendar.getInstance();
        simpleDateFormat=new SimpleDateFormat("MM/dd/yyyy");
        String date=simpleDateFormat.format(calendar.getTime());
        Log.d("myTag",date);

        firebaseAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        uid= Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        final DocumentReference document=fStore.collection("ONLINE_TICKET_PURCHASE").document(date.replace("/","-"));


        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("BOOK TICKET");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent=getIntent();
        Bundle data=intent.getExtras();
        assert data != null;
        source.setText(data.getString("key1"));
        destination.setText(data.getString("key2"));
        busNo.setText(data.getString("key3"));
        price.setText(data.getString("key4"));
        final StringBuilder textToSend=new StringBuilder();

        if(!data.getString("key5").equals("No Intermediate Station")){
            changeAt.setText(data.getString("key5"));
            textToSend.append("User ID: ").append(uid).append("\n")
                    .append("Journey Start at: ").append(source.getText()).append("\n")
                    .append("End at: ").append(destination.getText()).append("\n")
                    .append("Price: ").append(price.getText()).append("\n")
                    .append("Preferred Bus: ").append(busNo.getText()).append("\n")
                    .append("Change Bus at: ").append(changeAt.getText()).append("\n");
        }
        else {
            textToSend.append("User ID: ").append(uid).append("\n")
                    .append("Journey Start at: ").append(source.getText()).append("\n")
                    .append("End at: ").append(destination.getText()).append("\n")
                    .append("Price: ").append(price.getText()).append("\n")
                    .append("Preferred Bus: ").append(busNo.getText()).append("\n");
        }

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MultiFormatWriter multiFormatWriter=new MultiFormatWriter();
                try {
                    BitMatrix bitMatrix=multiFormatWriter.encode(textToSend.toString(), BarcodeFormat.QR_CODE,300,300);
                    Bitmap bitmap=Bitmap.createBitmap(300,300,Bitmap.Config.RGB_565);

                    for(int i=0;i<300;i++){
                        for(int j=0;j<300;j++){
                            bitmap.setPixel(i,j,bitMatrix.get(i,j)? Color.BLACK:Color.WHITE);
                        }
                    }

                    qrImage.setImageBitmap(bitmap);
                    Map<String,Object> detail=new HashMap<>();
                    Map<String,Object> nestedDetail=new HashMap<>();

                    if(changeAt.toString().contentEquals("No Intermediate Station"))
                    {
                        nestedDetail.put("User ID",uid);
                        nestedDetail.put("Source",source.getText());
                        nestedDetail.put("Destination",destination.getText());
                        nestedDetail.put("Price",price.getText());
                        nestedDetail.put("Bus No",busNo.getText());
                        detail.put(Double.toString(k),nestedDetail);
                    }
                    else {
                        nestedDetail.put("User ID",uid);
                        nestedDetail.put("Source",source.getText());
                        nestedDetail.put("Destination",destination.getText());
                        nestedDetail.put("Price",price.getText());
                        nestedDetail.put("Bus No",busNo.getText());
                        nestedDetail.put("Change at",changeAt.getText());
                        detail.put(Double.toString(k),nestedDetail);
//                        detail.put("User ID:",uid);
//                        detail.put("Source",source.getText());
//                        detail.put("Destination",destination.getText());
//                        detail.put("Price",price.getText());
//                        detail.put("Bus No",busNo.getText());
//                        detail.put("Change at",changeAt.getText());
                    }
                    document.set(detail,SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("myTag","On Success: Ticket is saved for" + uid);
                            k=k+1;
                            Log.d("myTag", String.valueOf(k));
//                            startActivity(new Intent(bookTicketActivity.this,MainActivity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("myTag","On Failure: Ticket is failed to save for" + uid);
                        }
                    });

                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
