package com.example.whatsapp_notification_spy;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Message_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context activity;
    private ArrayList<MyMessage> items;

    public Message_Adapter(Activity activity, ArrayList<MyMessage> items) {
        this.activity = activity;
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageViewHolder messageViewHolder = (MessageViewHolder) holder;
        MyMessage message = getItem(position);
        messageViewHolder.contact_name.setText(message.getContact_name());
        messageViewHolder.message.setText((message.getMessage()));
        messageViewHolder.time.setText(message.getTime());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public MyMessage getItem(int position){
        return items.get(position);
    }

    private class MessageViewHolder extends RecyclerView.ViewHolder {

        private TextView contact_name;
        private TextView message;
        private TextView time;


        public MessageViewHolder(View itemView) {
            super(itemView);
            this.contact_name = itemView.findViewById(R.id.contact_name);
            this.message = itemView.findViewById(R.id.message);
            this.time = itemView.findViewById(R.id.time);

        }
    }
}
