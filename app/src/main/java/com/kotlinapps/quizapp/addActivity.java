package com.kotlinapps.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kotlinapps.quizapp.data.State;

public class addActivity extends AppCompatActivity {
    private StateViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        viewModel = new ViewModelProvider(this).get(StateViewModel.class);


        final EditText stateName = findViewById(R.id.stateET);
        final EditText capitalName = findViewById(R.id.capitalET);
        Button add = findViewById(R.id.addState);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String StateName = stateName.getText().toString();
                String CapitalName = capitalName.getText().toString();
                if(!StateName.isEmpty() && !CapitalName.isEmpty()){
                    State state = new State(StateName,CapitalName);
                    viewModel.insertState(state);
                }
            }
        });
    }
}
