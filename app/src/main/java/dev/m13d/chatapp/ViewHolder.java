package dev.m13d.chatapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {

    TextView message, user, time;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        message = itemView.findViewById(R.id.message_text);
        user = itemView.findViewById(R.id.message_user);
        time = itemView.findViewById(R.id.message_time);
    }
}
