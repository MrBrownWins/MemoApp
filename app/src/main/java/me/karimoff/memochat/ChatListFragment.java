package me.karimoff.memochat;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatListFragment extends Fragment {

    @BindView(R.id.searchTextView) TextView searchTextText;
    @BindView(R.id.searchEditText) EditText searchEditText;

    @BindView(R.id.friendsListView) ListView friendsListView;

    List<User> friendsList = new ArrayList<>();
    UserAdapter friendsListAdapter;

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

    @OnTextChanged(R.id.searchEditText)
    public void afterTextChanged(Editable editable) {

        String filterText = editable.toString();
        if (filterText.length() > 0) {
            friendsListAdapter.getFilter().filter(filterText);
        } else {
            friendsListAdapter.getFilter().filter("");
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Query users = mDatabase.child("users").orderByKey();

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<User> friends = new ArrayList<>();

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

        friendsListAdapter = new UserAdapter(getActivity(), R.layout.user_list_item, friendsList, new OnChatRoomClickListener() {
            @Override
            public void onClicked(String uid, int position) {
                final String room1 = user.getUid() + "_" + friendsList.get(position).getUid();
                final String room2 = friendsList.get(position).getUid() + "_" + user.getUid();
                final DatabaseReference chats = mDatabase.child("chats");

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

        friendsListView.setAdapter(friendsListAdapter);
    }

    public void startChatActivity(String room) {
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        intent.putExtra("room", room);
        startActivity(intent);
    }
}
