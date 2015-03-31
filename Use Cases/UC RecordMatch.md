# RecordMatch

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
| 1 | Starts the recording of a match          | Displays the recording view                                  |
| 2 | Records outcomes of throws               | Displays state of game based on results until match finishes |
| 3 |                                          | Match finishes, displays match results and statistics        |
| 4 | Confirms results and presses Save button | Stores results in database                                   |
