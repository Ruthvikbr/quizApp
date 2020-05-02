package com.kotlinapps.quizapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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
    LiveData<List<State>> getAllStates();

}
