package com.example.dhanuja.cpool;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ActivityTwo extends AppCompatActivity {

    private EditText username;
    private EditText pass;
    private Button enter;
    private int counter=5;
    private TextView info;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        username = (EditText)findViewById(R.id.usernametxt);
        pass = (EditText)findViewById(R.id.passwordtxt);
        enter = (Button) findViewById(R.id.enterbtn);
        username = (EditText)findViewById(R.id.usernametxt);
        info = (TextView)findViewById(R.id.infotxt);

        info.setText("Number of attempts remaining: 5");

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user != null){
            finish();
            startActivity(new Intent(ActivityTwo.this, HomeActivity.class));
        }

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(username.getText().toString() , pass.getText().toString());
            }
        });

    }

    private void validate(String user, String passver){

        if(user.isEmpty() || passver.isEmpty() ){
            Toast.makeText(this, "Please Enter all the Details" , Toast.LENGTH_SHORT).show();
        }
        else {

            progressDialog.setMessage("Please Wait");
            progressDialog.show();

            firebaseAuth.signInWithEmailAndPassword(user, passver).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(ActivityTwo.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ActivityTwo.this, HomeActivity.class));
                    } else {
                        Toast.makeText(ActivityTwo.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        counter--;
                        info.setText("Number of attempts remaining:" + counter);
                        progressDialog.dismiss();
                        if (counter == 0) {
                            startActivity(new Intent(ActivityTwo.this, ActivityOne.class));
                        }
                    }

                }
            });
        }

    }
}
