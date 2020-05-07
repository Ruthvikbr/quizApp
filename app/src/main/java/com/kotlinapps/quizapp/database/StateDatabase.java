package com.kotlinapps.quizapp.database;

import android.content.Context;
import android.content.res.AssetManager;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.kotlinapps.quizapp.data.State;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
                    ).addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull final SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            executor.execute(new Runnable() {
                                @Override
                                public void run() {
                                    populateFromDB(context.getAssets(),INSTANCE.stateDao());

                                }
                            });
                        }
                    }).fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }

    private static void populateFromDB(AssetManager assetManager,StateDao stateDao) {
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();
        String json = "";
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(assetManager.open("sate-capital.json")));
            String mLine;
            while ((mLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(mLine);
            }
            json = stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        try{
            JSONObject states = new JSONObject(json);
            JSONObject section = states.getJSONObject("sections");
            populateFromJSON(section.getJSONArray("States (A-L)"), stateDao);
            populateFromJSON(section.getJSONArray("States (M-Z)"), stateDao);
            populateFromJSON(section.getJSONArray("Union Territories"), stateDao);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    private static void populateFromJSON(JSONArray states,StateDao stateDao){
        try{
            for(int i =0;i<states.length();i++){
                JSONObject stateData = states.getJSONObject(i);
                String stateName = stateData.getString("key");
                String stateCapital = stateData.getString("val");
                stateDao.InsertState(new State(stateName,stateCapital));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }



}
