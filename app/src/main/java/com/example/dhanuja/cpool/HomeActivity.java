package com.example.dhanuja.cpool;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth, firebaseAuthdv;
    private Button chatdv;
    Spinner source;
    Spinner destination;
    String[] sourceloc = {"VJTI", "Dadar", "Wadala", "Matunga"};
    String[] destloc = {"VJTI", "Dadar", "Wadala", "Matunga"};
    String dadar = "Dadar", vjti = "VJTI";
    private FirebaseDatabase firebaseDatabasedv;
    private int CurrentNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        source = (Spinner) findViewById(R.id.source);
        destination = (Spinner) findViewById(R.id.destination);
        chatdv = (Button) findViewById(R.id.chatdvbtn);

        final ArrayAdapter<String> adapters = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, sourceloc);
        adapters.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        source.setAdapter(adapters);

        ArrayAdapter<String> adapterd = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, destloc);
        adapterd.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        destination.setAdapter(adapterd);

/*        //Number of active users for dadar-vjti START
        firebaseAuthdv = FirebaseAuth.getInstance();
        firebaseDatabasedv = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReferencedv = firebaseDatabasedv.getReference("ActivedvUsers");

        databaseReferencedv.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ActiveUsers activeUsers = dataSnapshot.getValue(ActiveUsers.class);
                CurrentNumber = activeUsers.getNumberactive();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HomeActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
*/        //Number of active users for dadar-vjti END

        chatdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String chosens = source.getSelectedItem().toString();
                final String chosend = destination.getSelectedItem().toString();

                if (chosens.equals(sourceloc[1]) && chosend.equals(destloc[0])) {

//                    ActiveUsers newdata = new ActiveUsers(CurrentNumber + 1);
  //                  databaseReferencedv.setValue(newdata);

                    Intent intent = new Intent(HomeActivity.this, ChatdvActivity.class);
                    startActivity(intent);
                }
                else if(chosens.equals(sourceloc[2]) && chosend.equals(destloc[0])){
                    Intent intent = new Intent(HomeActivity.this, ChatwvActivity.class);
                    startActivity(intent);
                }
                else if(chosens.equals(sourceloc[3]) && chosend.equals(destloc[0])){
                    Intent intent = new Intent(HomeActivity.this, ChatmvActivity.class);
                    startActivity(intent);
                }
                else if(chosens.equals(sourceloc[0]) && chosend.equals(destloc[1])){
                    Intent intent = new Intent(HomeActivity.this, ChatvdActivity.class);
                    startActivity(intent);
                }
                else if(chosens.equals(sourceloc[0]) && chosend.equals(destloc[2])){
                    Intent intent = new Intent(HomeActivity.this, ChatvwActivity.class);
                    startActivity(intent);
                }
                else if(chosens.equals(sourceloc[0]) && chosend.equals(destloc[3])){
                    Intent intent = new Intent(HomeActivity.this, ChatvmActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(HomeActivity.this,"The chosen Source-Destination pair is invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.homemenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.profileclk) {
            startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
        }

        if (id == R.id.historyclk) {
            Toast.makeText(this, "History Page is not yet Created", Toast.LENGTH_SHORT).show();
        }

        if (id == R.id.logoutclk) {

            firebaseAuth = FirebaseAuth.getInstance();

            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(HomeActivity.this, ActivityOne.class));

        }
        return super.onOptionsItemSelected(item);
    }

    private void SendUserData() {
        int adduser = 1;
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("ActivedvUsers");
        ActiveUsers activeUsers = new ActiveUsers(adduser);
        myRef.setValue(activeUsers);
    }

/*    private void getCurrentdvusers(){
        firebaseAuthu = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference("ActivedvUsers");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ActiveUsers activeUsers = dataSnapshot.getValue(ActiveUsers.class);
                CurrentNumber = activeUsers.getNumberactive();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HomeActivity.this,databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }
*/

}
