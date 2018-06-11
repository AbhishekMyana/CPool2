package com.example.dhanuja.cpool;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private Button register;
    private EditText username;
    private EditText mail;
    private EditText phone;
    private EditText newpass;
    private EditText repass;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register = findViewById(R.id.registerbtn);
        username = findViewById(R.id.nametxt);
        mail = findViewById(R.id.mailtxt);
        phone = findViewById(R.id.notxt);
        newpass = findViewById(R.id.newpasstxt);
        repass = findViewById(R.id.repasstxt);

        firebaseAuth = FirebaseAuth.getInstance();


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()) {
                    String uemail= mail.getText().toString().trim();
                    String upass= newpass.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(uemail, upass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this , ActivityTwo.class));
                            }
                            else
                            {
                                Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });


                }
            }
        });
    }

    private Boolean validate (){
        Boolean result = false;

        String name = username.getText().toString();
        String email = mail.getText().toString();
        String number = phone.getText().toString();
        String password = newpass.getText().toString();
        String repassword = repass.getText().toString();

        if(name.isEmpty() || email.isEmpty() || number.isEmpty() || password.isEmpty() || repassword.isEmpty() ){
            Toast.makeText(this, "Please Enter all the Details" , Toast.LENGTH_SHORT).show();
        }
        else if(!password.equals(repassword)){
            Toast.makeText(this, "Your password does not match, Try again!" , Toast.LENGTH_SHORT).show();
        }
        else {
            result = true;
        }
        return result;

    }
}
