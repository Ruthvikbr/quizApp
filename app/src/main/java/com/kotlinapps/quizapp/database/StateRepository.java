package com.kotlinapps.quizapp.database;

import android.app.Application;

import com.kotlinapps.quizapp.data.State;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StateRepository {

    private static StateRepository REPOSITORY = null;

    private StateDao mStateDao;

    ExecutorService executor = Executors.newSingleThreadExecutor();

    private StateRepository(Application application){
        StateDatabase db = StateDatabase.getDatabase(application);
        mStateDao = db.stateDao();
    }

    public static StateRepository getStateRepository(Application application){
        if (REPOSITORY == null) {
            synchronized (StateRepository.class){
                if (REPOSITORY == null){
                    REPOSITORY = new StateRepository(application);
                }
            }
        }
        return REPOSITORY;
    }

    public void insertState(final State state){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mStateDao.InsertState(state);
            }
        });
    }

    public void deleteState(final State state){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mStateDao.DeleteState(state);
            }
        });
    }

    public void updateState(final State state){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mStateDao.updateState(state);
            }
        });
    }

}
