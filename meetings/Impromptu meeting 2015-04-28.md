##Impromtu meeting

Add isLoggedIn boolean in CapStat class. Important for the GUI: will decide what the user can see, allow them to see their own stats and such. The CapStat class should also hold reference to one user, which is the logged in user.

### Goal for next week
Implement the saving of matches: finished but unapproved matches should be saved separately, and be moved to the standard database once both players have approved it.

The saving should be done to MatchRegister, which should point to a facade in the Infrastructure level with methods for saving and retrieving. The Facade handles directs call to the correct classes, who manage talking to the database.

### Solution for subscriptions/Observer pattern

Instead of using Observers, we build an Event Bus that handles all types of events. The implementation will be like this:
* The event bus has a few HashMaps with Strings as keys, and a Set of (for example) NotificationListeners, DataListeners, PropertyChangeListener.
* The listeners implement an interface (naturally) which gives them this type. When they add themselves, they add themselves with a String as key. When an event happens, the event bus in notified with a String, and calls the proper methods of all the listeners tied to that Key.
* An alternative approach is: create a type of enum, Event, which is used as Key. This is a fixed list, but more can be added.
