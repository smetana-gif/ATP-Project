package Server;

import java.io.InputStream;
import java.io.OutputStream;

// Strategy pattern interface for handling different client requests on the server side, supporting SOLID open/closed principle.
public interface IServerStrategy {

    //Defines the specific logic for handling a client connection.
    void serverStrategy(InputStream inFromClient, OutputStream outToClient);
}