# Fall2016Swe573_healthTracker

Bogazici University, SWE 573 Fall 2016 project.
In this project, I will develop a web application solving needs described in the given requirements document:[Project Description](https://github.com/Mephala/Fall2016Swe573_healthTracker/blob/master/SWE573_projectdescription.pdf).

**Project Architecture**

As elicitation of the requirements becomes more clear, I will update [readme file](https://github.com/Mephala/Fall2016Swe573_healthTracker/edit/master/README.md) of my project.

* Server will be written in *Java* as a deployable WAR application, to be deployed on Tomcat Web Container.
* Client-side will be a fully functional html page, client side scripting will be done in *Javascript*.
* As database, I will use *Mysql*.
* For frameworks [Apache Maven](https://maven.apache.org/), [Spring Framework](https://projects.spring.io/spring-framework/) and [Hibernate ORM](http://hibernate.org/) will be used. Additional libraries may be found in the [pom.xml](https://github.com/Mephala/Fall2016Swe573_healthTracker/blob/master/pom.xml) file.
* For issue tracking, I will constantly update [My Issue Repository](https://github.com/Mephala/Fall2016Swe573_healthTracker/issues)


**Issue Tracking**

Issue tracking will be handled through [My Issue Repository](https://github.com/Mephala/Fall2016Swe573_healthTracker/issues) , I determined issue tags and color codes for issues. Here you can find the basic guidelines of issue categorization:

* Research : Issues regarding to "How to solve project requirements" are handled with this label. Requirements document: [Project Description](https://github.com/Mephala/Fall2016Swe573_healthTracker/blob/master/SWE573_projectdescription.pdf)
* Back-end : When a new feature is needed to be implemented in the back-end, regarding to server programming or Database change.
* bug      : Whenever I notice a bug, issue about a bug is going to be created. After solving root cause of the bug, regarding UI or Back-end issue will be a follow-up.
* UI       : When a new feature or a bugfix is required regarding to *user-interface*, an issue with this label is going to be created.
* TestCase : A TDD as in *Test-Driven Development* issues will be created before actually implementing a feature in the back-end.
* question : No matter how much requirement elicitation has been done, there comes a time when further clarification is required, this is when *question* tagged issue will be created.

**About Contributors**

This project will be developed by only one person , *ie individual project* , therefore all issues, bugs, requirement-engineering needs to be handled by the repository owner.

**Deployable Final Product**

Deployable final product will be available for reviewing once there is something actually *implemented*. Until this time, *demo versions* of the product's would be available in web-site link.
However, before even making a *mock-up demo*, requirements must be clarified so please wait until [requirement elicitation research issue](https://github.com/Mephala/Fall2016Swe573_healthTracker/issues/1) is closed.

First [milestome deliverable](https://github.com/Mephala/Fall2016Swe573_healthTracker/milestone/2) is deployed on [home server](http://46.196.100.145/healthTracker/).


**Project Wiki**

As project begins to get into shape, [project wiki page](https://github.com/Mephala/Fall2016Swe573_healthTracker/wiki) will be showing directives how to use and get best from the web-application.

**How to Run this project**

As this is a completely open-source project, anyone can run this project. But before, you need [Mysql Server](https://dev.mysql.com/downloads/mysql/) installed on your system.
[Hibernate ORM](http://hibernate.org/) framework handles DBA relations, however project requires an active **Mysql User** with *read-write-update* privileges.

*Import* [pom.xml](https://github.com/Mephala/Fall2016Swe573_healthTracker/blob/master/pom.xml) with *Maven* and project is all set-up.
To actually run the server, you should either give *maven command* below:

`clean install tomcat7:run`

In order to get *WAR* deployable for seperate *Tomcat* container, you can just *build* the project and get *WAR* file created from *target* folder, which is automatically created by *Maven*.

For only building project, without calling embedded *Tomcat:RUN* command:

`clean install`

**What have I been doing so far**

I will be logging everything I have done for the success of this project, can be seen in my [Personal Log](https://github.com/Mephala/Fall2016Swe573_healthTracker/blob/master/PersonalLog.md)

It will be updated constantly and you can catch-up the newest stuff I have added.







