package com.example.dhanuja.cpool;

import android.app.ProgressDialog;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private Button register;
    private EditText username;
    private EditText mail;
    private EditText phone;
    private EditText newpass;
    private EditText repass;
    private FirebaseAuth firebaseAuth;
    String user,uemail,uno;
    private ProgressDialog progressDialog;

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

        progressDialog = new ProgressDialog(this);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validate()) {
                    progressDialog.setMessage("Loading....");
                    progressDialog.show();
                    user= username.getText().toString().trim();
                    uemail= mail.getText().toString().trim();
                    uno= phone.getText().toString().trim();
                    String upass= newpass.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(uemail, upass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                FirebaseDatabase.getInstance().getReference("UserInfo").push().setValue(new UserProfile(user,uemail,uno));

                                Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                progressDialog.dismiss();
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                username.setText("");
                                mail.setText("");
                                phone.setText("");
                                newpass.setText("");
                                repass.setText("");
                                progressDialog.dismiss();

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

 /*   private void SendUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef= firebaseDatabase.getReference("UserInfo");
        UserProfile userProfile = new UserProfile(user , uemail, uno);
        myRef.push(userProfile);
    }*/
}
