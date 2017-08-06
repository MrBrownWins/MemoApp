package me.karimoff.memochat;

import android.os.Bundle;
import android.service.autofill.Dataset;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
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


//TextView.OnEditorActionListener is for sending message in ChatActivity, there is no button in the ChatActivity
//messages are controlled with actionSend button in the keyboard

public class ChatActivity extends AppCompatActivity implements TextView.OnEditorActionListener {

    Chat chat;
    @BindView(R.id.edit_text_message) EditText textMessageET; //Binding with Butterknife
    @BindView(R.id.recycler_view_chat) RecyclerView recyclerView;

    Bundle bundle; // this is for getting data from previous activity
    String room; // room has messages

    List<Message> messagesList;

    //below is for declaring RecyclerView
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    DatabaseReference messages; //DatabaseReferece is for database connection
    private FirebaseAuth mAuth; //the app uses firebase authentication for registration
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        bundle = getIntent().getExtras();
        room = bundle.getString("room"); //getting room from ChatListFragment

        textMessageET.setOnEditorActionListener(this);

        mLayoutManager = new LinearLayoutManager(this); //for recyclerView
        recyclerView.setLayoutManager(mLayoutManager); //for recyclerView

        //Firebase database connection
        messages = FirebaseDatabase.getInstance().getReference()
                .child("chats/" + room + "/messages");

        //inform if there changes in the database. if there is new message
        messages.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Message> messages = new ArrayList<Message>();

                //getting all messages
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Message message = data.getValue(Message.class);
                    messages.add(message); //adding message to messages List
                }
                messagesList = messages;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        recyclerView.setAdapter(mAdapter);

    }

    @Override
    //OnEditorAction is for sending message in ChatActivity, there is no button in the ChatActivity
    //messages are controlled with actionSend button in the keyboard
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_SEND) {
            sendMessage(); //sending message
            return true;
        }
        return false;
    }
    //ths function puts new message to firebase
    public void sendMessage(){

        String text = textMessageET.getText().toString();
        Message message = new Message(text, user.getUid() , new Date());

        DatabaseReference newMessage = messages.push();
        newMessage.setValue(message);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
