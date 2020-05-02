package com.kotlinapps.quizapp.database;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.kotlinapps.quizapp.data.State;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {State.class},version = 1,exportSchema = false)
public abstract class StateDatabase  extends RoomDatabase {

     abstract StateDao stateDao();

    private static StateDatabase INSTANCE = null;

    private static  ExecutorService executor = Executors.newSingleThreadExecutor();

    public static StateDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (StateDatabase.class){
                if(INSTANCE == null){
                    INSTANCE= Room.databaseBuilder(
                           context.getApplicationContext()
                           ,StateDatabase.class
                           ,"State"
                    ).fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }

}
