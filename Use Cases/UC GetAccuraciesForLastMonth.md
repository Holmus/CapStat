# UC GetAccuraciesForLastMonth

### Summary
This UC describes an example of getting certain statistics for a certain user under a certain period of time. It is designed to represent an average statistics "task".

### Priority
Medium

### Extends
-

### Includes
-

### Participants
The current user of the system

### Normal flow of events
The hypothetical situation is that the user wants to a plot of their accuracy over the last month. This is thought to take place in the "main view" of the statistics GUI.

| # | Actor                                        | System                                                         |
|---|----------------------------------------------|----------------------------------------------------------------|
| 1 | Selects to view a plot                       |                                                                |
| 2 | Selects time and accuracy as axises          |                                                                |
| 3 | The current user is already selected as user |                                                                |
| 4 | Selects "from" date to one month back        |                                                                |
| 5 |                                              | Displays loading display (since the calculation can take time) |
| 6 |                                              | Displays a plot for the desired results                        |
