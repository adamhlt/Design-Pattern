package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;

/**
 * Represents a student.
 *
 * @version 1.0
 */
public class Student {

    /**
     * A string of the student identity from teams ( generaly "firstName lastName" )
     */
    private final String _identity;
    /**
     * Get student unique id
     */
    private final String _id;
    /**
     * Store all connections or disconnections events of the students
     * An event represent switch of state between connected and disconnected
     */
    private final LinkedList<LocalDateTime> _eventList;

    public String getIdentity() {
        return this._identity;
    }

    public String getId() { return _id; }

    public LinkedList<LocalDateTime> getEventList() { return _eventList; }

    public void addEvent( LocalDateTime dateTime ) {
        this._eventList.add( dateTime );
    }

    /**
     * Create student from his id and his identity
     *
     * @param id       Unique id of the student
     * @param identity Identity from teams
     * @see utils.StudentIDServer to generate id from identity
     */
    public Student( String id, String identity ) {
        this._identity = identity;
        this._id = id;
        this._eventList = new LinkedList<>();
    }

    /**
     * Get total time being connected in minute of the student
     *
     * @return An long representing a time in minute.
     */
    public long getTotalAttendanceDuration() {
        double totalDuration = 0;
        LocalDateTime lastEvent = null;
        boolean isConnected = false;

        for( LocalDateTime event : this._eventList ) {
            if( isConnected )
                totalDuration += Duration.between( lastEvent, event ).toMinutes();
            lastEvent = event;
            isConnected = !isConnected;
        }
        return Math.round( totalDuration );
    }

    /**
     * Get a percentage of the time being connected by the student
     *
     * @param classDuration A Duration of the classroom
     * @return An integer representing a percentage
     */
    public int getAttendancePercent( Duration classDuration ) {
        return Math.round( (float) getTotalAttendanceDuration() * 100 / (float) classDuration.toMinutes() );
    }

    /**
     * Check if the last period got an end
     *
     * @return A boolean as true if the last period of the student got an end
     */
    public boolean isClosed() {
        return _eventList.size() % 2 == 0;
    }

    /**
     * Get the first event of the student ( the first connection )
     *
     * @return A localDateTime of the first event
     */
    public LocalDateTime getFirstEvent() {
        return ( _eventList.size() != 0 ) ? _eventList.getFirst() : null;
    }

    /**
     * Get the last event of the student
     * Generaly the last disconnection but can be the last connection if the student
     * doesn't disconnect of the teams
     *
     * @return A localDateTime of the last event
     */
    public LocalDateTime getLastEvent() {
        return ( _eventList.size() != 0 ) ? _eventList.getLast() : null;
    }
}
