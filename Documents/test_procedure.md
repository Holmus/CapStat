# Test procedures to successfully test 'all' possible cases in CapStat


### Register user
1. Register a simple user.
	* OUTCOME: **SUCCESS**
		1. Click the register button.
		2. Enter credentials:
			* Full name:        Anders Andersson
			* Username:         AndAnd
			* Password:         AndAnd1234
			* Password again:   AndAnd1234
			* Birthday:         12/31/1994
			* Attend.Year:      2014
			* LP:               1
		3. Click the register button.
	
2. Register the same user again.
	* OUTCOME: **FAIL**
		1. Click the register button.
		2. Enter credentials:
			* Full name:        Anders Andersson
			* Username:         AndAnd
			* Password:         AndAnd1234
			* Password again:   AndAnd1234
			* Birthday:         12/31/1994
			* Attend.Year:      2014
			* LP:               1
		3. Click the register button.
	
3. Register user with the same user name and every other credential unique.
	* OUTCOME: **FAIL**
		1. Click the register button.
		2. Enter credentials:
			* Full name:        Johan Johansson
			* Username:         AndAnd
			* Password:         asd123
			* Password again:   asd123
			* Birthday:         8/1/1993
			* Attend.Year:      2013
			* LP:               2
		3. Click the register button.
	
4. Register the same user with different user name.
	* OUTCOME: **SUCCESS**
		1. Click the register button
		2. Enter credentials:
		    * Full name:        Anders Andersson
		    * Username:         AndAnd2
		    * Password:         AndAnd1234
		    * Password again:   AndAnd1234
		    * Birthday:         12/31/1994
		    * Attend.Year:      2014
		    * LP:               1
		3. Click the register button.
    
5. Register user with the same user again, with the same username in lower case only.
    * OUTCOME: **FAIL**
		1. Click the register button.
		2. Enter credentials:
		    * Full name:        Anders Andersson
		    * Username:         andand
		    * Password:         AndAnd1234
		    * Password again:   AndAnd1234
		    * Birthday:         12/31/1994
		    * Attend.Year:      2014
		    * LP:               1
		3. Click the register button.

6. Register with full name, username, password, containing some foreign characters (mainly åäö).
	* OUTCOME: **SUCCESS**
		1. Click the register button.
		2. Enter credentials:
			* Full name:        Håkan Järnkrök
			* Username:         HåkNJärnKrÖk2k
			* Password:         HåkNJärnKrÖk2k1234
			* Password again:   HåkNJärnKrÖk2k1234
			* Birthday:         6/13/1986
			* Attend.Year:      2006
			* LP:               3
		3. Click the register button.

// TODO try register with empty fields. <br>
// TODO try register with faulty data (ex day 32 in birth date).

### Login
1. Login with the first user created.
	* OUTCOME: **SUCCESS**
		1. Enter credentials
			* Username:         AndAnd
			* Password:         AndAnd1234
		2. Click on the login button
		3. Click logout
		
2. Login with the first user again, using the same password in lower case only.
	* OUTCOME: **SUCCESS**
		1. Enter credentials
			* Username:         AndAnd
			* Password:         andand1234
		2. Click on the login button
		3. Click logout
		
3. Login with the first user and a faulty password.
    * OUTCOME: **FAIL**
        1. Enter credentials
			* Username:         AndAnd
			* Password:         FaultyPass
        2. Click on the login button
		
4. Login with the first user again, using the same password in lower case only.
	* OUTCOME: **FAIL**
		1. Enter credentials
			* Username:         andand
			* Password:         AndAnd1234
		2. Click on the login button
		
5. Login with a non existing user.
	* OUTCOME: **FAIL**
		1. Enter credentials
			* Username:         NotExistingUser
			* Password:         NoPassword
		2. Click on the login button
		
6. Login with the user with foreign characters.
	* OUTCOME: **SUCCESS**
		1. Enter credentials
			* Username:         HåkNJärnKrÖk2k
			* Password:         HåkNJärnKrÖk2k1234
		2. Click on the login button
		3. Click logout
		
7. Login with a blank user and password.
	* OUTCOME: **FAIL**
        1. Enter credentials
            * Username:         
            * Password:         
        2. Click on the login button

8. Login with the first user and leave password blank.
    * OUTCOME: **FAIL**
        1. Enter credentials
            * Username:         AndAnd
            * Password:         
        2. Click on the login button

### Play match

* Play match as guest.
	* Using player one as the guest user.
		* Using player two as a registered user.
		* Using player two as a non-registered user.
	* Using player two as guest user.
		* Using player one as a registered user.
        * Using player one as a non-registered user.
	* Using both players as guest user.
	* Using player one as a registered user.
		* Using player two as a registered user.
        * Using player two as a non-registered user.
    * Using player two as a registered user.
        * Using player one as a registered user.
        * Using player one as a non-registered user.
    * Using both players as a registered user.
    * Using both players as non-registered users.
* Play match as a logged in user.
	* Using player one as logged in user.
        * Using player two as a registered user.
        * Using player two as a non-registered user.
    * Using player two as logged in user.
        * Using player one as a registered user.
        * Using player one as a non-registered user.
    * Using both players as logged in user.
    * Using player one as a registered user.
        * Using player two as a registered user.
        * Using player two as a non-registered user.
    * Using player two as a registered user.
        * Using player one as a registered user.
        * Using player one as a non-registered user.
    * Using both players as a registered user.
    * Using both players as non-registered users.
		
* Play matches with different outcomes.
	* p1 wins all hits.
	* p2 wins all hits.
	* playing down to the last glass every set letting p1 and p2 
	win a round each. And p1 wins the last round.
	* playing down to the last glass every set letting p1 and p2 
    win a round each. And p2 wins the last round.
    
    


### Show statistics

// TODO
