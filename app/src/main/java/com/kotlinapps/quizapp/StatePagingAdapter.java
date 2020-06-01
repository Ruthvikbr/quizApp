package com.kotlinapps.quizapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.kotlinapps.quizapp.data.State;

public class StatePagingAdapter extends PagedListAdapter<State,StateViewHolder> {
    public StatePagingAdapter() {
        super(DIFF_CALLBACK);
    }
    private ClickListener clickListener;

    @NonNull
    @Override
    public StateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.list_item,parent,false);

        return new StateViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StateViewHolder holder, final int position) {
        final State currentItem = getItem(position);
        if(currentItem!= null){
            holder.bind(currentItem);

            if(clickListener!=null){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickListener.onItemClick(v,position);
                    }
                });
            }
        }
    }

    public void setItemOnClickListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }

    public interface ClickListener{
        void onItemClick(View view,int position);
    }

    public State getStateAtPosition(int position){
        return getItem(position);
    }


    private static DiffUtil.ItemCallback<State> DIFF_CALLBACK = new DiffUtil.ItemCallback<State>() {
        @Override
        public boolean areItemsTheSame(@NonNull State oldItem, @NonNull State newItem) {
            return (oldItem.getStateName() == newItem.getStateName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull State oldItem, @NonNull State newItem) {
            return oldItem.equals(newItem);
        }
    };
}
