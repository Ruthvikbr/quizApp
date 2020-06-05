package com.kotlinapps.quizapp.utils;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.kotlinapps.quizapp.data.State;
import com.kotlinapps.quizapp.database.StateRepository;

public class NotificationWorker extends Worker {
    private StateRepository stateRepository;
    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        stateRepository = StateRepository.getStateRepository((Application) context.getApplicationContext());
    }

    @NonNull
    @Override
    public Result doWork() {
        State state  = stateRepository.getRandomState();
        Notifications.getDailyNotification(getApplicationContext(),state);
        return Result.success();
    }
}
