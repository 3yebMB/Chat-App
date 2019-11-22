package dev.m13d.chatapp;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.github.library.bubbleview.BubbleTextView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Collections;
import java.util.Objects;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

public class MainActivity extends AppCompatActivity {

    private static int SIGN_IN_CODE = 1;
    private RelativeLayout activity_main;
    private FirebaseListAdapter<Message> adapter;
    private EmojiconEditText emojiconEditText;
    private ImageView emojiButton, sendButton;
    private EmojIconActions emojIconActions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

//        sendButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String mail = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();
//                FirebaseDatabase.getInstance().getReference().push(
//                        new Message(mail, emojiconEditText.getText().toString())
//                );
//                emojiconEditText.setText("");
//            }
//        });

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(Collections.singletonList(
//                                    new AuthUI.IdpConfig.GoogleBuilder().build(),
//                                    new AuthUI.IdpConfig.FacebookBuilder().build(),
                                    new AuthUI.IdpConfig.EmailBuilder().build())).build(),
//                                    new AuthUI.IdpConfig.PhoneBuilder().build(),
//                                    new AuthUI.IdpConfig.AnonymousBuilder().build())).build(),
                                    SIGN_IN_CODE);
        } else {
            Snackbar.make(activity_main, "You're authorized", Snackbar.LENGTH_LONG).show();
            displayAllMessages();
        }
    }

    private void initViews() {
        activity_main = findViewById(R.id.activity_main);
        sendButton = findViewById(R.id.send_button);
        emojiButton = findViewById(R.id.emoji_button);
        emojiconEditText = findViewById(R.id.textField);

        emojIconActions = new EmojIconActions(getApplicationContext(), activity_main, emojiconEditText, emojiButton);
        emojIconActions.ShowEmojIcon();
    }

    private void displayAllMessages() {
        ListView listOfMessages = findViewById(R.id.list_of_messages);

        Query query = FirebaseDatabase.getInstance().getReference().child("chats");
//The error said the constructor expected FirebaseListOptions - here you create them:
        FirebaseListOptions<Message> options = new FirebaseListOptions.Builder<Message>()
                .setQuery(query, Message.class)
                .setLayout(R.layout.list_item)
                .build();
        //Finally you pass them to the constructor here:
        adapter = new FirebaseListAdapter<Message>(options){
            @Override
            protected void populateView(View v, Message model, int position) {
                // Get references to the views of message.xml
                TextView messageText = (TextView)v.findViewById(R.id.message_text);
                TextView messageTime = (TextView)v.findViewById(R.id.message_time);

                // Set their text
                messageText.setText(model.getTextMessage());
                // Format the date before showing it
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getMessageTime()));
            }
        };


//        adapter = new FirebaseListAdapter<Message>() {
//            @Override
//            protected void populateView(@NonNull View v, @NonNull Message model, int position) {
//
//            }
//        };

//        adapter = new FirebaseListAdapter<Message>(this, Message.class, R.layout.list_item, FirebaseDatabase.getInstance().getReference()) {
//            @Override
//            protected void populateView(View v, Message model, int position) {
//                TextView text_user, text_time;
//                BubbleTextView text_text;
//
//                text_user = v.findViewById(R.id.message_user);
//                text_time = v.findViewById(R.id.message_time);
//                text_text = v.findViewById(R.id.message_text);
//
//                text_user.setText(model.getUserName());
//                text_text.setText(model.getTextMessage());
//                text_time.setText(DateFormat.format("dd-mm-yyyy HH:MM:SS", model.getMessageTime()));
//            }
//        };

        listOfMessages.setAdapter(adapter);
    }
}
