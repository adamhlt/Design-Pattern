# Microsoft-Teams Attendees Analyser

Microsoft-Teams Attendees Analyser is an end year projet of our last computer science degree. It was made by three
students :

- Henault Adam
- Tahiri Mohamed
- Foucher Alexandre

## Software

This software allow user to read / visualize more easily a csv file of all connections and disconnections events of
students in teams classroom.

A csv data file look like this :

```
Nom complet	Action de l’utilisateur	Date et heure
Prenom1 Nom1	Rejoint	19/01/2021 à 09:58:51
Prenom2 Nom2	Rejoint	19/01/2021 à 10:00:39
Prenom2 Nom2	A quitté l'appel	19/01/2021 à 11:54:58
Prenom3 Nom3	Rejoint	19/01/2021 à 10:00:43
Prenom3 Nom3	A quitté l'appel	19/01/2021 à 11:55:03
Prenom4 Nom4	Rejoint	19/01/2021 à 10:01:03
Prenom4 Nom4	A quitté l'appel	19/01/2021 à 11:55:09
Prenom5 Nom5	Rejoint	19/01/2021 à 10:01:17
Prenom5 Nom5	A quitté l'appel	19/01/2021 à 11:55:12
```

Allow user to generate result like this :

<p align="center">
  <img width="650" src="https://i.imgur.com/RqtJxOU.png" alt="">
</p>

## How to use

- First the software will need a correct csv file when it's done it will show up some information la like :
    - First connection
    - Last disconnection
    - Date of the course
- Then the user had to give name, begin time and end time to the classroom
- To finish user have to choose between multiple setting and then press generate

<p align="center">
  <img width="460" src="https://i.imgur.com/yugsCrV.png" alt="">
</p>

## Design Pattern

#### Strategy pattern

The most used one, we use **strategy pattern** for our generator and our sorter algorithms that allow us to add as many
we want of them.

Class diagram look like this :

<p align="center">
  <img width="600" src="https://i.imgur.com/UKUvRZZ.png" alt="" style="border-radius: 15px">
</p>

#### Iterator

We also use **iterator** to manage our list iteration, in this project iterator are slightly overkill but this is a
school projet and we need to show what we can do so we did it.

Class diagram look like this :

<p align="center">
  <img width="600" src="https://i.imgur.com/0rJFW4q.png" alt="" style="border-radius: 15px">
</p>

