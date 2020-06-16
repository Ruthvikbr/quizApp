package com.kotlinapps.quizapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.PagedList;

import com.kotlinapps.quizapp.data.State;
import com.kotlinapps.quizapp.database.StateRepository;

public class StateViewModel extends AndroidViewModel {

    private StateRepository stateRepository;
    public LiveData<PagedList<State>> pagedListLiveData;

    public MutableLiveData<String> sortOrder = new MutableLiveData<>();

    public StateViewModel(@NonNull Application application) {
        super(application);
        stateRepository = StateRepository.getStateRepository(application);

        sortOrder.setValue("StateID");
        pagedListLiveData = Transformations.switchMap(sortOrder, new Function<String, LiveData<PagedList<State>>>() {
            @Override
            public LiveData<PagedList<State>> apply(String input) {
                return stateRepository.getStatesInSortedOrder(input);
            }
        });
    }

    public void changeSortOrder(String sortBy) {
        switch (sortBy) {
            case "State Name":
                sortBy = "State";
                break;
            case "State Capital":
                sortBy = "Capital";
                break;
            case "State ID":
                sortBy = "StateID";
                break;
        }
        sortOrder.postValue(sortBy);
    }

    public void insertState(State state) {
        stateRepository.insertState(state);
    }

    public void updateState(State state) {
        stateRepository.updateState(state);
    }

    public void deleteState(State state) {
        stateRepository.deleteState(state);
    }
}
