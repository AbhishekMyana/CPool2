package com.example.dhanuja.cpool;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileActivity extends AppCompatActivity {

    private TextView pname,pemail,pphone;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase ;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Profile");
        progressDialog.show();
        pname = (TextView)findViewById(R.id.pnametxt);
        pemail = (TextView)findViewById(R.id.pemailtxt);
        pphone = (TextView)findViewById(R.id.pphonetxt);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference("UserInfo");

        final String message_user = getIntent().getStringExtra("message_user");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds:dataSnapshot.getChildren()) {
                    UserProfile userProfile = ds.getValue(UserProfile.class);
                    if(userProfile.getUsermail().equals(message_user)) {
                        pname.setText("NAME : " + userProfile.getUserName());
                        pemail.setText("EMAIL ID : " + userProfile.getUsermail());
                        pphone.setText("MOBILE NUMBER : " + userProfile.getUserno());
                    }
                }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UserProfileActivity.this,databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
