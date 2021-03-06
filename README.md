Address Book Application
===========================================

Description
-----------

Address book application is designed and implemented on top of Java and Swing to support the following requirements:

As a Reece Branch Manager 
I would like an address book application on my PC
So that I can keep track of my customer contacts

Acceptance Criteria
  - Address book will hold name and phone numbers of contact entries
  -	Users should be able to add new contact entries
  -	Users should be able to remove existing contact entries
  -	Users should be able to print all contacts in an address book
  -	Users should be able to maintain multiple address books
  -	Users should be able to print a unique set of all contacts across multiple address books
 

Two UIs are used for address book manipulation and contact manipulation.


1. Address book info UI

   This is the place to maintain multiple address books, the provided functions are listed here:
   
   1) create an address book
   2) remove address books 
        (press CTRL + mouse left button to select one or more address books)
   3) search address books 
        (instant searching after typing in address book name)
   4) show contacts in selected address books 
        (press CTRL + mouse left button to select one or more address books)
   5) show unique contacts in selected address books
        (press CTRL + mouse left button to select one or more address books)
   6) clear the displayed data
   7) go to the contact management UI
   
2. Contact management UI

   This is the place to maintain contacts, the provided functions are listed here:
   
   1) create a contact
   2) remove selected contacts
        (support removing one or more contacts,  press CTRL + mouse left button to select one or more contacts)
   3) search contacts
        (instant searching after typing in contact name / phone)
   4) show contacts in selected address books
        (press CTRL + mouse left button to select one or more address books)
   5) show unique contacts in selected address books
        (press CTRL + mouse left button to select one or more address books)
   6) clear the displayed data
   7) close the contact management UI   
       

Project Structure
-----------------------------------------------

The project structure is created with Maven and follow the conventions. 

   
Reece-address-book (project root directory)

    -- src   (source code)
    
       -- java
          
          -- manager  (the classes managing UI elements communication)
          -- model    (the model calssed used in UI, service, repository)
          -- repo     (the classes simulating address book data store)
          -- service  (the classes managing address books and contacts)
          -- ui       (the user interface classes)
          App         (the application entry point)
    
    -- test  (unit tests)

       -- java
       
          -- repo     (unit tests for repo classes)
          -- service  (unit tests for service classes)
          
    pom.xml           (the Maven pom file)
    
    README.md         (the project document file)
          


System Environment
-----------------------------------------------
* Java version: 1.8.0_172, vendor: Oracle Corporation
* IntelliJ IDEA 2018.1.6 (Ultimate Edition)
* Git version 2.18.0.windows.1
* Apache Maven 3.5.0
* OS name: "windows 7", version: "6.1", arch: "amd64", family: "windows"


Building
--------

To build, enter the project directory, for example "/d/dev/Code/reece-address-book" and type the following command:

    mvn clean install

This command would trigger the maven build process, run unit tests and generate the artifact "reece-address-book-1.0.jar" in the target directory

To build without running the unit test, enter the project directory, for example "/d/dev/Code/reece-address-book" and
 type the following commands:

    mvn clean install -Dmaven.test.skip=true


Running Application from the command line
-------------------------------------------

To run the address application, please use the following option to run the executable jar "reece-address-book-1.0.jar" which
 would show the address book info UI. Users can click buttons on the UI to add/remove/search address books and 
 also can navigate to contact management UI from the address book info UI to add/remove/search contacts.

Option 1:
Enter the project directory, for example "/d/dev/Code/reece-address-book", type the following command, 

    java -jar target/reece-address-book-1.0.jar

for example:

    Administrator@AUMMN MINGW64 /d/dev/Code/reece-address-book (dev)
    $ java -jar target/reece-address-book-1.0.jar
    

This would start the address book application and present the address book info UI.


Stop Running Application from the command line
-------------------------------------------

To stop the application,  press CTRL and C keys together to terminate the current process.
Or click the red X icon on the right top of the address book info UI to stop it.


Version Control
-------
Git is used for version control system.




Contact
-------
If need more information, please contact me at  canglangke001@gmail.com




