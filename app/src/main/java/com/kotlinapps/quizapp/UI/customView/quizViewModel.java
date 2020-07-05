package com.kotlinapps.quizapp.UI.customView;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;


import com.kotlinapps.quizapp.data.State;
import com.kotlinapps.quizapp.database.StateRepository;

import java.util.List;

public class quizViewModel extends AndroidViewModel {

    //public MutableLiveData<List<State>> states = new MutableLiveData<>();
    private StateRepository stateRepository;

    public MutableLiveData<Integer> count = new MutableLiveData<>();
    public LiveData<List<State>> state ;



    public quizViewModel(@NonNull Application application) {
        super(application);
        stateRepository = StateRepository.getStateRepository(application);
        count.setValue(4);
        loadGame();
    }

    private void loadGame() {
        Log.v("Load game","Load game called");
        state = Transformations.switchMap(count, new Function<Integer, LiveData<List<State>>>() {
            @Override
            public LiveData<List<State>> apply(Integer input) {
                return stateRepository.getQuizStates(input);
            }
        });
    }

    public void refreshGame() {
        loadGame();
    }
}
