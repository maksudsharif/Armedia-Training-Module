# Armedia Alfresco Training Module
 
This is part of a training module for Alfresco development. This repository will contain a repo and share amp project for Alfresco 5.0.2.1. Concepts discussed include: 

  - Content Models
  - Behaviors
  - Workflows
  - Actions

## Required pre-requisites

Things you should be familiar with include:

* [Spring Framework] - Spring helps development teams everywhere build simple, portable, fast and flexible JVM-based systems and applications.
* [HTTP/RESTful Web Services] - an architectural style for networked hypermedia applications
* [MVC / MVW] - Model View Controller/Whatever design paradigm
* [Spring MVC] - Spring flavor of MVC
* [Freemarker Template Language] - templating engine similiar to Velocity/Thymleaf
* [AJAX] - a web development technique for creating interactive web applications
* [JSON] - standard format to transmit data objects
* [Maven] - awesome build automation tool
* [ECM] - Enterprise Content Management
* [CMIS] - Standard for interoperability with content management systems

  

## Quick Start Guide
Software you'll need: 
* Maven - <https://maven.apache.org/download.cgi>
* Java 8 - <http://www.oracle.com/technetwork/java/javase/downloads/index.html>
 
For training assignment:
* Clone [Armedia Training Module]
* Import into IDE as a Maven Project
* Work through tutorials

For completed project:
* Clone [Armedia Training Demo]
* Import into IDE as a Maven Project
* Run mvn clean install integration-test -Pamp-to-war,enterprise
* Direct browser to localhost:8080/alfresco and localhost:8081/share

## Installation
For training assignments:
 * Clone [Armedia Training Module]
 * Import as Maven project into IDE
 * mvn clean install integration-test -Pamp-to-war,enterprise
 * Refer to FAQ for any issues.

For the completed project:
* Clone [Armedia Training Demo]
* Import as Maven project into IDE
* mvn clean install integration-test -Pamp-to-war,enterprise
* Create a new Site: 'FDA'
* Create user groups: COORDINATOR, ENGINEERING, OPERATIONS, MARKETING, MANAGEMENT
* Create folder 'Confiuration' in Site Document Library
* Upload included CSV file to 'Configuration'
* Create Folder 'Reports' in Site Document Library
* Create sub-folders: 'Draft', 'Pending', 'Published'
* Refer to FAQ for any issues.

## FAQ
Q: Which IDE should I use?

A: Use whatever you are comfortable with. Any IDE with proper Maven support should work.                 For this project, I used STS/Eclipse.

Q: Artifacts are not found on public?

A: Make sure that your Maven installation settings are correct with:
        - Proper user credentials for Alfresco Private Repository (.m2/settings.xml)
        Make sure to select 'enterprise' maven profile in the IDE
    
Q: Why can't I see logger/debugger statements?
    
A: Make sure your log4j.properties are correctly set up. Also make sure that you have your maven 
        command line arguments correctly setup (module.log.level=debug, etc.)

Q: My build failed!?

A: Check to the console logs for any errors. Common ones include improper maven profiles,                 settings.xml not correctly setup, another application already bound to ports 8080 and 8081.
    
Q: Where can I find the JAVADOCS?

A: [Alfresco Javadocs]
    
Q: Help, I'm stuck. Where should I go for everything else?

A: Ask your Buddy.

## Helpful Links
[Armedia Training Demo]

## Version
- 1.2.0

[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does it's job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)

   [CMIS]: <https://www.alfresco.com/cmis>
   [HTTP/RESTful Web Services]: <https://docs.oracle.com/javaee/6/tutorial/doc/gijqy.html>
   [MVC / MVW]: <https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller>
   [Spring MVC]: <http://docs.spring.io/spring-framework/docs/current/spring-framework-reference/html/mvc.html>
   [Freemarker Template Language]: <http://freemarker.incubator.apache.org/>
   [AJAX]: <http://www.tutorialspoint.com/ajax/>
   [JSON]: <https://spring.io/understanding/JSON>
   [Maven]: <https://spring.io/guides/gs/maven/>
   [Spring Framework]: <http://spring.io>
   [ECM]: <http://www.aiim.org/What-is-ECM-Enterprise-Content-Management>
   [Armedia Training Demo]: <https://github.com/maksudsharif/Alfresco-Training-Demo>
   [Armedia Training Module]: <git@github.com:maksudsharif/Armedia-Training-Module.git>
   [Alfresco Javadocs]: <http://dev.alfresco.com/resource/docs/java/>
   
   [Maven Download]: <https://maven.apache.org/download.cgi>
   [Java 8]: <http://www.oracle.com/technetwork/java/javase/downloads/index.html>
   
  


