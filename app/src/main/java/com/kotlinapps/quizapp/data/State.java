package com.kotlinapps.quizapp.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "State")
public class State {

    @PrimaryKey(autoGenerate = true)
    private long StateID;

    @NonNull
    @ColumnInfo(name ="State")
    private String StateName;

    @NonNull
    @ColumnInfo(name ="Capital")
    private String CapitalName;

    public State(@NonNull String stateName, @NonNull  String capitalName) {
        StateName = stateName;
        CapitalName = capitalName;
    }

    @Ignore
    public State(long stateID, @NonNull String stateName,@NonNull String capitalName) {
        StateID = stateID;
        StateName = stateName;
        CapitalName = capitalName;
    }

    public long getStateID() {
        return StateID;
    }

    public void setStateID(long stateID) {
        StateID = stateID;
    }

    public String getStateName() {
        return StateName;
    }

    public void setStateName(String stateName) {
        StateName = stateName;
    }

    public String getCapitalName() {
        return CapitalName;
    }

    public void setCapitalName(String capitalName) {
        CapitalName = capitalName;
    }
    public boolean equals(State s2) {
        return (StateName == s2.getStateName() && CapitalName == s2.getCapitalName());
    }
}
