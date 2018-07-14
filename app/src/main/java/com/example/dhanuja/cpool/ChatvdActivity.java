package com.example.dhanuja.cpool;

import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatvdActivity extends AppCompatActivity {

    ImageButton send;
    private ListView listOfMessage;
    private TextView messageText, messageUser, messageTime,sendTime,CurrentTime,activenumber;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    ArrayList<String> list ;
    ArrayAdapter<String> adapter;
    DatabaseReference dref;
    ChatMessage chatMessage;
    private int CurrentNumber;
    List<ChatMessage> chatMessageList;
    List<ChatMessage> chatMessagesendList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatvd);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        messageText = (TextView) findViewById(R.id.message_text);
        messageUser = (TextView) findViewById(R.id.message_user);
        sendTime = (TextView) findViewById(R.id.message_time);
        listOfMessage = (ListView) findViewById(R.id.list_of_messages);
        send = (ImageButton)findViewById(R.id.sendbtn);
        activenumber = (TextView)findViewById(R.id.activeusers);

        chatMessageList = new ArrayList<>();
        chatMessagesendList = new ArrayList<>();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ChatAdapter chatAdapter = new ChatAdapter(this,R.layout.list_item,chatMessageList);
        final ChatAdapter chatAdaptersend = new ChatAdapter(this,R.layout.list_item_send,chatMessagesendList);

//Active Users display - START
        FirebaseAuth firebaseAuthdv = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabasevd = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReferencevd = firebaseDatabasevd.getReference("ActivevdUsers");

        databaseReferencevd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ActiveUsers activeUsers = dataSnapshot.getValue(ActiveUsers.class);
                CurrentNumber = activeUsers.getNumberactive();
                activenumber.setText("People active in this Chatroom : "+CurrentNumber);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ChatvdActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
//Active Users display - END
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        dref = firebaseDatabase.getReference("ChatvdActivity");
        list = new ArrayList<>();

        adapter = new ArrayAdapter<String>(this,R.layout.list_item,R.id.message_text,list);
        adapter = new ArrayAdapter<String>(this,R.layout.list_item,R.id.message_user,list);
        adapter = new ArrayAdapter<String>(this,R.layout.list_item,R.id.message_time,list);

        listOfMessage.setAdapter(chatAdapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input = (EditText)findViewById(R.id.input);
                FirebaseDatabase.getInstance().getReference("ChatvdActivity").push().setValue(new ChatMessage(input.getText().toString(),
                        FirebaseAuth.getInstance().getCurrentUser().getEmail()));

                input.setText("");
            }
        });


        chatMessage = new ChatMessage();

        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                chatMessageList.clear();
                chatAdapter.notifyDataSetChanged();

                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    chatMessage = ds.getValue(ChatMessage.class);

                    ChatMessage chatmessage= new ChatMessage("\n"+chatMessage.getMessageText().toString()+"\n" , chatMessage.getMessageUser().toString() , chatMessage.getSendTime().toString());
                    chatMessageList.add(chatmessage);

                }

                listOfMessage.setAdapter(chatAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ChatvdActivity.this,databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onStart(){

        FirebaseAuth firebaseAuthvd = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabasevd = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReferencevd = firebaseDatabasevd.getReference("ActivevdUsers");

        databaseReferencevd.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ActiveUsers activeUsers = dataSnapshot.getValue(ActiveUsers.class);
                CurrentNumber = activeUsers.getNumberactive();
                ActiveUsers newdata = new ActiveUsers(CurrentNumber + 1);
                databaseReferencevd.setValue(newdata);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ChatvdActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

        super.onStart();
        Toast.makeText(ChatvdActivity.this,"You have entered the Chatroom", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onStop(){
        FirebaseAuth firebaseAuthvd = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabasevd = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReferencevd = firebaseDatabasevd.getReference("ActivevdUsers");

        databaseReferencevd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ActiveUsers activeUsers = dataSnapshot.getValue(ActiveUsers.class);
                CurrentNumber = activeUsers.getNumberactive();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ChatvdActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

        ActiveUsers newdata = new ActiveUsers(CurrentNumber - 1);
        databaseReferencevd.setValue(newdata);

        Toast.makeText(ChatvdActivity.this,"You have left the Chatroom", Toast.LENGTH_SHORT).show();
        super.onStop();
    }

}
