System design document for CapStat (SDD)
=======



>Version: 1.0

>Date: May 29, 2015

>Author: Johan Andersson, Rikard Hjort, Jakob Holmgren, Christian Persson

This version overrides all previous versions.

###1 Introduction

####1.1 Design goals
The design uses loose coupling between modules to provide the possibility to changes GUI, to move the persistent storage, i.e. to a new remote database or to using a web API. The design should allow users to have great control of the statistics presentation described further down. The design must also be testable, so that unit tests can be written for specific modules. See RAD document for goals regarding usability.

####1.2 Definitions, acronyms and abbreviations
The references section contains definitions and rules for the caps game, which this application is a tool for.

* GUI, graphical user interface.
* JavaFX, a framework for creating a GUI.
* Match, a single match of caps.
* Round, one round in the game, which can consist of several rounds.
* Throw, a single throw in a match, which can be either a hit or miss.
* MVC, a general principle for decoupling responsibilities in the application. See Layered architecture.
* Layered architecture, a way of partitioning the application into four loosely coupled layers used for GUI, controlling the game, representing the domain and handling technical implementations, respectively.
* Business domain, the problem that the application models and for which it provides tools.
* Event bus, an object which handles firing events and adding event listeners in the application.

###2 System design

####2.1 Overview
The application will use layered architecture, which is a modified version of MVC.

The presentation layer will hold the GUI components, which are implemented with the JavaFX framework. The application layer will mainly modify and control the domain layer, the domain layer will model the core domain, and the more technical aspects which the domain layer needs will be handled by the infrastructure layer, which will also take care of event handling through an event bus.

#####2.1.1 The domain model
The model's task is to model the world of caps, where there a matches being played, matches that have been played, and users playing and spectating matches. The model also models the statistics of players, such as their ranking, their accuracy, their wins and losses.

#####2.1.2 Rules
The rules of caps is very well defined, and not likely to change. However, the Match class could be subclassed if different behavior is needed. For a full definition of the rules of caps, see appendix.

#####2.1.3 Matches
A match is played until one player has won as many rounds as is needed. The number is decided at the outset of the match. The outcome of a match, as well as every play of the match, can be recreated at any time as long as the starting player and the full throw sequence is known. However, we have implemented the possibility for a match to have an incomplete throw sequence, without that making the statistics useless

#####2.1.5 Throw sequences
A throw sequence is a series of throws, which are either hits or misses. These are kept by the match they represent, and stored permanently once the game ends. If one has the full, unbroken throw sequence of a match, one could compute anything about the match. But, since the application relies on rapid user input, which could become incorrect at any time, a slightly modified approach to the full, perfect sequence has been chosen. The throw sequence of a match can be split up in several small sequences, each describing the game state at the exact point where it starts. Thus, if something goes wrong, a user can "start over" from a new starting point, while losing minimal amount of information, and without having to discard the whole sequence.  

#####2.1.4 Statistics
The part of the model that handles statistics uses a minimal data input. It uses only the throw sequences, and some simple information about the match. From that information, the statistics can deduce anything that is deducible about a game.

#####2.1.5 Event handling
During a match, a lot of events take place that other parts of a system want to be notified about. Rather than making the Match class handle these publisher-subscriber relations, which might be many, it delegates this work to an event bus. The clients that want to be notified of changes subscribe using the string keys that the Match class provides, and the match notifies the event bus when a change relating to a specific key has taken place. For example, if a client want to get notified when a match ends, it may do so by registering with the event bus using the MATCH_ENDED String constant in match. This approach has the added benefit of being reusable – the event bus can be used for many types of event handling, by any other class. It is strongly recommended that future classes also use the event bus rather than implement another version of the publisher subscriber pattern.

####2.2 Software decomposition
#####2.2.1 General
The software is decomposed into four main modules, or "layers". Two of these have submodules. See Figure X. The modules are ordered hierarchically, meaning that no module may know about another module of higher order. The following list orders modules from higher to lowest. This means that tha presentation layer may know about all other layers, while the infrastructure layer may only know about itself. See figure X for a diagram of the relationship between layers.

* **Presentation layer**, that handles drawing the GUI and taking user input. This is the view part of the MVC.
* **Application layer**, that handles controlling and modifying the model, and coordinating the model and infrastructure layer. This is the controller part of the MVC.
* **Model layer**, the core object model of the application, representing games of caps, users and handling statistics calculations.
* **Infrastructure layer**, handles low level technical details, such as communicating with the database and managing the task queue that the database uses for intermediate storage. This layer also holds the event bus.

![Figure X. The relationships between layers](./images/top_layer_modules.png)

*Figure X. The reltionship between layers. Note that the top layer, called only "capstat", contains only one class, Main, with the single main method that starts the program, and nothing else It is therefore not mentioned int the list of layers.**

#####2.2.2 Decomposition into subsystems
The model layer and the infrastructure layer consists of submodules. The hierarchical philosophy applies here as well: a lower ordered package may not be aware of a higher ordered one. See figure X and X for a diagram of realtionships between packages.

The model layer consists of the following subsystems:
* **Statistics package** contains a class for representing a match that has been played and saved, a repository for storing and saving matches, and means of creating plottable statistics for played games.
* **Match package**, the module that contains the match factory, matches and the throw sequences. This submodule represents the "live" match, that can be played and listened to by observers.
* **User package** contains elements of the User aggregate, means to create it, a class for keeping track of and setting which user is currently logged in, and a repository for storing and retrieving users.

![Figure X, the model package](./images/model_package.png)

*Figure X. The relationship between submodules in the model layer.*

The infrastructure layer consists of the following subsystems:
* **Database package**, manages connection to the database, both modifying and querying. This package contains value objects, called "blueprints", representing what can be stored to or fetched from the database.
* **Taskqueue package**, handles local intermittent storage while objects are added to the database, to prevent dataloss.
* **Eventbus package**, contains the event bus and the interfaces that listeners need to implement to register with it.

![Figure X. the infrastructure layer](./images/infrastructure_package.png)

*Figure X. The relationships between subsystems in the infrastructure layer.*

#####2.2.3 Layering
The layering follows the pattern of the modules described under 2.2.1. Figure X describes this layering. Higher ordered layers are higher in the figure.

2.2.4 Dependency analysis

2.3 Concurrency issues

2.4 Persistent data management

2.5 Access control and security

2.6 Boundary conditions

3 References

  APPENDIX
