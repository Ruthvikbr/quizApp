package com.kotlinapps.quizapp.database;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.kotlinapps.quizapp.data.State;



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

}
