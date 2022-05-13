# RaveWave

## Introduction

By combining Kahoot with Spotify, RaveWave creates a competitive and fun game to play among friends. This is the 
repository of the back-end part of our implementation, you'll find the front-end part [here](https://github.com/sopra-fs22-group-17/RaveWave-client).

## Technologies

The back-end is written in Java and uses Spring Boot framework. We use JPA for persistence and the deployment is 
handled by Heroku. Communication between the server and the client is done with REST and websockets. For the 
websockets an additional messaging protocol called STOMP is used. 

##Spotify Web API

This game relies heavily on the Spotify Web API, which is based on REST principles. It gives access to songs and their 
metadata, as well as Spotify user related information.You can find more information about the API [here](https://developer.spotify.com/documentation/web-api/).

##Members of the SoPra group

SoPra Group 17 2022 consists of [Marco Leder](https://github.com/marcoleder), [Valentin Hollenstein](https://github.com/v4lentin1879), [Dennys Huber](https://github.com/devnnys), [Sheena Lang](https://github.com/SheenaGit) and [Isabella Chesney](https://github.com/bellachesney). 

## TODOS
- [] Duplicate Songs, when fetching from multiple Users
- [] Songlist empty -> ex. no liked songs