package component.output;

import model.Classroom;
import model.Student;
import utils.PopupManager;

import java.io.FileWriter;
import java.time.format.DateTimeFormatter;

public class Html implements Generator {
    @Override
    public void Generate( Classroom classroom )
    {
        try
        {
            FileWriter html = new FileWriter(classroom.getName() + ".html");
            html.append("<!DOCTYPE html>\n");
            html.append("   <html lang=\"fr\">\n");
            html.append("   <head>\n");
            html.append("       <meta charset=\"utf-8\"><title> Attendance Report </title>\n");
            html.append("       <link rel=\"stylesheet\" media=\"all\" href=\"visu.css\">\n");
            html.append("   </head>\n");
            html.append("   <body>\n");
            html.append("       <h1> Rapport de connexion </h1>\n\n");
            html.append("       <div id=\"blockid\">");
            html.append("       <table>\n");
            html.append("           <tr>\n");
            html.append("               <th> Date : </th>\n");
            html.append("               <td>" + classroom.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "</td>\n");
            html.append("           </tr>\n");
            html.append("           <tr>\n");
            html.append("               <th> Heure début : </th>\n");
            html.append("               <td> " + classroom.getBegin().toLocalTime() + "</td>\n");
            html.append("           </tr>\n");
            html.append("           <tr>\n");
            html.append("               <th> Heure fin : </th>\n");
            html.append("               <td>" + classroom.getEnd().toLocalTime() +"</td>\n");
            html.append("           </tr>\n");
            html.append("           <tr>\n");
            html.append("               <th> Cours : </th>\n");
            html.append("               <td>" + classroom.getName() + "</td>\n");
            html.append("           </tr>\n");
            html.append("           <tr>\n");
            html.append("               <th> Fichier analysé : </th>\n");
            html.append("               <td>" + classroom.getFilename() + "</td>\n");
            html.append("           </tr>\n");
            html.append("           <tr>\n");
            html.append("               <th> Nombre de connectés : </th>\n");
            html.append("               <td>" + classroom.getStudents().size() + "</td>\n");
            html.append("           </tr>\n");
            html.append("       </table>\n\n");
            html.append("       <h2> Durées de connexion</h2>\n\n");
            html.append("       <p> Pour chaque personne ci-dessous, on retrouve son temps total de connexion sur la plage déclarée du cours, ainsi qu'un graphe qui indique les périodes de connexion (en vert) et d'absence de connexion (en blanc). En pointant la souris sur une zone, une bulle affiche les instants de début et de fin de période.</p>\n");
            html.append("       <div id=\"blockpeople\">\n");

            for (Student student : classroom.getStudents())
            {
                html.append(        "<div class=\"datapeople\">\n");
                html.append(        "<div class=\"name\">" + student.getName() + "</div>\n");
                html.append(        "<div class=\"timebar\">\n");

                for (int i = 0; i < student.getEventList().size(); i = i+2)
                {

                }

                html.append("       </div>\n");
                html.append("       <div class=\"duration\">" + student.getTotalAttendanceDuration() + "</div> \n");
                html.append("       <div class=\"percentd\"> 100% </div> \n");
                html.append("       </div>\n");

            }

            html.append("       </div>\n");
            html.append("   </body> \n");
            html.append("</html>\n");

            html.flush();
            html.close();
        }
        catch (Exception e)
        {
            PopupManager.showAlert("Erreur lors de la création du fichier !");
        }
    }

    /*

============================[ FROM PROCESSOR ]===============================

public String toHTMLCode() {

        String html = "<!DOCTYPE html> \n <html lang=\"fr\"> \n <head> \n <meta charset=\"utf-8\"> ";
        html += "<title> Attendance Report </title> \n <link rel=\"stylesheet\" media=\"all\" href=\"visu.css\"> \n";
        html += "</head> \n <body> \n ";
        html += "<h1> Rapport de connexion </h1>\n" +
                "\n" +
                "<div id=\"blockid\">\n" +
                "<table>\n" +
                "\t<tr>\n" +
                "\t\t<th> Date : </th>\n" +
                "\t\t<td> " + this._allpeople.iterator().next().getDate() + " </td>\n" +
            "\t</tr>\n" +
            "\t<tr>\n" +
            "\t\t<th> Heure début : </th>\n" +
            "\t\t<td> " + this._startTime + " </td>\n" +
            "\t</tr>\n" +
            "\t<tr>\n" +
            "\t\t<th> Heure fin : </th>\n" +
            "\t\t<td> " + this._endTime + " </td>\n" +
            "\t</tr>\n" +
            "\t<tr>\n" +
            "\t\t<th> Cours : </th>\n" +
            "\t\t<td> CM Bases de données et programmation Web </td>\n" +
            "\t</tr>\n" +
            "\t<tr>\n" +
            "\t\t<th> Fichier analysé : </th>\n" +
            "\t\t<td> " + this._fileName + " </td>\n" +
            "\t</tr>\n" +
            "\t<tr>\n" +
            "\t\t<th> Nombre de connectés : </th>\n" +
            "\t\t<td> " + this._allpeople.size() + "  </td>\n" +
            "\t</tr>\n" +
            "</table>\n" +
            "</div>\n" +
            "\n" +
            "<h2> Durées de connexion</h2>\n" +
            "\n" +
            "<p> Pour chaque personne ci-dessous, on retrouve son temps total de connexion sur la plage déclarée du classroom, ainsi qu'un graphe qui indique les périodes de connexion (en vert) et d'absence de connexion (en blanc). En pointant la souris sur une zone, une bulle affiche les instants de début et de fin de période. \n" +
            "</p>";
    html += "<div id=\"blockpeople\"> ";

        for ( Student student : this._allpeople) {

        html += student.getHTMLCode();
    }

    html += "</div> \n </body> \n </html>";
        return html;
}

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
