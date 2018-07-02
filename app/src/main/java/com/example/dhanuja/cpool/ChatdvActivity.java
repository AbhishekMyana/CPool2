package com.example.dhanuja.cpool;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.SpannedString;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;
import android.text.format.DateFormat;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.awt.font.TextAttribute;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class ChatdvActivity extends AppCompatActivity {

//    FloatingActionButton fab;
    ImageButton send;
    private ListView listOfMessage;
    private Firebase mRef;
    private TextView messageText, messageUser, messageTime,sendTime,CurrentTime,activenumber;
    private FirebaseListOptions<ChatMessage> options;
    private Query query;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase ,ufirebaseDatabase;
    ArrayList<String> list ;
    ArrayAdapter<String> adapter;
    DatabaseReference dref,uref;
    ChatMessage chatMessage;
    UserProfile userProfile;
    private int CurrentNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatdv);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

//        fab = (FloatingActionButton)findViewById(R.id.fabbtn);
        messageText = (TextView) findViewById(R.id.message_text);
        messageUser = (TextView) findViewById(R.id.message_user);
        sendTime = (TextView) findViewById(R.id.message_time);
        listOfMessage = (ListView) findViewById(R.id.list_of_messages);
        send = (ImageButton)findViewById(R.id.sendbtn);
        activenumber = (TextView)findViewById(R.id.activeusers);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//Active Users display - START
        FirebaseAuth firebaseAuthdv = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabasedv = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReferencedv = firebaseDatabasedv.getReference("ActivedvUsers");

        databaseReferencedv.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ActiveUsers activeUsers = dataSnapshot.getValue(ActiveUsers.class);
                CurrentNumber = activeUsers.getNumberactive();
                activenumber.setText("People active in this Chatroom : "+CurrentNumber);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ChatdvActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
//Active Users display - END
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        ufirebaseDatabase = FirebaseDatabase.getInstance();

        dref = firebaseDatabase.getReference("ChatdvActivity");
        list = new ArrayList<>();

        adapter = new ArrayAdapter<String>(this,R.layout.list_item,R.id.message_text,list);
        adapter = new ArrayAdapter<String>(this,R.layout.list_item,R.id.message_user,list);
        adapter = new ArrayAdapter<String>(this,R.layout.list_item,R.id.message_time,list);

        listOfMessage.setAdapter(adapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input = (EditText)findViewById(R.id.input);
                FirebaseDatabase.getInstance().getReference("ChatdvActivity").push().setValue(new ChatMessage(input.getText().toString(),
                        FirebaseAuth.getInstance().getCurrentUser().getEmail()));

                input.setText("");
            }
        });


/*        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText)findViewById(R.id.input);
                FirebaseDatabase.getInstance().getReference("ChatdvActivity").push().setValue(new ChatMessage(input.getText().toString(),
                        FirebaseAuth.getInstance().getCurrentUser().getEmail()));

                input.setText("");
            }
        });
*/
        chatMessage = new ChatMessage();



        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list.clear();
                adapter.notifyDataSetChanged();

                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    chatMessage = ds.getValue(ChatMessage.class);

                    String user = new String(chatMessage.getMessageUser().toString());
                    String message = new String(chatMessage.getMessageText().toString());
                    String time = new String(chatMessage.getSendTime().toString());

                    //Spanned total=new Spanned("<b>" + user + "</b>\n" + message + "\n" + time);
                    String total1=new String("      " + user + " \n" + message + "\n                    " + time);
                    //String edit = new String(Html.fromHtml(total1,Html.FROM_HTML_MODE_LEGACY));
                    list.add(total1);

                }

                listOfMessage.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ChatdvActivity.this,databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

    }

/*    @Override
    public void onBackPressed(){
        Intent intent = new Intent(ChatdvActivity.this, HomeActivity.class);
        startActivity(intent);

        FirebaseAuth firebaseAuthdv = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabasedv = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReferencedv = firebaseDatabasedv.getReference("ActivedvUsers");

        databaseReferencedv.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ActiveUsers activeUsers = dataSnapshot.getValue(ActiveUsers.class);
                CurrentNumber = activeUsers.getNumberactive();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ChatdvActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

        ActiveUsers newdata = new ActiveUsers(CurrentNumber - 1);
        databaseReferencedv.setValue(newdata);

        Toast.makeText(ChatdvActivity.this,"You have left the Chatroom", Toast.LENGTH_SHORT).show();
    }
*/
    @Override
    public void onStart(){

        FirebaseAuth firebaseAuthdv = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabasedv = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReferencedv = firebaseDatabasedv.getReference("ActivedvUsers");

        databaseReferencedv.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ActiveUsers activeUsers = dataSnapshot.getValue(ActiveUsers.class);
                CurrentNumber = activeUsers.getNumberactive();
                ActiveUsers newdata = new ActiveUsers(CurrentNumber + 1);
                databaseReferencedv.setValue(newdata);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ChatdvActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
//        ActiveUsers newdata = new ActiveUsers(CurrentNumber + 1);
//        databaseReferencedv.setValue(newdata);

        super.onStart();
        Toast.makeText(ChatdvActivity.this,"You have entered the Chatroom", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onStop(){
        FirebaseAuth firebaseAuthdv = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabasedv = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReferencedv = firebaseDatabasedv.getReference("ActivedvUsers");

        databaseReferencedv.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ActiveUsers activeUsers = dataSnapshot.getValue(ActiveUsers.class);
                CurrentNumber = activeUsers.getNumberactive();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ChatdvActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

        ActiveUsers newdata = new ActiveUsers(CurrentNumber - 1);
        databaseReferencedv.setValue(newdata);

        Toast.makeText(ChatdvActivity.this,"You have left the Chatroom", Toast.LENGTH_SHORT).show();
        super.onStop();
    }

}
