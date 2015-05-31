Requirements and Analysis Document for CapStat
==============================================

Version: 0.2  
Date: 2015-05-31  
Author: Rikard Hjort, Christian Persson  

This version overrides all previous versions.

## 1 Introduction

### 1.1 Purpose of application
CapStat is a statistics, tracking and ranking tool for the drinking game "caps". It is intended to be used over a larger period of time in order to build up a good statistic significance, and to possibly uncover previously unknown patterns and correlations in a caps players performance. For definitions, terms, and rules of the application, see references.

### 1.2 General characteristics of application
CapStat is a standalone desktop application. It tracks player achievements, statistics and results. It does this through user input of each throw made in a match – hit or miss. The end result is a database of player rankings and performance, which can be viewed within the application.

### 1.3 Scope of application
CapStat saves data to a local database on the machine on which CapStat is run, meaning that only a single match can be recorded simultaneously, and the recorded results will not be automatically shared between different machines running CapStat. The application requires manual input of data. The application does not provide support for automatically recognizing and recording hits or misses.

### 1.4 Objectives and success criteria of the project
CapStat is intended to be used for quick and correct input of game results, and focuses on speed and effectiveness of data input. Therefore the following criteria are relevant:

1.  It should be possible to start recording results for a game of caps (with default rules) with only two button presses.
2.  A spectator should be able to initiate and record the results of a game of caps in CapStat using only the keyboard.
3.  CapStat should be able to deduce the state of a game of caps correctly, assuming correct input has been given from the start of the game.
4.  CapStat should be able to give a brief, graphical overview of the current state of the game.

In addition to these, the following general criteria should also be fulfilled:

1.  CapStat should provide a GUI (see Definitions) for all parts of the application – recording results, viewing statistics, and more.
2.  Recorded results, and therefore all statistics, should be persistent between instances of the application.
3.  Changes in recorded results should be reflected in statistics without any additional user interaction.

### 1.5 Definitions, acronyms and abbreviations
**Users** are individual accounts registered to the system.
**Players** are users currently playing a match.
A **spectator** is a user inputting game stats and recording hits and misses for a game.
A **match** is a set of an odd number of rounds, in a best-of-X format. When one player has won a certain number of rounds they are declared the winner. Example: in a best-of-7, the player who first has won 4 rounds will be the winner, since it’s not possible for the other player to exceed or exceed 4 won rounds.
A **round** is a series of throws that continue until one player has lost all his glasses.
**Duels** are a sudden death style challenge of throwing caps. The first to miss his throw loses the duel.
**GUI** means Graphical User Interface.
**Java** is a platform independent programming language which will be used for this application.
**JRE** means Java Runtime Environment, and provides the needed programs to run Java applications.

## 2 Requirements

### 2.1 Functional requirements
The user should be able to:

1.  Register as user.
2.  Start a match.
    a.  Choose participating players.
    b.  Choose whether to play a ranked or an unranked match.
3.  Records the hits and misses throughout the game.
4.  Save the results of the game.

The user should also be able to:

1.  Select a user to get all match results for.
2.  Plot different aspects of the results, such as accuracy, longest duel, throws performed, against each other, or against date of match.

### 2.2 Non-functional requirements

#### 2.2.1 Usability
The user should be able to both quickly input data through the keyboard. Immediately it should be obvious how to start a game, and how to register hits and misses. The state of the game in the program should have a very clear and unambiguous mapping to the real game, and it should be readable at a glance.

The user should be able to personally decide what statistics to display, and how to display it, without interference from the program – even if this means making plots and comparisons that don’t make sense. The user is put in charge.

#### 2.2.2 Reliability
The software should never crash or freeze during the recording of a game. Some instability is permissible in the statistics view, but on the game recording side, this is not acceptable.

#### 2.2.3 Performance
Since caps can be a very fast game, the recording of throws in a match should be very efficient, meaning that recording throws should result in little to no delays at all.

Calculating statistics, however, can take a little more time, since the calculations sometimes can be fairly complex, especially if large data sets are used. All calculations should nevertheless take less than one minute to finish.

#### 2.2.4 Supportability
The application will initially be implemented as a desktop application with a desktop GUI, but it should be easy to construct new GUIs for the application, such as a web GUI or a smartphone GUI. It should take less than one man-month to implement such a GUI.

It should later on be possible to divide the application into a client/server architecture, in order to provide possibility to view ongoing matches, provide a live feed of results, etc. This functionality is intended for things such as tournament play, where many matches are going on at once and might have an audience that watches the games.

The application should include automated tests, for testing different scenarios that might appear in a match of caps, and for testing all use cases. A GUI test should also be manually performed and recorded.

#### 2.2.5 Implementation
The application will be developed in Java, meaning that all devices that run the application need to have JRE installed and configured. The application does not need to be installed on the device, but instead will be run independently. Since networking is not supported, an Internet connection is not required.

#### 2.2.6 Packaging and installation
The application will be delivered as a zip archive, containing a runnable Java file with the code for the application, as well as scripts for running the application on different platforms.

#### 2.2.7 Legal
N/A

### 2.3 Application models

#### 2.3.1 Use case model
See Appendix for UML and description of Use Cases.

#### 2.3.2 Use cases priority
1.  RecordMatch
    a.  RecordThrow
    b.  StartMatch
    c.  Undo
    d.  Revert
2.  SaveResults
3.  RegisterUser
4.  Park

#### 2.3.3 Analysis model
See Appendix.

There will be unique id’s for

*   Players, the nickname
*   Matches, the match ID

The persistent objects are

*   ResultsLedger
*   UserLedger

#### 2.3.4 User interface
CapStat will use a fixed (non skinnable, non themable) GUI. The GUI will be scalable in size, down to 800 x 400 pixels. The GUI will consist of two main panels, one for game recording, and one for statistics viewing. The panel for game recording will have large objects and clear colors to ensure sure that the game state can be understood at a glance, and will allow very little user modification. The panel for statistics viewing will consist of several customizable tabs for the user to create their own diagrams.

### 2.4 References
See Rules of Caps.
