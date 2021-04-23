package model;

import component.teams.DateTimeConverter;
import component.teams.StudentIDServer;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Locale;

public class Student implements Comparable<Student> {

    private final String _name;
    private final String _id;

    private final LinkedList<Period> _periodList;

    public Student( String name ) {
        this._name = name;
        this._id = StudentIDServer.getId(this._name);
        this._periodList = new LinkedList<>();
    }

    public String get_id() {
        return _id;
    }

    public void addPeriod(String action, String instant) {

        if ( action.charAt(0) == 'R' ) {
            Period period = new Period(instant);
            this._periodList.add(period);
        } else
            if ( action.charAt(0) == 'A' ) {
                this._periodList.getLast().stopAt(instant);
            } else {
                System.out.println(this._name + " --> erreur : action inconnue ["+action+"] ");
            }
    }

    public void forceEndTimeAt(String instant) {
        // delete all periods started after ending time
        var localPeriodEndingTime = DateTimeConverter.StringToLocalDateTime(instant);
        boolean STOP = false;
        while (!STOP) {
            if (this._periodList.size()>0 && this._periodList.getLast().get_start().isAfter(localPeriodEndingTime))
                this._periodList.removeLast();
            else
                STOP = true;
        }

        // no ending time, so fix it
        if (this._periodList.size()>0 && this._periodList.getLast().get_end()==null)
            this._periodList.getLast().stopAt(instant);
        else
        // ending after the official end of the course, so limit it
        if (this._periodList.size()>0 && this._periodList.getLast().get_end().isAfter(localPeriodEndingTime))
            this._periodList.getLast().stopAt(instant);
    }

    public void forceStartTimeAt(String instant) {
        // delete all periods ended before starting time
        var periods = this._periodList.iterator();
        boolean STOP = false;
        var localPeriodStartingTime = DateTimeConverter.StringToLocalDateTime(instant);
        while ( periods.hasNext() && !STOP) {
            var period = periods.next();
            if (period.get_end().isBefore(localPeriodStartingTime))
                periods.remove();
            else
                STOP = true;
        }
        // adjust starting time of period if before official course starting time
        if (!this._periodList.isEmpty() && this._periodList.getFirst().get_start().isBefore(localPeriodStartingTime))
            this._periodList.getFirst().startAt(instant);

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

    public String getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        return ( this._periodList.getFirst().get_start().format(formatter.withLocale(Locale.FRANCE)) );
    }

    @Override
    public int compareTo( Student o) {
        return (int)(this.getTotalAttendanceDuration()-o.getTotalAttendanceDuration());
    }

    public boolean isOutOfPeriod() {
        return this._periodList.isEmpty();
    }

}
