package model;

import utils.StudentIDServer;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class Student implements Comparable<Student> {

    private final String _name;
    private final String _id;

    private final LinkedList<LocalDateTime> _eventList;

    public Student( String name ) {
        this._name = name;
        this._id = StudentIDServer.getId(this._name);
        this._eventList = new LinkedList<>();
    }

    public String getId() { return _id; }

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

    public boolean isClosed() {
        return _eventList.size()%2==0;
    }

    public String getName() {
        return this._name;
    }

    @Override
    public String toString() {
        return "People{" +
                "_name='" + _name + '\'' +
                ", _id='" + _id + '\'' +
                ", _periodList=" + _eventList +
                '}';
    }

    @Override
    public int compareTo( Student o) {
        return (int)(this.getTotalAttendanceDuration()-o.getTotalAttendanceDuration());
    }

    public boolean isOutOfPeriod() {
        return this._eventList.isEmpty();
    }

}
