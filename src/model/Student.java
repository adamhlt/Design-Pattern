package model;

import component.list.ArrayList;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.LocalTime;

/**
 * Represents a student.
 *
 * @version 1.1
 */
public class Student {
    /**
     * A string of the student identity from teams ( generaly "firstName lastName" )
     */
    private final String _identity;

    /**
     * A string of 8 chars representing MD5 hash of the student identity
     */
    private final String _id;

    /**
     * Store all connections or disconnections events of the student
     * An event represent switch of state between connected and disconnected
     */
    private final ArrayList<LocalTime> _eventList;

    /**
     * Create student from identity
     *
     * @param identity Identity from teams
     */
    public Student( String identity ) {
        _identity = identity;
        _id = generateID( identity );
        _eventList = new ArrayList<>();
    }

    /**
     * Get identity
     *
     * @return String of the student identity
     */
    public String getIdentity() {
        return _identity;
    }

    /**
     * Get ID
     *
     * @return String of the student id
     */
    public String getId() { return _id; }

    /**
     * Get the event list of the student
     *
     * @return An arrayList of all connections or disconnections events of the student
     */
    public ArrayList<LocalTime> getEventList() { return _eventList; }

    /**
     * Add a connection or disconnection event of the student
     *
     * @param dateTime Time when the event occur
     */
    public void addEvent( LocalTime dateTime ) { _eventList.add( dateTime ); }

    /**
     * Get total time being connected in minute of the student
     *
     * @return An long representing a time in minute.
     */
    public long getTotalAttendanceDuration() {
        double totalDuration = 0;
        LocalTime lastEvent = null;
        boolean isConnected = false;

        for( LocalTime event : _eventList ) {
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
     * Get the first event of the student ( the first connection )
     *
     * @return A LocalTime of the first event
     */
    public LocalTime getFirstEvent() {
        return ( _eventList.size() != 0 ) ? _eventList.getFirst() : null;
    }

    /**
     * Get the last event of the student, generaly the last disconnection but can be the last connection if the
     * student never leave the teams classroom
     *
     * @return A LocalTime of the last event
     */
    public LocalTime getLastEvent() {
        return ( _eventList.size() != 0 ) ? _eventList.getLast() : null;
    }

    /**
     * Generate a 8 chars length string by hashing the identity string using MD5
     *
     * @param identity String of a student identity
     * @return 8 chars length string representing ID
     */
    private static String generateID( String identity ) {
        String id = "XXXXXXXX";
        try {
            MessageDigest md = MessageDigest.getInstance( "MD5" );
            md.update( identity.getBytes() );
            byte[] digest = md.digest();
            //convertir le tableau de bits en une format hexadécimal
            /*digest.length*/
            id = Integer.toString( ( digest[0] & 0xff ) + 0x100, 16 ).substring( 1 ) +
                    Integer.toString( ( digest[1] & 0xff ) + 0x100, 16 ).substring( 1 ) +
                    Integer.toString( ( digest[2] & 0xff ) + 0x100, 16 ).substring( 1 ) +
                    Integer.toString( ( digest[3] & 0xff ) + 0x100, 16 ).substring( 1 );

        } catch( NoSuchAlgorithmException e ) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * Check if the student was connected at a specific moment in time
     *
     * @param time LocalTime that representing an instant
     * @return true if the student was connected at this moment, false othewise
     */
    public boolean wasHeConnectedAt( LocalTime time ) {
        boolean result = false;
        for( LocalTime event : _eventList ) {
            if( event.isAfter( time ) || event.equals( time ) )
                break;
            else result = !result;
        }
        return result;
    }
}
