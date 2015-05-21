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

2.2 Software decomposition

2.2.1 General

2.2.2 Decomposition into subsystems

2.2.3 Layering

2.2.4 Dependency analysis

2.3 Concurrency issues

2.4 Persistent data management

2.5 Access control and security

2.6 Boundary conditions

3 References

  APPENDIX
