/**
 * Data Access Object (DAO) for the events table. Provides methods for performing insert, update, and query operations
 * on the events table within the database. It also includes LiveData support for observing changes to the data.
 */

package com.mp012202200038_01.BasicEventManager;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * The interface Event dao.
 */
@Dao
public interface EventDao {

    /**
     * Insert.
     *
     * @param event the event
     */
    @Insert
    void insert(Event event);

    /**
     * Gets event by id.
     *
     * @param eventId the event id
     * @return the event by id
     */
    @Query("SELECT * FROM event_table WHERE id = :eventId")
    Event getEventById(int eventId);

    /**
     * Update.
     *
     * @param event the event
     */
    @Update
    void update(Event event);

    /**
     * Delete all.
     */
    @Query("DELETE FROM event_table")
    void deleteAll();

    /**
     * Gets all events.
     *
     * @return the all events
     */
    @Query("SELECT * FROM event_table ORDER BY day ASC, month ASC")
    LiveData<List<Event>> getAllEvents();

}
