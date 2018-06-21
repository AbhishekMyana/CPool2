package com.example.dhanuja.cpool;

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.text.format.DateFormat;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class ChatdvActivity extends AppCompatActivity {

    FloatingActionButton fab;
    private FirebaseListAdapter adapter;
    private ListView listOfMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatdv);


        fab = (FloatingActionButton)findViewById(R.id.fabbtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText)findViewById(R.id.input);
                FirebaseDatabase.getInstance().getReference().push().setValue(new ChatMessage(input.getText().toString(),
                        FirebaseAuth.getInstance().getCurrentUser().getEmail()));

                input.setText("");
            }
        });

        displayChatMessage();

    }

    private void displayChatMessage(){

        listOfMessage = (ListView) findViewById(R.id.list_of_messages);

        Query query = FirebaseDatabase.getInstance().getReference().child("chats");

        FirebaseListOptions<ChatMessage> options =
                new FirebaseListOptions.Builder<ChatMessage>()
                        .setQuery(query, ChatMessage.class)
                        .setLayout(android.R.layout.simple_list_item_1)
                        .build();
        adapter = new FirebaseListAdapter<ChatMessage>(options){
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                TextView messageText, messageUser, messageTime;
                messageText = (TextView) findViewById(R.id.message_text);
                messageUser = (TextView) findViewById(R.id.message_user);
                messageTime = (TextView) findViewById(R.id.message_time);

                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH-mm-ss)", model.getMessageTime()));

            }
        };
        listOfMessage.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
        }

}
