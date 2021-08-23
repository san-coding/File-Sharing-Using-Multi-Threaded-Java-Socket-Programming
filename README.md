# File-Sharing-Using-Multi-Threaded-Java-Socket-Programming
Multiple clients  send  file to the Server, Server spawns a thread for each client, updates the file required by the client and sends it back to the respective client , using Multi-Threaded Java Socket Programming

## Commands to run the Application

Open three terminals side by side, two inside the **Client** folder containing the **client.java** file and one terminal inside the **Server** folder containing  the **server.java** file.

### First run the Server
```
javac server.java
```

```
java server
```

### Run the Client
```
javac client.java
```

```
java client
```
## PROBLEM STATEMENT
Given the wines dataset, Implement a client server program.

The program should have the following functionality:

- The client program should have the facility to update the wineswith values
- Upon entering a quality(5,6,7)it should calculate the mean,median for that type        of wine

- The server program will provide the following features:

  - On entering the quality it should display lowest pH
  - Given the quality it should find highest alcohol content and write to a file called Q5.csv if the quality given is 5
  - 

## SOLUTION AND CODE WORKFLOW

- Server has started and is waiting for the client to connect.
- Lets consider a scenario where 2 clients connect to server at same time 
  (code allows multiple clients to connect)
- Client 1 connects to the server.
- Server creates object of ClientHandler class for each new client.
- Server spawns a new thread using the object of ClientHandler class for each new client.
- Server threads are waiting for the clients to send a CSV file
- Clients reads the wines.csv and stores the data into a byte array[].
- Clienta calculates and displays mean, median of pH, acidity and alcohol content for the given input quality value.
- Clients send the byte array to the server and waits for the server’s response.
- Server threads receive the byte array containing the data , sent by the respective clients.
- Server threads create their own copy of wines.csv and writes the data from the byte array to the wines.csv.
- Server threads read their copy of wines.csv line by line and calculates lowest ph and highest alcohol
- Server threads creates Q + quality value +.csv for their respective clients and , writes to it and sends it back to client

## Preview

### BOTH CLIENTS CONNECT TO THE SERVER

<img width="1263" alt="Screenshot 2021-08-23 at 12 05 34 PM" src="https://user-images.githubusercontent.com/65719940/130401551-d45d78dd-2a9d-4447-8d9f-8e5af42dbd7c.png">

### SCENARIO 1 : CLIENTS SEND FILE 1 AFTER THE OTHER
- CLIENT 1 SENDS THE  FILE TO THE SERVER , SERVER PROCESSES IT AND SENDS BACK
THE Q5.csv , SERVER WAITS FOR CLIENT 2 TO SEND THE FILE
<img width="1268" alt="Screenshot 2021-08-23 at 12 07 16 PM" src="https://user-images.githubusercontent.com/65719940/130401758-683c0d50-bd90-4109-869f-6232763fc1f3.png">

- CLIENT 2 SENDS THE  FILE TO THE SERVER , SERVER PROCESSES IT AND SENDS BACK
THE Q6.csv , SERVER WAITS FOR THE NEXT CLIENT TO CONNECT

<img width="1013" alt="Screenshot 2021-08-23 at 12 09 47 PM" src="https://user-images.githubusercontent.com/65719940/130402047-e7aaa325-28b6-433b-91a4-d9d05ded42cc.png">

### SCENARIO 2 : BOTH CLIENTS SEND THE FILE TOGETHER

- Both clients connect to the server
- Client 1 enters the quality on client side and sends the file to server
- Before server can send the Q5.csv, client 2 enters the quality on client side and sends the file to the server
- Server processes and sends back Q5.csv to client 1.
- Server then processes and sends back Q6.csv to client 2.
- Server waits for the next client to connect.

<img width="1015" alt="Screenshot 2021-08-23 at 12 12 18 PM" src="https://user-images.githubusercontent.com/65719940/130402313-17a5cd50-3f8f-49f9-ab3a-31e09c751b7c.png">


