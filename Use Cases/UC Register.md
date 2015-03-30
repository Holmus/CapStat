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

| # | Actor                                                                      | System                                                                   |
|---|----------------------------------------------------------------------------|--------------------------------------------------------------------------|
| 1 | Clicks Register button from main view                                      | Displays the Register view and focuses the nickname textbox              |
| 2 | Enters information (nickname, password, date of birth, year of admittance) | Displays positive indicate next to each input field to indicate OK input |
| 3 |                                                                            | Enables Register button                                                  |
| 4 | Clicks Register button in Register view                                    | Shows confirmation screen with button back to main view                  |
| 5 | Clicks button in confirmation screen                                       | Displays the main view with the user now logged in                       |

### Alternative flow of events
#### Flow 2.1
User enters information that is not valid for registering, e.g. illegal characters in nickname och nickname already taken

| #     | Actor                      | System                                                           |
|-------|----------------------------|------------------------------------------------------------------|
| 2.1.1 | Enters illegal information | Displays a red checkmark next to the input field that is invalid |
