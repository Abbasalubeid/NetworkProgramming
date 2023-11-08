# HTTP GUESSING  GAME

## HTTP

HTTP, or HyperText Transfer Protocol, is a foundational protocol of the web, essential for the transfer of data and media across the internet. It operates through a request-response model at the application layer

This project is designed as a practical study of the HTTP protocol by implementing a multithreaded server. The focus is to understand how HTTP works, including its request and response structures, headers, and the role of cookies in adding state to stateless HTTP transactions. The server allows clients to play a guessing game, managing state across multiple HTTP requests using cookies.


## Project Structure

- **MVC Architecture:** This projects follows the Model-View-Controller (MVC) pattern for better separation of concerns:
  - **Model:** Manages the game state and logic.
  - **View:** Generates HTML content for client interaction.
  - **Controller:** Processes requests and coordinates between the View and Model.


### Files

- `controller/HttpRequestHandler.java`: Parses HTTP requests, interacts with `GameSession` to process game logic, and uses `HttpResponseBuilder` to create responses.
- `model/GameSession.java`: Represents a client's game session, keeping track of the guessing game state and the client's session ID.
- `view/HttpResponseBuilder.java`: Provides methods for creating the HTTP response with the appropriate HTML content.
- `MainServer.java`: Initializes the server on a specified port and listens for incoming HTTP requests to handle them concurrently.
- `GameClient.java`: Simulates a client making HTTP requests to the server, makes it possible to play the guessing game programmatically.

## Usage

### Compiling and Running the Server

1. Navigate to the `src` directory in your terminal.
2. Compile all the Java files using the `javac` command:
   ```
   javac controller/HttpRequestHandler.java view/HttpResponseBuilder.java model/GameSession.java MainServer.java
   ```
3. Start the server by running the `MainServer` class:
   ```
   java MainServer
   ```
4. With the server running, open a web browser and go to `http://localhost:8080` to play the guessing game.
5. The server terminal will display HTTP requests as they are received and will provide real-time feedback including the request line, path, query parameters, and session handling details.

### Using the GameClient to Simulate a Web Browser

1. Open a new terminal and navigate to the `src` directory.
2. Compile the `GameClient.java`:
   ```
   javac GameClient.java
   ```
3. Run the `GameClient` by specifying how many times you want to simulate a web browser session:
   ```
   java GameClient
   ```
4. Enter the desired number of game sessions for the `GameClient` to play and hit `Enter`.
5. On the server terminal, you can see the `GameClient` sending requests to the server and the server responding accordingly.

