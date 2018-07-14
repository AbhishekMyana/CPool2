package com.example.dhanuja.cpool;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ChatAdapterSender extends ArrayAdapter<ChatMessage>{

    Context chat;
    int resource;
    List<ChatMessage> chatMessagesendList;

    public ChatAdapterSender(Context chat , int resource , List<ChatMessage> chatMessagesendList){
        super(chat ,resource,chatMessagesendList);

        this.chat = chat;
        this.resource = resource;
        this.chatMessagesendList = chatMessagesendList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(chat);

        View view= inflater.inflate(R.layout.list_item_send,null);

        TextView message_text = view.findViewById(R.id.message_text);
        TextView message_user = view.findViewById(R.id.message_user);
        TextView message_time = view.findViewById(R.id.message_time);

        ChatMessage chatMessage= chatMessagesendList.get(position);

        message_text.setText(chatMessage.getMessageText());
        message_user.setText(chatMessage.getMessageUser());
        message_time.setText(chatMessage.getSendTime());

        return view;
    }
}
