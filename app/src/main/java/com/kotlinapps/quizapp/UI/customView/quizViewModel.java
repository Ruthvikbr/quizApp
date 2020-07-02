package com.kotlinapps.quizapp.UI.customView;

import android.app.Application;

import androidx.annotation.NonNull;

import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;


import com.kotlinapps.quizapp.data.State;
import com.kotlinapps.quizapp.database.StateRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class quizViewModel extends AndroidViewModel {

    public MutableLiveData<List<State>> states = new MutableLiveData<>();
    private StateRepository stateRepository;



    public quizViewModel(@NonNull Application application, int optionCount) {
        super(application);
        stateRepository = StateRepository.getStateRepository(application);
        loadGame(optionCount);


    }

    private void loadGame(int optionCount) {
        try {
            states.postValue(stateRepository.getQuizStates(optionCount).get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void refreshGame(int optionCount) {
        loadGame(optionCount);
    }
}
