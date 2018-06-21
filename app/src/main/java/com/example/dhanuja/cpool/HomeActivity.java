package com.example.dhanuja.cpool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button chatdv;

    Spinner source;
    Spinner destination;
    String [] sourceloc = {"VJTI","Dadar","Wadala","Matunga"};
    String [] destloc = {"VJTI","Dadar","Wadala","Matunga"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        source = (Spinner)findViewById(R.id.source);
        destination = (Spinner)findViewById(R.id.destination);
        chatdv = (Button)findViewById(R.id.chatdvbtn);

        ArrayAdapter<String> adapters = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item , sourceloc);
        source.setAdapter(adapters);

        ArrayAdapter<String> adapterd = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item , destloc);
        destination.setAdapter(adapterd);

        chatdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ChatdvActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.homemenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id==R.id.profileclk){
            startActivity(new Intent(HomeActivity.this , ProfileActivity.class));
        }

        if(id==R.id.historyclk){
            Toast.makeText(this,"History Page is not yet Created",Toast.LENGTH_SHORT).show();
        }

        if(id==R.id.logoutclk){

            firebaseAuth = FirebaseAuth.getInstance();

            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(HomeActivity.this , ActivityOne.class));

        }
        return super.onOptionsItemSelected(item);
    }

}
