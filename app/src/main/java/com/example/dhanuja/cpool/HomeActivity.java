package com.example.dhanuja.cpool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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
            Toast.makeText(this,"Profile Page is not yet Created",Toast.LENGTH_SHORT).show();
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
