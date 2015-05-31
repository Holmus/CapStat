# UC Logout

### Summary
This UC describes the behaviour of the application whenever the user tries to logout.

### Priority
Low

### Extends
-

### Includes
-

### Participants
Logged-in user

### Normal flow of events
Fairly straight-forward. The logout button is not available while recording a match.

| # | Actor                                       | System                                              |
|---|---------------------------------------------|-----------------------------------------------------|
| 1 | Presses logout button in upper right corner |                                                     |
| 2 |                                             | Exits current activity, moves to user to login view |
