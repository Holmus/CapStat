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

| # | Actor                                   | System                                                                 |
|---|-----------------------------------------|------------------------------------------------------------------------|
| 1 | Clicks Register button from main view   | Displays the Register view and focuses the nickname textbox            |
| 2 | Enters nickname in nickname textbox     | Displays green checkmark next to nickname textbox to indicate OK input |
| 3 | Enters password in password textbox     | Displays green checkmark next to password textbox to indicate OK input |
| 4 |                                         | Enables Register button                                                |
| 5 | Clicks Register button in Register view | Shows confirmation screen with button back to main view                |
| 6 | Clicks button in confirmation screen    | Displays the main view with the user now logged in                     |

### Alternative flow of events
#### Flow 2.1
User enters a nickname that is not valid (contains illegal characters, or is already taken)

| #     | Actor                               | System                                                                                  |
|-------|-------------------------------------|-----------------------------------------------------------------------------------------|
| 2.1.1 | Enters nickname in nickname textbox | Displays a red checkmark next to nickname text box, with explanatory text below textbox |

#### Flow 2.2
User enters a password that is not valid (too short, does not contain all required characters, contains illegal characters)

| #     | Actor                               | System                                                                                  |
|-------|-------------------------------------|-----------------------------------------------------------------------------------------|
| 2.2.1 | Enters password in password textbox | Displays a red checkmark next to password text box, with explanatory text below textbox |
