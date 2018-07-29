# URLShortener

A web application to shorten your large url

#### Version 1.0

* Facility to shorten your large url (if big enough)
* A Url Table that shows you all the urls shortened 
* Related site hits on that Url
* An expiration period (40 days) after which URL will expire and you'll have to shorten it again

### Prerequisites

* Java 8
* Mysql
* Jboss 

### Technologies used

* Java => Resteasy, JDBC, Websocket
* Javascript, jsp, bootstrap 


### Installing

Clone your repository from github

Find the database script file in 'Scripts' folder in repository and run that file in mysql

Import the cloned project in eclipse (preferred) or other IDE

Setup your jboss server and add project to server

Run the server and go to 'http://localhost:8080/UrlShortening'. Enjoy!

![1](https://user-images.githubusercontent.com/26091655/43366083-bcaaa26e-9350-11e8-8f8f-188671db6487.png)
Enter your long URL 

![2](https://user-images.githubusercontent.com/26091655/43366084-bcde2b52-9350-11e8-9049-e075c91410e9.png)
Press the button and Wollaa!!, copy the link and enjoy.

![4](https://user-images.githubusercontent.com/26091655/43366085-bd0e7848-9350-11e8-8509-096b83eea537.png)
You can also see analytics for your url

## Built With

* [Resteasy](http://www.dropwizard.io/1.0.2/docs/) - The Api framework used
* [Maven](https://maven.apache.org/) - Dependency Management

## Contributing

Feel free to contribute to this project. We always appreciate help.

## Authors

* **Syed Muhammad Oan** - *All work uptil now* - [Me](https://github.com/smoan22)

No contributers yet who have participated in this project.

## Bugs and Feedback

For bugs, questions and discussions please use the Github Issues.

## Acknowledgments

* To anyone who wants to contribute to improving this project

