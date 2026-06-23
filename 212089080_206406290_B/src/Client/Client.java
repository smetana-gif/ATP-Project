package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

// The Class responsible for creating a connection with the server and executing a specific client strategy
    public class Client {

        private InetAddress serverIP;
        private int serverPort;
        private IClientStrategy clientStrategy;

    // Constructor to initialize server connection details and the specific strategy
        public Client(InetAddress serverIP, int serverPort, IClientStrategy clientStrategy) {

            this.serverIP = serverIP;
            this.serverPort = serverPort;
            this.clientStrategy = clientStrategy;
        }

    // Connects to the server using a Socket, receiving the IO streams , and applying strategy
        public void communicateWithServer() {
    //try-with-resources to ensure the Socket closes automatically when communication ends
            try (Socket serverSocket = new Socket(serverIP, serverPort)) {


                InputStream inFromServer = serverSocket.getInputStream();
                OutputStream outToServer = serverSocket.getOutputStream();

                clientStrategy.clientStrategy(inFromServer, outToServer);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

