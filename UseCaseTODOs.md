See SRS for complete Use Case descriptions

# Allocation #
  * Nicholas
    * {2, 3, 4, 5} - focus on validation and database manipulation
  * Sean
    * {~~1~~,16} - user authentication and administration
    * {2, 5, 13} - focus on email functionality
    * {7} - random passwords, email, database manipulation, validation
    * {8, 9, 10} - database manipulation, validation
    * {23} - email functionality, upload functionality, validation
    * {27, 28, 29} - database manipulation, validation
    * {~~30~~} - report generation
  * Xing
    * {10, 11} - database manipulation, file-system manipulation, validation
    * {25, 26} - database manipulation, report generation, validation
  * Jane
    * {~~13, 14, 15, 17, 18, 19, 20, 21~~, 23, 24} - focus on validation and form design
  * Justin
    * {~~6~~} - database manipulation, validation
    * {12} - database manipulation, validation
    * {~~14, 17, 22~~, 23} - map functionality
    * {~~22~~} - note the data security requirements

# Tickets #
  1. Use Case 2: Are bulk uploads validated? **They should be.**
  1. Use Case 3: Error file? **Rows with errors should get sent to another file.**
  1. _**Closed**_.Use Case 4: Be sure only labs can modify and view directly.
  1. _**Closed**_.Use Case 4: EOs view through reports only.
  1. _**Closed**_.Use Case 5: Labs`<`--`>`Contractors should be a many to many relationship.
  1. _**Closed**_.Use Case 5: Can EOs see contractor details? **They should.**
  1. _**Closed**_.Use Case 8: "Sampler" vs. "Sample"? **Should be "sampler".**
  1. _**Closed**_. Use Case 9: Can admins update laboratories? **They should.**
  1. Use Case 10, 11: BLOB vs FILE?
  1. Use Case 13: Send email confirmation on password change? **It should.**
  1. Use Case 13: There should also be a minimum password length.
  1. _**Closed**_.Use Case 14: License number NOT NULL? **It can be NULL.**
  1. _**Closed**_.Use Case 14: Need to be able to add samplers via map.
  1. Use Case 15: Maybe have polygon data associated with water bodies? **If there's time.**
  1. _**Closed**_. Use Case 16: Can admins update contractors? **They should.**
  1. _**Closed**_.Use Case 17: Sampler location can be updated via map.
  1. _**Closed**_.Use Case 19: Must labs have at least one sampler associated with them? **No, it's OK to have a lab with no samplers.**
  1. Use Case 19: Note the update condition (sampler can move between contractors). **No idea why I wrote this condition, should be removed.(Jane)**
  1. _**Closed**_.Use Case 19: Note there is a confirmation before submission. **I'll add this. (Jane)**
  1. Use Case 20: Sampling frequency form same as "update sampler"? **Should be separate forms.  In our last meeting it was going to be one form, have you changed your mind?  It will be easier as a separate form. (Jane)**
  1. Use Case 23: Note the various users and their different uses.
  1. NaN is visible on spreadsheets and reports but represents NULL in database. "<X.XXX" or ">X.XXX" is for out of range numbers.
  1. Need to change SRS Sample Reports?
  1. _**Closed**_. Project plan needs dev tools update.
  1. _**Closed**_. Project plan SOW should have days > 6 translated to weeks.
  1. Think of other specific functionality to help user experience, minimize validation errors, etc.
  1. Who can view pictures/video? **Only Contractors and EOs should**
  1. Change project plan to reflect the minutes are done by Nicholas.
  1. Page transitions (successView, cancelView, etc)