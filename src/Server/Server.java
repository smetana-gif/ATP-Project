package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private int port;
    private int listeningIntervalMS;
    private IServerStrategy serverStrategy;
    private ExecutorService threadPool;
    private volatile boolean stop;

    public Server(int port, int listeningIntervalMS, IServerStrategy serverStrategy) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.serverStrategy = serverStrategy;
        this.threadPool = Executors.newFixedThreadPool(10);
        this.stop = false;
    }

    public void start() {
        new Thread(() -> {
            runServer();
        }).start();
    }

    private void runServer() {

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            serverSocket.setSoTimeout(listeningIntervalMS);
            System.out.println("Server started on port " + port);
            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client connected from: " + clientSocket.getRemoteSocketAddress());
                    threadPool.execute(() -> {
                        try {
                            serverStrategy.serverStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
                            clientSocket.close();
                            System.out.println("Finished handling client.");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                } catch (SocketTimeoutException e) {
                }
            }
                threadPool.shutdown();
                System.out.println("Server stopped successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public void stop() {
            this.stop = true;
        }
    }

