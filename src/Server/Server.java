package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Multithreaded server that handles clients using a thread pool and a specific strategy
public class Server {

    private int port;
    private int listeningIntervalMS;
    private IServerStrategy serverStrategy;
    private ExecutorService threadPool;
    private volatile boolean stop;

    // Constructor initializes server settings and creates the fixed thread pool from configurations.
    public Server(int port, int listeningIntervalMS, IServerStrategy serverStrategy) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.serverStrategy = serverStrategy;
        int threadPoolSize = Configurations.getInstance().getThreadPoolSize();
        this.threadPool = Executors.newFixedThreadPool(threadPoolSize);
        this.stop = false;
    }

    // Starts the server main loop in a separate thread so it doesn't block the main program
    public void start() {
        new Thread(() -> {
            runServer();
        }).start();
    }
    // Main server loop - listens for clients and sends them to the thread pool
    private void runServer() {

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            // Timeout stops accept() from blocking forever, so we can check the 'stop' flag
            serverSocket.setSoTimeout(listeningIntervalMS);
            System.out.println("Server started on port " + port);
            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client connected from: " + clientSocket.getRemoteSocketAddress());
                    // Hand over the client handling to a thread from the pool
                    threadPool.execute(() -> {
                        try {
                            // Run the chosen strategy -Supports SOLID open-closed principle - if I want the server will do
                            //something else, I just add new a class who implement the interface instead changing the code to if else statements.
                            serverStrategy.serverStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
                            clientSocket.close();
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
    // Changes the flag to stop the server loop safely
        public void stop() {
            this.stop = true;
        }
    }

