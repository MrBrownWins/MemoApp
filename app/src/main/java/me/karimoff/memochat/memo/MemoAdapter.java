package me.karimoff.memochat.memo;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.karimoff.memochat.R;

/**
 * Created by karimoff on 8/3/17.
 */

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.ViewHolder>{

    private List<Memo> memoData;

    // creating custom View Holder for Recycle View
    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView) ImageView imageView;
        @BindView(R.id.titleTextView) TextView titleTextView;
        @BindView(R.id.dateTextView) TextView dateTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public MemoAdapter(List<Memo> memoData) {
        this.memoData = memoData;
    }

    // create new View Holder object by inflating custom layout
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.memo_list_item,parent,false);

        return new ViewHolder(view);
    }

    //
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.imageView.setImageDrawable(memoData.get(position).getImage());
        holder.titleTextView.setText(memoData.get(position).getTitle());
        holder.dateTextView.setText(memoData.get(position).getCreated().toString());
    }

    @Override
    public int getItemCount() {
        return memoData.size();
    }

    public void addItem(Drawable image, String title, Date created) {
        Memo item = new Memo(image, title, created);

        memoData.add(item);

    }
}
