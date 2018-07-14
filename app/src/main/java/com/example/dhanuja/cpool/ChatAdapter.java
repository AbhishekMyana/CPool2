package com.example.dhanuja.cpool;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
        TextView message_user = view.findViewById(R.id.message_user);
        TextView message_time = view.findViewById(R.id.message_time);

        ChatMessage chatMessage= chatMessageList.get(position);

        message_text.setText(chatMessage.getMessageText());
        message_time.setText(chatMessage.getSendTime());
        message_user.setText(chatMessage.getMessageUser());

        return view;
    }
}
