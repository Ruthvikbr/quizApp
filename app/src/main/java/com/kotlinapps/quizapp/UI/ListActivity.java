package com.kotlinapps.quizapp.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.kotlinapps.quizapp.R;
import com.kotlinapps.quizapp.StatePagingAdapter;
import com.kotlinapps.quizapp.StateViewModel;
import com.kotlinapps.quizapp.data.State;

public class ListActivity extends AppCompatActivity {

    public static final int UPDATE_STATE_REQUEST_CODE = 1;
    public static final int NEW_STATE_REQUEST_CODE = 2;

    public static final String EXTRA_DATA_STATE_NAME = "extra_state_name_to_be_updated";
    public static final String EXTRA_DATA_STATE_CAPITAL = "extra_state_capital_to_be_updated";
    public static final String EXTRA_DATA_ID = "extra_data_id";
    private StateViewModel viewModel;
    private  State DeleteState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        FloatingActionButton add = findViewById(R.id.add);
        ActionBar actionBar = getActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this,addActivity.class);
                startActivityForResult(intent,NEW_STATE_REQUEST_CODE);
            }
        });

         viewModel = new ViewModelProvider(this).get(StateViewModel.class);
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

        ConstraintLayout constraintLayout = findViewById(R.id.listLayout);
        final Snackbar snackbar = Snackbar.make(constraintLayout,"State Deleted", BaseTransientBottomBar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewModel.insertState(DeleteState);
                    }
                });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                DeleteState = statePagingAdapter.getStateAtPosition(position);
                viewModel.deleteState(DeleteState);
                snackbar.show();

            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerView);

        statePagingAdapter.setItemOnClickListener(new StatePagingAdapter.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                State existingState = statePagingAdapter.getStateAtPosition(position);
                launchUpdateStateActivity(existingState);
            }
        });
    }

    private void launchUpdateStateActivity(State state) {
        Intent intent = new Intent(this, addActivity.class);
        intent.putExtra(EXTRA_DATA_STATE_NAME, state.getStateName());
        intent.putExtra(EXTRA_DATA_STATE_CAPITAL, state.getCapitalName());
        intent.putExtra(EXTRA_DATA_ID, state.getStateID());
        startActivityForResult(intent, UPDATE_STATE_REQUEST_CODE);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
