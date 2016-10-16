# Requirement Elicitation of the Health Tracker Project

* 1\. **Authentication**: User authentication shall be done via *username* and *password* couple.
    * 1.1\. Editing profile information requires authentication. Following information is the whole section of updates that require user authentication.
      * 1.1.1\. Updating weight.
          * 1.1.1.1\. With an option to be in metric (kg) or imperial (lbs) selection.
      * 1.1.2\. Updating height.
           * 1.1.2.1\. With an option to be in metric (m) or imperial (ft) selection.
      * 1.1.3\. Updating previous food intake.
      * 1.1.4\. Updating user activity level.
      * 1.1.5\. Updating user gender.
      * 1.1.6\. Updating password.
    * 1.2\. Some information user provided during **Registration**(See: 2.x.x) is read only and can not be editable afterwards. Those are:
      * 1.2.1\. Username should be read-only.
      * 1.2.1\. Passwords should be masked and can only be changed upon **Reset Password** (See: x.x.x) request.
* 2\. **Features available to all users (registered and non-registered)** : Some features of the application should be available to all users whether they are registered or not. Those are:
   * 2.1\. Calculating *Body-Mass Index* (See: x.x.x)
   * 2.2\. Querying food-calorie and food-nutrition information, making a food-search (See x.x.x)
   * 2.3\. Querying activity -> Estimated calories information. (See: x.x.x)
   * 2.4\. Registration has no constraints. Any user shall register. (See: 3.x.x)

* 3\. **Registration** : This process should be available to all users. There should be a link on top of every page in the web application to enable user to navigate to registration interface.
   * 3.1\. **Registration Info** Registration info are gathered during registration process. Some of them are editable afterwards, some are not. (See: 1.x.x) Required informations are:
        * 3.1.2\. User weight.
        * 3.1.3\. User height.
        * 3.1.4\. User nick-name.
        * 3.1.5\. User password.
        * 3.1.6\. User gender.
        * 3.1.7\. User activity-level. Pre-defined user activity levels help calculating user basal metabolic rate which is going to be stored for further version calculations (See: x.x.x) Pre-defined activity levels are:
            * 3.1.7.1\. None (stay in bed all day)
            * 3.1.7.1\. Sedentariness (very little)
            * 3.1.7.1\. Light (1 to 3 days per week)
            * 3.1.7.1\. Moderate (3 to 5 days per week)
            * 3.1.7.1\. Hard (6 days per week)
            * 3.1.7.1\. Non-Stop (You are Energizer Bunny.)

* 4\. **User food intake**: User food intake is the interface where user can add his/her food intake with *amount* and *name* parameters. Specification of these parameters are:
    * 4.1\. **Query Paramters** : *Amount* and *Name of the food* parameters are needed.
        * 4.1.1\. **Name** parameter is **required**. The system shall search through *implemented APIs* (See: x.x.x) to find out the exact food that the user has eaten.
        * 4.1.2\. **Amount** parameter is **not-required** for some food-types, **required** for some other food-types. Completely dependant on *implemented APIs* (See: x.x.x) architecture.
    * 4.2\. **Food consume time**: Being completely **optional**, gives user an idea to see *when* he/she consumed that food. Those are the available **optional** time-stamps for the food-intake for every dish:
        * 4.2.1\. Breakfast
        * 4.2.2\. Lunch
        * 4.2.3\. Dinner
        * 4.2.4\. Snack
        * 4.2.5\. Mid-night
        * 4.2.6\. **Number of dishes for each time**: There is no limit. User can add all dishes he/she had for any specific time, ie: User may have eaten all dishes on breakfast and had eaten anything else during the day. The system should be **flexible** enough to allow user distributing dishes he/she eat.
    * 4.3\. **Food Selection API** Names of the dishes and their nutrition information is going to be fetched from [USDA Food Composition Database](https://ndb.nal.usda.gov/ndb/doc/) via REST API
    * 4.4\. **API Caching** There is no specification about caching, however some level of caching might help to reduce query times. There should be a second level cache before requesting from *USDA Food Composition Database*
    * 4.5\. **Preview Previous Food Intakes** : Users should be able to see their previously consumed foods along with their total calories. There must be graphical charts and numerical values regarding to their past activities.
    * 4.6\. **Food Intake Log**: Those logs are saved **only for registered users**. Unregistered users can only *query* to see food->nutrition analysis. (See: 4.1)
    * 4.7\. **How old the system should keep user food intakes**: All activity persistence will be determined by the requirement 6.x.x. Please refer.

* 5\. **User Activities**: *Registered users* shall be able to log their activities on daily basis or about past.
    * 5.1\. **Unregistered Users** : Those users shall be able to query activity -> calorie spent with query parameters (See: 5.3.1)
    * 5.2\. **Registered Users** : Registered users shall be able to query and *log* (See: 5.4.x) their activities.
    * 5.3\. **Querying Activities** : Users shall query an activity info with following parameters:
        * 5.3.1\. Activity Name -> Describing name of the activity, the system shall be complete the query if matching solution is found.
        * 5.3.2\. Activity Duration -> Describing the duration of the activity.
    * 5.4\. **Logging Activities** : *Registered Users* shall be able to log their previous activities. They shall query and find the activity they did and *log* it into system's database.
    * 5.5\. **Viewing Previous Activities** : *Registered Users* shall see their previous activities. How long their activities will be stored ? Please refer 6.x.x

* 6\. **Activity Persistence Interval** : This section only concerns *registered users*. Non-register user activities shall never be persisted.
    * 6.1\. **Activity Start Time** : All registered users shall be able to enter activities ( both *food consumption* and *exercise activity* See 4.x.x and 5.x.x) back until *the time they registered*.
    * 6.2\. **De-active Users** : Even if any **Registered User** stops using the application, their data will be stored until end of time. (yes, *end of time*)

* 7\. **Body-Mass Index Calculations** : All users ( both *registered* and *un-registered*) shall be able to calculate their BMI.
     * 7.1\. **BMI Calculation Method** : The System shall calculate BMI with [given method](https://en.wikipedia.org/wiki/Body_mass_index) in the [project description document](https://github.com/Mephala/Fall2016Swe573_healthTracker/blob/master/SWE573_projectdescription.pdf)
     * 7.2\. **BMI Logging** : **Registered Users** shall be able to see their BMI changes against timeline. A graph indicating changes based on the time interval shall be presented.

* 8\. **Calorie Balance Calculations** : The system shall calculate user calorie balance on daily basis and *custom* selected basis (See: 8.2.x).
     * 8.1\. **Daily Calorie Calculations** : The system shall calculate total calorie difference based on the food intake (See: 4.x.x) and exercise calorie output (See: 5.x.x)
     * 8.2\. **Custom Calorie Calculations** : User shall search calorie balance for custom timeline. For instance, user shall be able to see calorie difference for the last 4 days.
     * 8.3\. **Presenting User Calorie Balance** : *Registered Users* shall be able to see his/her calorie balance on their profile info. Ease of access is needed.





