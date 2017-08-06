package me.karimoff.memochat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by karimoff on 8/3/17.
 */

class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<User> userData;

    private OnChatClickedListener listener;

    // creating custom View Holder for Recycle View
    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.emailTextView) TextView emailTextView;
        @BindView(R.id.uidTextView) TextView uidTextView;
        @BindView(R.id.chatButton) Button chatButton;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public UserAdapter(List<User> userData, OnChatClickedListener listener) {
        this.userData = userData;
        this.listener = listener;
    }

    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list_item,parent,false);

        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final User user = userData.get(position);

        holder.emailTextView.setText(user.getEmail());
        holder.uidTextView.setText(user.getUid());
        holder.chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onChatClicked(user.getUid(), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userData.size();
    }

    public void filter(String text){

    }
}