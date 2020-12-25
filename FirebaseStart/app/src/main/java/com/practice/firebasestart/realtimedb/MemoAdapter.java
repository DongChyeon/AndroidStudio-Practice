package com.practice.firebasestart.realtimedb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.practice.firebasestart.R;

import java.util.ArrayList;

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.ViewHolder> {
    private Context context = null;
    private ArrayList<MemoItem> memoItems = null;
    private MemoViewListener memoViewListener = null;

    public MemoAdapter(ArrayList<MemoItem> items, Context context, MemoViewListener listener) {
        this.memoItems = items;
        this.context = context;
        this.memoViewListener =  listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.memo_item_list, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.titleView.setText(memoItems.get(i).getMemotitle());
        viewHolder.contentView.setText(memoItems.get(i).getMemocontents());
    }

    @Override
    public int getItemCount() {
        return memoItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView titleView = null;
        public TextView contentView = null;

        public ViewHolder(View view) {
            super(view);
            titleView = view.findViewById(R.id.textView);
            contentView = view.findViewById(R.id.textView2);
        }

        @Override
        public void onClick(View view) {
            memoViewListener.onItemClick(getAdapterPosition(), view);
        }
    }
}
