package com.kotlinapps.quizapp.database;

import android.app.Application;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.kotlinapps.quizapp.data.State;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class StateRepository {

    private static StateRepository REPOSITORY = null;
    private StateDao mStateDao;
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    private StateRepository(Application application) {
        StateDatabase db = StateDatabase.getDatabase(application);
        mStateDao = db.stateDao();
    }

    public static StateRepository getStateRepository(Application application) {
        if (REPOSITORY == null) {
            synchronized (StateRepository.class) {
                if (REPOSITORY == null) {
                    REPOSITORY = new StateRepository(application);
                }
            }
        }
        return REPOSITORY;
    }

    public void insertState(final State state) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mStateDao.InsertState(state);
            }
        });
    }

    public void deleteState(final State state) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mStateDao.DeleteState(state);
            }
        });
    }

    public void updateState(final State state) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mStateDao.updateState(state);
            }
        });
    }

//    public LiveData<PagedList<State>> getAllStates() {
//
//        return new LivePagedListBuilder<>(
//                mStateDao.getAllStates(),
//                PAGE_SIZE
//        ).build();
//    }

    public Future<List<State>> getQuizStates() {
        Callable<List<State>> callable = new Callable<List<State>>() {
            @Override
            public List<State> call() throws Exception {
                return mStateDao.getQuizStates();
            }
        };
        return executor.submit(callable);
    }

    @WorkerThread
    public State getRandomState() {
        return mStateDao.getRandomState();
    }


    public LiveData<PagedList<State>> getStatesInSortedOrder(String sortOrder){
        int PAGE_SIZE = 15;
        return new LivePagedListBuilder<>(
                mStateDao.getSortedStates(constructQuery(sortOrder)),
                PAGE_SIZE
        ).build();
    }

    public SupportSQLiteQuery constructQuery(String sortBy){
        String query = "SELECT * FROM State ORDER BY "+sortBy+" ASC";
        return new SimpleSQLiteQuery(query);
    }

    public Future<List<State>> getQuizStates(final int Value) {
        Callable<List<State>> callable = new Callable<List<State>>() {
            @Override
            public List<State> call() throws Exception {
                return mStateDao.getQuizStates(Value);
            }
        };
        return executor.submit(callable);
    }


}
