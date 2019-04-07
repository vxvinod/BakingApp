package com.example.a60010743.bakingpro.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {RecepieDetails.class}, version = 1)
public abstract class RecepieDetailsDatabase extends RoomDatabase {

    public abstract RecepieDao recepieDao();

    private static volatile RecepieDetailsDatabase INSTANCE;

    static RecepieDetailsDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (RecepieDetailsDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RecepieDetailsDatabase.class, "recepie_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
