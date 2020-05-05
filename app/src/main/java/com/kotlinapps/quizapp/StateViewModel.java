package com.kotlinapps.quizapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.kotlinapps.quizapp.data.State;
import com.kotlinapps.quizapp.database.StateRepository;

public class StateViewModel extends AndroidViewModel {

    private StateRepository stateRepository;
    public LiveData<PagedList<State>> pagedListLiveData;

    public StateViewModel(@NonNull Application application) {
        super(application);
        stateRepository = StateRepository.getStateRepository(application);
        pagedListLiveData = stateRepository.getAllStates();
    }
    public void insertState(State state){
        stateRepository.insertState(state);
    }

    public void updateState(State state){
        stateRepository.updateState(state);
    }

    public void deleteState(State state){
        stateRepository.deleteState(state);
    }
}
