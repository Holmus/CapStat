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
2.  SaveResults
3.  RegisterUser

#### 2.3.3 Analysis model
See Appendix.

There will be unique id’s for
*   Players, the nickname
*   Matches, the match ID

The persistent objects are
*   ResultsLedger
*   UserLedger

#### 2.3.4 User interface
CapStat will use a fixed (non skinnable, non themable) GUI. The GUI will have a fixed size. The GUI will consist of two main panels, one for game recording, and one for statistics viewing. The panel for game recording will have large objects and clear colors to ensure sure that the game state can be understood at a glance, and will allow very little user modification. The panel for statistics viewing will be fairly simple, providing a text field for entering a user name, and a line graph to plot different statistics.

### 2.4 References
See Rules of Caps.

Appendix
========

## Use cases
Overview:

asdl;kfjkla;sdfjkl;asjdf picture here

## Use case texts

### UC Exit


### Summary
This UC describes how to exit the application.

### Priority
Low

### Extends
-

### Includes
-

### Participants
User

### Normal flow of events
This is a very straight-forward UC; almost nothing extra-ordinary happens.

| # | Actor               | System                                           |
|---|---------------------|--------------------------------------------------|
| 1 | Presses exit button |                                                  |
| 2 |                     | Aborts any ongoing activity, immediately closing |

### UC Login

### Summary
This UC describes the behaviour of the application when the user tries to log in to the system. Alternative entry point of the application.

### Priority
Medium

### Extends
-

### Includes
-

### Participants
Not logged-in user

### Normal flow of events
The login view is the main view that is shown when starting the application.

| # | Actor                        | System                                                                   |
|---|------------------------------|--------------------------------------------------------------------------|
| 1 | Enters nickname and password |                                                                          |
| 2 | Presses login button         |                                                                          |
| 3 |                              | Validates nickname with password, moves to main view for logged-in users |

### Exceptional flow of events
#### Flow 3.1: login credentials invalid
A user with the given nickname doesn't exist, or the password is wrong.

| #   | Actor | System                                    |
|-----|-------|-------------------------------------------|
| 3.1 |       | Displays error text next to failing field |

### UC Logout

### Summary
This UC describes the behaviour of the application whenever the user tries to logout.

### Priority
Low

### Extends
-

### Includes
-

### Participants
Logged-in user

### Normal flow of events
Fairly straight-forward. The logout button is not available while recording a match.

| # | Actor                                       | System                                              |
|---|---------------------------------------------|-----------------------------------------------------|
| 1 | Presses logout button in upper right corner |                                                     |
| 2 |                                             | Exits current activity, moves to user to login view |

### UC PlotAccuracy

### Summary
This UC describes the behaviour of the application when the user wants to display a plot for a users accuracy over time.

### Priority
Medium

### Extends
-

### Includes
-

### Participants
User

### Normal flow of events
This is done from the statistics view.

| # | Actor                                          | System                                                   |
|---|------------------------------------------------|----------------------------------------------------------|
| 1 | Enters nickname of user to show statistics for |                                                          |
| 2 | Chooses accuracy and time, respectively        |                                                          |
| 3 | Presses Plot button                            |                                                          |
| 4 |                                                | Gathers data for given user                              |
| 5 |                                                | Displays accuracies over time as a line graph            |

### UC PlotThrows

### Summary
This UC describes the behaviour of the application when the user wants to display a plot for a users total number of throws in matches over time.

### Priority
Medium

### Extends
-

### Includes
-

### Participants
User

### Normal flow of events
This is done from the statistics view.

| # | Actor                                          | System                                                               |
|---|------------------------------------------------|----------------------------------------------------------------------|
| 1 | Enters nickname of user to show statistics for |                                                                      |
| 2 | Chooses accuracy and time, respectively        |                                                                      |
| 3 | Presses Plot button                            |                                                                      |
| 4 |                                                | Gathers data for given user                                          |
| 5 |                                                | Displays total number of throws over time as a line graph            |

### UC RecordHit

### Summary
This UC describes the behaviour of the application while recording a match, and the user registers a hit.

### Priority
High

### Extends
RecordThrow

### Includes
-

### Participants
Spectator

### Normal flow of events
Very straight-forward.

| # | Actor                                                  | System                                                                                 |
|---|--------------------------------------------------------|----------------------------------------------------------------------------------------|
| 1 | Presses either Space (keybind) or on-screen Hit button |                                                                                        |
| 2 |                                                        | Switches turn to other player, registers hit (either continuing duel or starting duel) |

### UC RecordMatch

### Summary
This UC describes the very abstract, high-level description of what happens when a User wants to record the results of a game.

### Priority
High

### Extends
-

### Includes
* StartMatch
* RecordThrow
* SaveResults

### Participants
A logged in User that wants to record a match

### Normal flow of events
| # | Actor                                    | System                                                       |
|---|------------------------------------------|--------------------------------------------------------------|
| 1 | Starts the recording of a match          |                                                              |
| 2 |                                          | Displays the recording view                                  |
| 3 | Records outcomes of throws               |                                                              |
| 4 |                                          | Displays state of game based on results until match finishes |
| 5 | Confirms results and presses Save button |                                                              |
| 6 |                                          | Stores results in database                                   |

### UC RecordMiss

### Summary
This UC describes the behaviour of the application while recording a match, and the user registers a miss.

### Priority
High

### Extends
RecordThrow

### Includes
-

### Participants
Spectator

### Normal flow of events
Very straight-forward.

| # | Actor                                               | System                                                |
|---|-----------------------------------------------------|-------------------------------------------------------|
| 1 | Presses either G (keybind) or on-screen Miss button |                                                       |
| 2 |                                                     | Switches turn to other player (if no duel is ongoing) |

### Alternative flow of events
#### Flow 2.1: duel is ongoing
If a duel is ongoing, the miss ends the duel, and the player who lost the duel has the next turn.

| #   | Actor                                               | System                                                                                            |
|-----|-----------------------------------------------------|---------------------------------------------------------------------------------------------------|
| 2.1 |                                                     | Ends duel, removing a glass from the losing player (or other action, depending on state of match) |
| 2.2 |                                                     | Leaves the turn to the losing player (i.e. not switching)                                         |

### UC RecordThrow

### Summary
A throw is an attempt by one player to hit the glass of the opposing player. The outcome is either a hit or a miss.

A match is made up of a series of rounds, which is made up of plays, which are in turn made up of throws. This makes throws the most atomic part of a game of caps.

### Priority
High

### Extends
-

### Includes
-

### Participants
Spectator


### Normal flow of events

| # | Actor                 | System                             |
|---|-----------------------|------------------------------------|
| 1 | Registers hit or miss |                                    |
| 2 |                       | Records hit or miss                |
| 3 |                       | Updates GUI to indicate game state |
| 4 |                       | Passes turn over to next player    |

### UC Register

### Summary
This UC describes the process of registering a new User in the system, allowing them to act as both Spectators and Players.

### Priority
High

### Extends
-

### Includes
-

### Participants
A person that is not currently registered in the system

### Normal flow of events
Registration proceeds without complications

| # | Actor                                                                      | System                                                  |
|---|----------------------------------------------------------------------------|---------------------------------------------------------|
| 1 | Clicks Register button from main view                                      |                                                         |
| 2 |                                                                            | Displays the Register view and focuses the name textbox |
| 3 | Enters information (nickname, password, date of birth, year of admittance) |                                                         |
| 6 | Clicks Register button in Register view                                    |                                                         |
| 7 |                                                                            | Shows main view confirmation that registering wen well  |

### Alternative flow of events
#### Flow 3.1
User enters information that is not valid for registering (illegal characters in nickname and/or nickname already taken)

| #     | Actor                      | System                                                         |
|-------|----------------------------|----------------------------------------------------------------|
| 3.1.1 | Enters illegal information |                                                                |
| 3.1.2 |                            | Displays a red text next to nickname input field stating error |

### UC SaveResults

### Summary
This UC describes the behaviour of the application when a match is finished and the results are to be submitted to persistant storage.

### Priority
High

### Extends
-

### Includes
-

### Participants
Specatator

### Normal flow of events
Very straight-forward. This happens when the match is over, and the match winner is displayed on-screen.

| # | Actor                      | System                                                                  |
|---|----------------------------|-------------------------------------------------------------------------|
| 1 | Presses Save Result-button |                                                                         |
| 2 |                            | Switches to main logged-in view, with message that the result was saved |

### UC ShowStatistics

### Summary
This UC describes the overarching behaviour of the application when the user wants to display statistics.

### Priority
Medium

### Extends
-

### Includes
-

### Participants
User (does not have to be logged in)

### Normal flow of events
Very general: this particular UC only describes how the statistics view is shown; the getting of actual statistics are located in their own UCs.

| # | Actor                                              | System                                                                           |
|---|----------------------------------------------------|----------------------------------------------------------------------------------|
| 1 | Presses Statistics button on main (logged-in) view |                                                                                  |
| 2 |                                                    | Displays a view with a text field for nickname, drop-down boxes and a line chart |

### UC StartMatch

### Summary
This UC describes the behaviour of the application when a User wants to record results for a new Match. This UC is probably not preceded by any other UC, as this UC more or less is the entry point in the application.

The application focuses on letting the user quickly start recording results, and therefore uses default settings unless the user changes them.

### Priority
High

### Extends
-

### Includes
-

### Participants
A User that is logged in to the system

### Normal flow of events
| # | Actor                        | System                                          |
|---|------------------------------|-------------------------------------------------|
| 1 | Clicks the Play Match button |                                                 |
| 2 |                              | Switches view to the results recording view     |
| 3 |                              | Loads the default settings for a game of caps   |
| 4 |                              | Focuses the input area, enabling keyboard input |
