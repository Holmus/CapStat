# Register

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
