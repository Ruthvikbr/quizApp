package com.kotlinapps.quizapp.UI.customView;

import android.app.Application;


import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class QuizViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {

    private Application application;
    private int opCount;
    public QuizViewModelFactory(@NonNull Application application,int optionCount) {
        super(application);
        this.application = application;
        this.opCount = optionCount;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new quizViewModel(application,opCount);
    }
}
