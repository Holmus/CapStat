# UC Miss

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
