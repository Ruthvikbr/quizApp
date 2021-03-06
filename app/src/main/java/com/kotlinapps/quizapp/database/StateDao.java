package com.kotlinapps.quizapp.database;



import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.kotlinapps.quizapp.data.State;

import java.util.List;



@Dao
public interface StateDao {

    @Insert
    void InsertState(State state);

    @Delete
    void DeleteState(State state);

    @Update
    void updateState(State state);

    @Query("SELECT * FROM State")
    DataSource.Factory<Integer,State> getAllStates();

    @Query("SELECT DISTINCT * FROM State ORDER BY RANDOM() LIMIT 4")
    List<State> getQuizStates();

    @Query("SELECT * FROM State ORDER BY RANDOM() LIMIT 1")
    State getRandomState();

    @RawQuery(observedEntities = State.class)
    DataSource.Factory<Integer,State> getSortedStates(SupportSQLiteQuery sqLiteQuery);

    @RawQuery(observedEntities = State.class)
    LiveData<List<State>> getQuizStates(SupportSQLiteQuery sqLiteQuery);

    @Query("SELECT DISTINCT * FROM State ORDER BY RANDOM() LIMIT :Value")
    List<State> getQuizStates(int Value);

}





