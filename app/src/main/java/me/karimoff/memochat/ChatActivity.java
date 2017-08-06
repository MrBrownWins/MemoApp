package me.karimoff.memochat;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.Dataset;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatActivity extends AppCompatActivity implements TextView.OnEditorActionListener {

    Chat chat;
    @BindView(R.id.edit_text_message) EditText textMessageET;
    @BindView(R.id.recycler_view_chat) RecyclerView recyclerView;

    Intent intent;
    Bundle bundle;
    String room;

    List<Message> messagesList;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    DatabaseReference messages;
    private FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        bundle = getIntent().getExtras();
        room = bundle.getString("room");

        textMessageET.setOnEditorActionListener(this);

        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        messages = FirebaseDatabase.getInstance().getReference()
                .child("chats/" + room + "/messages");

        messages.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Message> messages = new ArrayList<Message>();

                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Message message = data.getValue(Message.class);
                    messages.add(message);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_SEND) {
            sendMessage();
            return true;
        }
        return false;
    }
    public void sendMessage(){

        String text = textMessageET.getText().toString();
        Message message = new Message(text, user.getUid() , new Date());

        DatabaseReference newMessage = messages.child(room).push();
        newMessage.setValue(message);
    }
}
