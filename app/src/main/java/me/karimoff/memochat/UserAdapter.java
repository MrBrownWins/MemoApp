package me.karimoff.memochat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by karimoff on 8/3/17.
 */

class UserAdapter extends ArrayAdapter<User> {
    List<User> items;

    OnChatRoomClickListener listener;

    public UserAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public UserAdapter(Context context, int resource, List<User> items, OnChatRoomClickListener listener) {
        super(context, resource, items);
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.user_list_item, null);
        }

        final User user = getItem(position);

        if (user != null) {
            TextView email = (TextView) v.findViewById(R.id.emailTextView);
            TextView uid = (TextView) v.findViewById(R.id.uidTextView);
            final Button chatButton = (Button) v.findViewById(R.id.chatButton);

            if (email != null) {
                email.setText(user.getEmail());
            }

            if (uid != null) {
                uid.setText(user.getUid());
            }

            chatButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    chatButton.setEnabled(false);
                    listener.onClicked(user.getUid(), position);
                }
            });
            chatButton.setEnabled(true);
        }

        return v;

    }



}