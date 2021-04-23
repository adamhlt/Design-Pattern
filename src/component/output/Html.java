package component.output;

import model.Classroom;
import model.Student;
import utils.PopupManager;

import java.io.FileWriter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Html implements Generator {

    private static final String HTML_DOC =
            """
                    <!DOCTYPE html>
                    <html lang="fr">
                    <head>
                        <meta charset="utf-8">
                        <title> Attendance Report </title>
                        <link rel="stylesheet" media="all" href="visu.css">
                    </head>
                    <style>
                        body{
                            background-color: #ddd;
                            font-family: Verdana, sans-serif;
                        }
                        #people {
                            width: 1230px;
                            background-color: #fff;
                            margin-bottom: 30px;
                            padding: 10px 0;
                        }
                        th {
                            text-align: left;
                            padding-right: 10px;
                        }
                        p {
                            width:1230px;
                        }
                        .data {
                            display: grid;
                            grid-column-gap: 5px;
                            grid-template-areas: "col0 col1 col2 col3 col4";
                            height:20px;
                            margin:0 10px;
                            border: solid 1px #fff;
                        }
                        .data:hover {
                            border: solid 1px red;
                        }
                        .id {
                            grid-area: col0;
                            width : 100px;
                            white-space: nowrap;
                            padding-left: 3px;
                        }
                        .name {
                            grid-area: col1;
                            width : 200px;
                            text-overflow: ellipsis;
                            white-space: nowrap;
                            padding-left: 3px;
                        }
                        .duration {
                            grid-area: col3;
                            text-align: center;
                            width: 40px;
                            background-color: #ddd;
                        }
                        .percent {
                            grid-area: col4;
                            text-align: center;
                            width: 60px;
                            background-color: #ddd;
                        }
                        .timeBar {
                            grid-area: col2;
                            width: 780px;
                            background-color: #DDD;
                            border-left: solid 2px black;
                            border-right: solid 2px black;
                        }
                        .timeBar > div {
                            padding: 0;
                            margin: 0;
                            height: 20px;
                            float:left;
                        }
                        .name, .duration, .percent, .id {
                            vertical-align: middle;
                            line-height: 20px;
                        }
                        .green{ background-color: #00FF4C; }
                        .white{ background-color:white; }
                    </style>
                    <body>
                        <h1> Rapport de connexion </h1>
                        <div>
                            <table>
                                <tr>
                                    <th> Date : </th>
                                    <td> %s </td>
                                </tr>
                                <tr>
                                    <th> Heure début : </th>
                                    <td> %s </td>
                                </tr>
                                <tr>
                                    <th> Heure fin : </th>
                                    <td> %s </td>
                                </tr>
                                <tr>
                                    <th> Cours : </th>
                                    <td> %s </td>
                                </tr>
                                <tr>
                                    <th> Fichier analysé : </th>
                                    <td> %s </td>
                                </tr>
                                <tr>
                                    <th> Nombre de connectés : </th>
                                    <td> %s  </td>
                                </tr>
                            </table>
                        </div>
                        <h2> Durées de connexion</h2>
                        <p>
                            Pour chaque personne ci-dessous, on retrouve son temps total de connexion sur la plage déclarée du cours, ainsi
                            qu'un graphe qui indique les périodes de connexion (en vert) et d'absence de connexion (en blanc). En pointant
                            la souris sur une zone, une bulle affiche les instants de début et de fin de période.
                        </p>
                        <div id="people">
                            %s
                        </div>
                    </body>
                    </html>
                    """;

    private static final String HTML_DATA_BLOCK =
            """
                    <div class="data">
                        <div class="id"> %s </div>
                        <div class="name"> %s </div>
                        <div class="timeBar">
                            %s
                        </div>
                        <div class="duration"> %s </div>
                        <div class="percent"> %s </div>
                    </div>
                    """;

    private static final String TIME_BAR_ITEM =
            """
                    <div class="%s" style="width:%s" title="%s"></div>
                    """;

    @Override
    public void Generate( Classroom classroom ) {
        try {
            FileWriter html = new FileWriter( classroom.getName() + ".html" );

            String date = classroom.getDate().format( DateTimeFormatter.ofPattern( "dd/MM/yyyy" ) );
            String begin = classroom.getBegin().toLocalTime().toString();
            String end = classroom.getEnd().toLocalTime().toString();
            String name = classroom.getName();
            String fileName = classroom.getFilename();
            String studentAmount = Integer.toString( classroom.getStudents().size() );
            StringBuilder data = new StringBuilder();

            long classroomDuration = Duration.between( classroom.getBegin() , classroom.getEnd() ).toSeconds();

            for ( Student student : classroom.getStudents() ) {
                String id = student.getId();
                String identity = student.getName();
                String totalAttendance = Long.toString( student.getTotalAttendanceDuration() );
                String percentAttendance = Integer.toString( student.getAttendancePercent( classroom.getDureeCours() )) + "%";
                StringBuilder timeBar = new StringBuilder();

                LocalDateTime blockBegin = null;
                LocalDateTime lastEnd = classroom.getBegin();
                boolean isOpen = false;
                for ( LocalDateTime event : student.getEventList() ) {
                    if( !isOpen ) {
                        blockBegin = event;
                        if( !lastEnd.equals(blockBegin) ){
                            timeBar.append(generateTimeBarDiv(lastEnd,blockBegin,classroomDuration,false));
                        }
                    } else {
                        timeBar.append(generateTimeBarDiv(blockBegin,event,classroomDuration,true));
                        lastEnd = event;
                    }
                    isOpen = !isOpen;
                }

                if( !lastEnd.equals(classroom.getEnd()) ){
                    timeBar.append(generateTimeBarDiv(lastEnd,classroom.getEnd(),classroomDuration,false));
                }

                String studentData = String.format(
                        HTML_DATA_BLOCK,
                        id,
                        identity,
                        timeBar,
                        totalAttendance,
                        percentAttendance
                        );

                data.append( studentData );
            }

            String fullPage = String.format(
                    HTML_DOC,
                    date,
                    begin,
                    end,
                    name,
                    fileName,
                    studentAmount,
                    data
            );

            html.append( fullPage );
            html.flush();
            html.close();
        } catch ( Exception e ) {
            PopupManager.showAlert( "Erreur lors de la création du fichier !" );
        }
    }

    private String generateTimeBarDiv(LocalDateTime begin,LocalDateTime end,long total, boolean isConnected) {
        long duration = Duration.between( begin , end ).toSeconds();
        double percent = (double)duration * 100 / (double)total;
        return String.format(
                TIME_BAR_ITEM,
                (isConnected)?"green":"white",
                percent + "%",
                (isConnected)?
                        "connecté(e) de " + begin.toLocalTime().toString() + " à " + end.toLocalTime().toString():
                        "déconnecté(e) de " + begin.toLocalTime().toString() + " à " + end.toLocalTime().toString()
        );
    }
}