CapStat
=======

>CapStat is a project by the following Users:
>
* **Christian Persson**, committing as Christian Persson
> * **Rikard Hjort**, committing as hjorthjort
> * **Jakob Holmgren**, committing as Jakob Holmgren
> * **Johan Andersson**, committing as lol2kpe

CapStat is a Java application designed for tracking statistics and players of the drinking game caps. It focuses on providing a stable backend to build frontends upon, with the provided GUI acting as an eample for how a frontend might look like. The provided GUI focuses on keyboard-driven input during recording of matches, and providing some examples of statistics available to extract from matches.

## Getting Started
1.  Clone the repository. The latest stable version is available in the `master` branch, while newer additions and ongoing development can be found in the `develop` branch.
2.  For the purpose of this project, CapStat currently uses a local [MySQL Community Edition](https://www.mysql.com/products/community/) server as database to store information. Download and install MySQL Community Edition, and start the MySQL server **prior** to building or running the application.

    For example, installing and running MySQL can be done on a Mac OS X system with [Homebrew](http://brew.sh/) with the following commands:

        brew update
        brew install mysql

    Then, to start the server:

        mysql.server start

    For other systems, please see MySQLs installation and usage instructions.

3.  After the server has started, some scripts need to be run in order to set up the database and table structure. First run `database/init.sql`, and then run `database/capstat.sql`, **as the root database user**. On a Mac OS X system, this can be done using the following commands:

    For `init.sql`:

        mysql -uroot < database/init.sql

    For `capstat.sql`:

        mysql -uroot capstat < database/capstat.sql

    For other systems, please refer to the MySQL documentation.

4.  CapStat uses [gradle](http://gradle.org/) as build system, but `gradle` does not need be installed in order to build and run the application. Instead, use the provided [gradle wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html). From the root directory of the project, issue the following commands (use `gradlew.bat` instead of `./gradlew` on Windows systems):

    Building:

        ./gradlew build

    Testing:

        ./gradlew test

    Running:

        ./gradlew run
