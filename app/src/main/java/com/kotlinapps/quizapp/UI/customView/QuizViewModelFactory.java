package com.kotlinapps.quizapp.UI.customView;

import android.app.Application;


import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class QuizViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {
    private Application application;

    public QuizViewModelFactory(@NonNull Application application) {
        super(application);
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new quizViewModel(application);
    }
}
