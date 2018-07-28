package com.example.dhanuja.cpool;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class ChatAdapter extends ArrayAdapter <ChatMessage> {

    Context chat;
    int resource;
    List<ChatMessage> chatMessageList;

    public ChatAdapter(Context chat , int resource , List<ChatMessage> chatMessageList){
        super(chat ,resource,chatMessageList);

        this.chat = chat;
        this.resource = resource;
        this.chatMessageList = chatMessageList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(chat);

        View view= inflater.inflate(R.layout.list_item,null);

        TextView message_text = view.findViewById(R.id.message_text);
        final TextView message_user = view.findViewById(R.id.message_user);
        TextView message_time = view.findViewById(R.id.message_time);

        final ChatMessage chatMessage= chatMessageList.get(position);

        message_text.setText(chatMessage.getMessageText());
        message_time.setText(chatMessage.getSendTime());
        message_user.setText(chatMessage.getMessageUser());
/*
        if(chatMessage.getMessageUser().toString()==FirebaseAuth.getInstance().getCurrentUser().getEmail()){
            message_user.setBackgroundResource(R.drawable.button_shape);
            message_user.setTextColor(Color.WHITE);
            message_user.setText("You");
        }
        else if(chatMessage.getMessageUser().toString()!=FirebaseAuth.getInstance().getCurrentUser().getEmail()){
            message_user.setText(chatMessage.getMessageUser());
        }
*/
        message_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(chat, "History Page is not yet Created", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(chat,UserProfileActivity.class).putExtra("message_user",chatMessage.getMessageUser());
                chat.startActivity(intent);
            }
        });

        return view;
    }

}
