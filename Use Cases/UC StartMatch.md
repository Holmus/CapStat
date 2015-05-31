# UC StartMatch

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
