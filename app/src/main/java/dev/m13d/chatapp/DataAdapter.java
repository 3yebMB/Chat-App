package dev.m13d.chatapp;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<ViewHolder> {

    ArrayList<Message> messages;
    LayoutInflater inflater;

    public DataAdapter(Context context, ArrayList<Message> messages) {
        this.messages = messages;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message msg = messages.get(position);
        holder.message.setText(msg.getTextMessage());
        holder.user.setText(msg.getUserName());
        holder.time.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", msg.getMessageTime()));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
