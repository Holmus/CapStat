# UC Login

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
