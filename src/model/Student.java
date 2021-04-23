package model;

import utils.StudentIDServer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Locale;

public class Student implements Comparable<Student> {

    private final String _name;
    private final String _id;

    private final LinkedList<LocalDateTime> _periodList;

    public Student( String name ) {
        this._name = name;
        this._id = StudentIDServer.getId(this._name);
        this._periodList = new LinkedList<>();
    }

    public String getId() { return _id; }

    public void addPeriod(LocalDateTime dateTime) {
        this._periodList.add(dateTime);
    }

    public long getTotalAttendanceDuration() {
        double totalDuration = 0;
        for ( Period period : this._periodList) {
            totalDuration += period.getDurationInMinutes();
        }
        return Math.round(totalDuration);
    }

    public boolean isClosed() {
        return this._periodList.getLast().isEnded();
    }

    public String getName() {
        return this._name;
    }

    @Override
    public String toString() {
        return "People{" +
                "_name='" + _name + '\'' +
                ", _id='" + _id + '\'' +
                ", _periodList=" + _periodList +
                '}';
    }

    @Override
    public int compareTo( Student o) {
        return (int)(this.getTotalAttendanceDuration()-o.getTotalAttendanceDuration());
    }

    public boolean isOutOfPeriod() {
        return this._periodList.isEmpty();
    }

}
