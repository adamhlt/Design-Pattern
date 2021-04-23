package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class Student {

    private final String _name;
    private final String _id;
    private final LinkedList<LocalDateTime> _eventList;

    public String getId() { return _id; }

    public String getName() {
        return this._name;
    }

    public Student( String id , String name ) {
        this._name = name;
        this._id = id;
        this._eventList = new LinkedList<>();
    }

    public void addPeriod(LocalDateTime dateTime) {
        this._eventList.add(dateTime);
    }

    public long getTotalAttendanceDuration() {
        double totalDuration = 0;
        LocalDateTime lastEvent = null;
        boolean isConnected = false;

        for ( LocalDateTime event : this._eventList ) {
            if( isConnected )
                totalDuration += Duration.between(lastEvent,event).toMinutes();
            lastEvent = event;
            isConnected = !isConnected;
        }
        return Math.round(totalDuration);
    }

    public int getAttendancePercent(long classDuration){
       return Math.round(getTotalAttendanceDuration() * 100 / classDuration);
    }

    public boolean isClosed() {
        return _eventList.size()%2==0;
    }

    public LocalDateTime getFirstEvent(){
        return ( _eventList.size() != 0 )?_eventList.getFirst():null;
    }

    public LocalDateTime getLastEvent(){
        return ( _eventList.size() != 0 )?_eventList.getLast():null;
    }

    public LinkedList<LocalDateTime> getEventList() { return _eventList; }
}
