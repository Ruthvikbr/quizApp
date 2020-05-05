package com.kotlinapps.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kotlinapps.quizapp.data.State;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        FloatingActionButton add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this,addActivity.class);
                startActivity(intent);
            }
        });

        StateViewModel viewModel = new ViewModelProvider(this).get(StateViewModel.class);
        RecyclerView recyclerView = findViewById(R.id.statesList);
        final StatePagingAdapter statePagingAdapter = new StatePagingAdapter();
        recyclerView.setAdapter(statePagingAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        viewModel.pagedListLiveData.observe(this, new Observer<PagedList<State>>() {
                    @Override
                    public void onChanged(PagedList<State> states) {
                        statePagingAdapter.submitList(states);
                    }
                }

        );
    }
}
