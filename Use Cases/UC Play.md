# Play

###Summary
A play is made up of throws. There are essentially two kinds of plays: the one that ends in duel, an the one that does not.

* A play where the first throw is a miss is over after the first throw, without a duel.
* If the first throw is a hit, a duel ensues. The play ends when the duel ends.

The spectator only needs to register hits and misses, and the application will decide when plays end and whose turn it is to initiate a play, etc.

###Priority
High

###Extends
-

###Includes
* Duel
* Throw


###Participants
Spectator

*(Even though the players are performing the acts of a play, such as throwing and duelling, the spectator is the person interacting with the application.)*

###Normal flow of events
|| Actor | System |
|----------------
|1| Registers hit or miss | |
|2| | Give visual feedback that there was a hit
|3| | **if (hit)** &emsp;perform a duel (see separate use case) <br />**else**  &emsp;do nothing
|4| | End play and switch turns
