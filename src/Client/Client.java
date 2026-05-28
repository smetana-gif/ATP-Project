package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

    public class Client {

        private InetAddress serverIP;
        private int serverPort;
        private IClientStrategy clientStrategy;


        public Client(InetAddress serverIP, int serverPort, IClientStrategy clientStrategy) {

            this.serverIP = serverIP;
            this.serverPort = serverPort;
            this.clientStrategy = clientStrategy;
        }


        public void communicateWithServer() {

            try (Socket serverSocket = new Socket(serverIP, serverPort)) {


                InputStream inFromServer = serverSocket.getInputStream();
                OutputStream outToServer = serverSocket.getOutputStream();

                clientStrategy.clientStrategy(inFromServer, outToServer);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

