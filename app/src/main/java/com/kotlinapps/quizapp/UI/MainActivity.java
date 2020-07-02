package com.kotlinapps.quizapp.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.kotlinapps.quizapp.R;
import com.kotlinapps.quizapp.UI.customView.QuizViewModelFactory;
import com.kotlinapps.quizapp.UI.customView.quizView;
import com.kotlinapps.quizapp.UI.customView.quizViewModel;
import com.kotlinapps.quizapp.data.State;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private quizView view;
    private quizViewModel viewModel;
    private SharedPreferences optionsPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        optionsPreference = PreferenceManager.getDefaultSharedPreferences(this);
        optionsPreference.registerOnSharedPreferenceChangeListener(listener);

        String optionCount = optionsPreference.getString("options_preference","Four");
        final int value;
        if(optionCount.equals("four")){
            value = 4;
        }
        else{
            value = 3;
        }
        viewModel = new ViewModelProvider(this, new QuizViewModelFactory(this.getApplication(),value)).get(quizViewModel.class);
        view =  findViewById(R.id.quizView);

        viewModel.states.observe(this, new Observer<List<State>>() {
            @Override
            public void onChanged(List<State> states) {
                if(states != null){
                    if (states.size() == 4 || states.size() == 3) {
                        view.setData(states,value);
                    } else {
                        Toast.makeText(MainActivity.this , "Add More states", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        view.setOptionsClickListener(new quizView.optionsClickListener() {
            @Override
            public void OnClicked(Boolean result) {
                updateResult(result,value);
            }
        });
    }
    private void updateResult(Boolean result,int optionCount){
        if(result){
            Toast.makeText(MainActivity.this , "Correct answer", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(MainActivity.this , "Wrong answer", Toast.LENGTH_SHORT).show();
        }
        viewModel.refreshGame(optionCount);
        view.reset();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.list_activity :
                Intent intent = new Intent(MainActivity.this,ListActivity.class);
                startActivity(intent);
                return true;
            case R.id.settings_fragment :
                Intent intent1 = new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(intent1);
                return true;
            default:  return super.onOptionsItemSelected(item);
        }
    }

    private SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if(key.equals("Sort_Preference")){
                String s = optionsPreference.getString(key,"Four");
                int val;
                if(s.equals("four")){
                    val = 4;
                }
                else{
                    val = 3;
                }
                viewModel.refreshGame(val);
                view.reset();
            }
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        optionsPreference.unregisterOnSharedPreferenceChangeListener(listener);
    }
}
