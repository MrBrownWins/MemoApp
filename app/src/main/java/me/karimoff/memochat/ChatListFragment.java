package me.karimoff.memochat;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatListFragment extends Fragment {

    @BindView(R.id.searchTextView) TextView searchTextText;
    @BindView(R.id.searchEditText) EditText searchEditText;
    @BindView(R.id.friendsListView) ListView friendsListView;

    ArrayList<String> friendsList = new ArrayList<>();
    ArrayAdapter<String> friendsListAdapter;

    private DatabaseReference mDatabase;

    public ChatListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
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
}
