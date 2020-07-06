package com.kotlinapps.quizapp.UI.customView;

import android.app.Application;
import android.util.Log;
import android.util.Pair;

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
    public MutableLiveData<Integer> increment = new MutableLiveData<>();
    public LiveData<List<State>> state ;
    int i =0;
    CustomLiveData trigger ;



    public quizViewModel(@NonNull Application application) {
        super(application);
        stateRepository = StateRepository.getStateRepository(application);
        count.setValue(4);
        increment.setValue(i);
        trigger = new CustomLiveData(count,increment);
        loadGame();
    }

    private void loadGame() {
        state = Transformations.switchMap(trigger, new Function<Pair<Integer, Integer>, LiveData<List<State>>>() {
            @Override
            public LiveData<List<State>> apply(Pair<Integer, Integer> input) {
                return stateRepository.getQuizStates(input.first);

            }
        });

    }

    public void refreshGame() {
        i++;
        increment.postValue(i);
        loadGame();
    }
}
