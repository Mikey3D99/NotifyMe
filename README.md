

# NotifyMe
Client-Server notification app, written in Java

 
## Goals for this project:

### Client:

####  Client side app sends and receives notifications for the server to handle :white_check_mark:
####  Client side app has verification of data passed to server :white_check_mark:
####  Client side app shows the notification at the correct time :white_check_mark:

### Server:

####  Server side app handles multiple clients (multithreading) :white_check_mark:
####  Server side app queues notifications :white_check_mark:
####  Server side app sends notifications in the correct order :white_check_mark:
####  Server side app verifies data passed bu the client :white_check_mark:

## Example usage:

#### 1. Run the server, server is listening to incoming connections...
![C_S_1](https://user-images.githubusercontent.com/78366670/117467388-edb2da80-af53-11eb-8e5b-edcce287912a.png)

#### 2. Run the client application and send your notfication / notifications
![C_S_2](https://user-images.githubusercontent.com/78366670/117467885-674ac880-af54-11eb-82bf-ed555e9728e7.png)

#### 3. Now the client waits for the notification to be sent from the server

#### 4. Client receives the notification!
![C_S_3](https://user-images.githubusercontent.com/78366670/117468132-abd66400-af54-11eb-84a9-9e4e84aefa83.png)

#### 5. You can run multiple client app instances at a time! 
![C_S_4](https://user-images.githubusercontent.com/78366670/117468616-269f7f00-af55-11eb-8c47-c078dd4f0702.png)



