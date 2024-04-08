/**
 * Singleton class to provide a database instance for the application. It includes an abstract method to get DAO for
 * Event operations. This class ensures only one instance of the database is created throughout the application to
 * prevent issues with concurrent access.
 */

package com.mp012202200038_01.BasicEventManager;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * The type Event database.
 */
@Database(entities = {Event.class}, version = 1, exportSchema = false)
public abstract class EventDatabase extends RoomDatabase {

    /**
     * Event dao event dao.
     *
     * @return the event dao
     */
    public abstract EventDao eventDao();

    private static volatile EventDatabase INSTANCE;

    /**
     * Gets database.
     *
     * @param context the context
     * @return the database
     */
    static EventDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (EventDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    EventDatabase.class, "event_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
