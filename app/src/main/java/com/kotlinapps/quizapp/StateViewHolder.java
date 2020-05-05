package com.kotlinapps.quizapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kotlinapps.quizapp.data.State;

public class StateViewHolder extends RecyclerView.ViewHolder {

    private TextView stateTextView,capitalTextView;


    public StateViewHolder(@NonNull View itemView) {
        super(itemView);
        stateTextView = itemView.findViewById(R.id.StateTextView);
        capitalTextView = itemView.findViewById(R.id.capitalTextView);
    }

    public void bind(State state){
        stateTextView.setText(state.getStateName());
        capitalTextView.setText(state.getCapitalName());
    }
}
