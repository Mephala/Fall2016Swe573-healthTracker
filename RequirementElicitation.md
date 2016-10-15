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

