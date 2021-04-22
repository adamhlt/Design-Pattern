package controller;

import java.io.File;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import model.People;

public class TEAMSProcessor {

    private Collection<People> _allpeople = null;
    private final String _fileName;
    private final String _startTime;
    private final String _endTime;
    private LocalTime _firstTime;
    private LocalTime _lastTime;

    public TEAMSProcessor(File _file, String _start, String _stop) {
        /*
         csv file to read
         start time of the course
         end time of the cource
        */
        this._startTime = _start;
        this._endTime = _stop;

        // load CSV file
        this._fileName = _file.getName();
        var teamsFile = new TEAMSAttendanceList(_file);

        // filter to extract data for each people
        var lines = teamsFile.get_attlist();
        if (lines != null) {
            // convert lines in data structure with people & periods
            var filter = new TEAMSAttendanceListAnalyzer(lines);
            // cut periods before start time and after end time
            filter.setStartAndStop(_start, _stop);
            // sort
            List<People> peopleByDuration = new ArrayList<People>(filter.get_peopleList().values());
            Collections.sort(peopleByDuration);
            // init the people collection
            this._allpeople = peopleByDuration;//filter.get_peopleList().values();
            getTime();
        }
    }

    public Collection<People> get_allpeople() {
        return _allpeople;
    }

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
                "\t\t<td> " + /*this._allpeople.iterator().next().getDate() +*/ " </td>\n" +
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
                "<p> Pour chaque personne ci-dessous, on retrouve son temps total de connexion sur la plage déclarée du cours, ainsi qu'un graphe qui indique les périodes de connexion (en vert) et d'absence de connexion (en blanc). En pointant la souris sur une zone, une bulle affiche les instants de début et de fin de période. \n" +
                "</p>";
        html += "<div id=\"blockpeople\"> ";

        for (People people : this._allpeople) {

            html += people.getHTMLCode();
        }

	    html += "</div> \n </body> \n </html>";
        return html;
    }

    private void getTime()
    {
        _firstTime = LocalTime.parse(_allpeople.stream().toList().get(0).get_start().split("à")[1].replaceAll("\\s", ""));
        _lastTime = LocalTime.parse(_allpeople.stream().toList().get(0).get_stop().split("à")[1].replaceAll("\\s", ""));
        System.out.println(_firstTime);
        System.out.println(_lastTime);
        for (People student: _allpeople.stream().toList())
        {
            if (_firstTime.isAfter(LocalTime.parse(student.getDate().split("à")[1])))
            {

            }
        }
    }

}
