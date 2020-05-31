package com.kotlinapps.quizapp.UI.customView;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.kotlinapps.quizapp.data.State;
import com.kotlinapps.quizapp.database.StateRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class quizViewModel extends AndroidViewModel {
    public MutableLiveData<List<State>> states = new MutableLiveData<>();
    private StateRepository stateRepository;
    public quizViewModel(@NonNull Application application) {
        super(application);
        stateRepository = StateRepository.getStateRepository(application);
    }

    private void loadGame(){
        try{
            states.postValue(stateRepository.getQuizStates().get());

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public  void refreshGame(){
        loadGame();
    }
}
