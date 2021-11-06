# AGILE-Cinemas
## Overview
  AGILE-Cinemas is a cinema ticket management system. This Project is made of three sprints.
  Please find features in Sprint Features below.
  
## Key Topic
  * Tips
    * Normal User login
      * Type any word to get into customer interface.
      * You can choose to skip login and view movies information as a guest.
    * Staff login
      * Type 12345 to access the staff interface.
      * Use 4-digit staff id(Can be created by manager.)
      * The system will check if you are manager by the staff id.
  * Sprint Features
    * Sprint One
      * Anyone(Guest/Customer) can view the available movies.
      * Customer can sign in and Guest can sign up.
      * User can cancel as they wish.
      * User can enter booking screen and must log in before enter that.
      * Create Staff Interface.(Without any features.)
    
    * Sprint Two
      * Customer can choose both the type or amount of the seats.
      * Customer might choose to pay by credit card or gift card when check out.
      * Staff can log in and edit the information of movies.
      * The system can generate Ticket ID.
      * Exit if time out (2mins) for now.
      
    * Sprint Three
      * Take user back to main menu when time out (2 mins)
      * Staff can add/remove/modify the movie data.
      * Staff can genereate the basic report including all booking details.
      * Manager has the same ablities as staff's. 
      * Manager can add or remove staff and generate Cancellation report including all the cancelled transactions.
  
  * How to run the program.
    * Make sure you have following tools updated before running the program.
      * The latest JAVA.(JAVA16)
      * Gradle higher than 7.0.0
    
    * By using 'gradle run -console pain' in the terminal. 
    
    * To enter the staff interface, please enter staff number 12345 then input 1234 as staff key.
  
  * How to do testing.
    * Make testcases by using Junit. The test file should follow the rules of Junit.
    
    * Run testcases
      * Every time running 'gradle build' will make testcases run automatically.
        or
      * Type 'gradle test' in the terminal to run the testcases manually.
  
  
  * How to collaborate.
    * Jenkins already set up.
    
    * All the pushes that are going to happen on master will be validated.
