package me.karimoff.memochat;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */

//This fragment is for listing all friends to select.
public class ChatListFragment extends Fragment {

    //Binding with ButterKnife
    @BindView(R.id.searchTextView) TextView searchTextText;
    @BindView(R.id.searchEditText) EditText searchEditText;

    @BindView(R.id.friendsRecyclerView) RecyclerView friendsRecyclerView;

    List<User> friendsList = new ArrayList<>(); //this list is to store all users

    //below is for declaring RecyclerView
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    private FirebaseAuth mAuth; //the app uses firebase authentication for registration
    FirebaseUser user;
    private DatabaseReference mDatabase; //DatabaseReferece is for database connection

    public ChatListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDatabase = FirebaseDatabase.getInstance().getReference(); //Firebase database connection
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);
        ButterKnife.bind(this, view);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Query users = mDatabase.child("users").orderByKey(); //querying all user in the database

        //if there is a new user, this listener will automatically inform
        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<User> friends = new ArrayList<>();

                //getting all users
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    User obj = data.getValue(User.class);
                    friends.add(obj);
                }
                friendsList = friends;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        friendsRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        friendsRecyclerView.setLayoutManager(mLayoutManager);

        //this adapter is for creating new chat rooms or accesing to chat rooms when new message is send
        mAdapter = new UserAdapter(friendsList, new OnChatClickedListener() {
            @Override
            public void onChatClicked(String uid, int position) {
                final String room1 = user.getUid() + "_" + friendsList.get(position).getUid();
                final String room2 = friendsList.get(position).getUid() + "_" + user.getUid();
                final DatabaseReference chats = mDatabase.child("chats"); //database connection

                //listener is to read new messages
                chats.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(room1)) {
                            startChatActivity(room1);
                        } else if (dataSnapshot.hasChild(room2)) {
                            startChatActivity(room2);
                        } else {
                            chats.child(room1).setValue(new Chat(new Date(), null));
                            startChatActivity(room1);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        friendsRecyclerView.setAdapter(mAdapter);

    }

    public void startChatActivity(String room) {
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        intent.putExtra("room", room); //sending room id to ChatActivity
        startActivity(intent);
    }
}
