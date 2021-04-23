package component.output;

import model.Classroom;

public class Html implements Generator {
    @Override
    public void Generate( Classroom classroom ) {

    }

    /*

    ============================[ FROM STUDENT ]===============================

    public String getHTMLCode() {

        if ( this.isOutOfPeriod() ) return ("");

        // duration max, in order to compute images width in percent
        LocalDateTime startTime = DateTimeConverter.StringToLocalDateTime(this._start);
        LocalDateTime endTime = DateTimeConverter.StringToLocalDateTime(this._stop);
        Duration delayMax = Duration.between(startTime, endTime);
        double durationMaxMinutes = Math.abs(delayMax.toSeconds()/60.);

        String html="";
		html += "<div class=\"datapeople\"> \n";
		html += "<div class=\"name\"> " + this.getName() + " </div> \n";
		html +=	"<div class=\"timebar\">";

        double totalDuration = 0;
        LocalDateTime refTime = DateTimeConverter.StringToLocalDateTime(this._start);
        for ( Period period : this._periodList) {

            LocalDateTime begin = period.get_start();
            LocalDateTime end = period.get_end();
            double duration = period.getDurationInMinutes();
            totalDuration += duration;
            // begin > reftime : white bar
            Duration delay = Duration.between(refTime, begin);
            double delayMinutes = Math.abs(delay.toSeconds()/60.);
            if (delayMinutes>0.0) {
                html += "<fxml.img src=\"off.png\" ";
                html += "width=\"" + (100.*delayMinutes/durationMaxMinutes) + "%\" ";
                html += "height=\"20\" title=\"absent(e) de " + refTime.toString();
                html += " à " + begin.toString() + " \"> \n";
            }
            // green bar for the current period
            html += "<fxml.img src=\"on.png\" ";
            html += "width=\"" + (100.*duration/durationMaxMinutes) + "%\" ";
            html += "height=\"20\" title=\"connecté(e) de " + begin.toString();
            html += " à " + end.toString()+ "\"> \n";
            refTime = end;
        }
        // last period aligned on end time ?
        //LocalDateTime endTime = component.teams.TEAMSDateTimeConverter.StringToLocalDateTime(this._stop);
        Duration delay = Duration.between(refTime, endTime);
        double delayMinutes = Math.abs(delay.toSeconds()/60.);
        if (delayMinutes>0.0) {
            html += "<fxml.img src=\"off.png\" ";
            html += "width=\"" + (100.*delayMinutes/durationMaxMinutes) + "%\" ";
            html += "height=\"20\" title=\"absent(e) de " + refTime.toString();
            html += " à " + endTime.toString() + " \"> \n";
        }
		html += "</div> \n"; // end of div timebar
        html +=	"<div class=\"duration\"> " + (long)Math.round(totalDuration) + " </div> \n";
        html +=	"<div class=\"percentd\"> " + (long)Math.round(100.*totalDuration/durationMaxMinutes) + "% </div> \n";
        html += "</div>\n"; // end of div datapeople
        return html;
    }
    */
}
