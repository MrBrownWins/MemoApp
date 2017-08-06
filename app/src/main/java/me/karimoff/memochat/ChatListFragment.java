package me.karimoff.memochat;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatListFragment extends Fragment {

    @BindView(R.id.searchTextView) TextView searchTextText;
    @BindView(R.id.searchEditText) EditText searchEditText;
    @BindView(R.id.friendsListView) ListView friendsListView;

    ArrayList<String> friendsList = new ArrayList<>();
    ArrayAdapter<String> friendsListAdapter;

    private FirebaseAuth mAuth;
    FirebaseUser user;
    private DatabaseReference mDatabase;

    public ChatListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
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

        Query users = mDatabase.child("users").orderByKey();

        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> friends = new ArrayList<>();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    friends.add(data.getKey());
                }
                friendsList = friends;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        friendsListAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, friendsList);

        friendsListView.setAdapter(friendsListAdapter);

    }

    @OnItemClick(R.id.friendsListView)
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        final String room1 = user.getUid() + "_" + friendsList.get(i);
        final String room2 = friendsList.get(i) + "_" + user.getUid();
        final DatabaseReference chats = mDatabase.child("chats");

        chats.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(room1)){
                    startChatActivity(room1);
                } else if(dataSnapshot.hasChild(room2)){
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
    public void startChatActivity(String room){
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        intent.putExtra("room", room);
        startActivity(intent);
    }
}
