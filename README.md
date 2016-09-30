# _Wildlife Tracker_

#### By _Ewa Manek_

## Description

_Web App for tracking wildlife sightings with Java backend using Spark with Velocity and Postgresql_

## Setup/Installation Requirements

* _Copy the repository from GitHub_
* _Make sure you have gradle and postgresql installed!_
* _Use the following commands in psql to create the postgres database or just create the wildlife_tracker database and run 'psql  wildlife_tracker < wildlife_tracker.sql'_
  * CREATE DATABASE wildlife_tracker;
  * CREATE TABLE animals (id serial PRIMARY KEY, name varchar, species varchar, health varchar, age varchar, endangered boolean);
  * CREATE TABLE sightings (id serial PRIMARY KEY, location varchar, rangerName varchar, date_sighted timestamp);
  * CREATE TABLE animals_sightings (id serial PRIMARY KEY, animal_id int, sighting_id int);
* _gradle will download and install junit and spark_
* _the 'gradle run' command will deploy the site to port 4567 by default_

## Technologies used

* Java 1.8.0_101
* Gradle 3.0
* JUnit 4.+
* Spark 2.3
* Velocity Template Engine 1.7
* Postgresql 9.6

## GitHub link

https://github.com/ewajm/wildlife-tracker

## Licensing

* MIT

Copyright (c) 2016 **_Ewa Manek_**
