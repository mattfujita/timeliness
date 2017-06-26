# Timeliness - An Angular Training Tool

This project contains a Spring Boot project that you can use to learn AngularJS.

There are a bunch of tags that label each steps completion. That, in addition to the learning material, make it easy to instruct AngularJS to a group of students.

## How to Run

* Make sure you have PostgreSql installed. (macOS users with Homebrew can use `brew install postgresql`)
* Create a PostgreSql role named "timely" with the password "timely". (You can use the command-line command `psql postgres -c "create role timely with login password 'timely'"`)
* Create a PostgreSql database named "timely" owned by the "timely" role. (You can use the command-line command `psql postgres -c "create database timely with owner timely"`)
* Make sure you have [maven](http://maven.apache.org/) installed. (macOS users with Homebrew can use `brew install maven`)
* Make sure you have Java 8 or better installed. (macOS users with Homebrew can use `brew cask install java`)
* In the directory into which you cloned this repository, run `mvn spring-boot:run`
* Open your browser to [http://localhost:5000](http://localhost:5000)
* Log in with maria/maria or hector/hector or register with your own username and password.
