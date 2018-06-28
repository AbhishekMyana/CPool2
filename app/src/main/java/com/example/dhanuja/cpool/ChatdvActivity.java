package com.example.dhanuja.cpool;

import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.text.format.DateFormat;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
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


public class ChatdvActivity extends AppCompatActivity {

    FloatingActionButton fab;
    private ListView listOfMessage;
    private Firebase mRef;
    private TextView messageText, messageUser, messageTime,sendTime;
    private FirebaseListOptions<ChatMessage> options;
    private Query query;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase ;
    ArrayList<String> list ;
    ArrayAdapter<String> adapter;
    DatabaseReference dref;
    ChatMessage chatMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatdv);


        fab = (FloatingActionButton)findViewById(R.id.fabbtn);
        messageText = (TextView) findViewById(R.id.message_text);
        messageUser = (TextView) findViewById(R.id.message_user);
        sendTime = (TextView) findViewById(R.id.message_time);
        listOfMessage = (ListView) findViewById(R.id.list_of_messages);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        dref = firebaseDatabase.getReference("ChatdvActivity");
        list = new ArrayList<>();

        adapter = new ArrayAdapter<String>(this,R.layout.list_item,R.id.message_text,list);
        adapter = new ArrayAdapter<String>(this,R.layout.list_item,R.id.message_user,list);
        adapter = new ArrayAdapter<String>(this,R.layout.list_item,R.id.message_time,list);


        listOfMessage.setAdapter(adapter);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText)findViewById(R.id.input);
                FirebaseDatabase.getInstance().getReference("ChatdvActivity").push().setValue(new ChatMessage(input.getText().toString(),
                        FirebaseAuth.getInstance().getCurrentUser().getEmail()));

                input.setText("");
            }
        });

        chatMessage = new ChatMessage();

        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list.clear();
                adapter.notifyDataSetChanged();

                for(DataSnapshot ds:dataSnapshot.getChildren())
                {

                    chatMessage = ds.getValue(ChatMessage.class);
                    list.add(chatMessage.getMessageUser().toString()  + " :                                           \n "+chatMessage.getMessageText().toString()+"                                                        \n                                                            "+chatMessage.getSendTime().toString());

                }

                listOfMessage.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ChatdvActivity.this,databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void clearData(){
        list.clear();
    }


}
