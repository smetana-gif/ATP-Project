package Server;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

// Singleton class to load and manage server configurations from the properties file
public class Configurations {

    private static Configurations instance = null;
    private Properties properties;

    // Private constructor loads the config file or sets defaults if not found
    private Configurations() {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("unable to find config.properties, using defaults.");
                properties.setProperty("threadPoolSize", "5");
                properties.setProperty("mazeGeneratingAlgorithm", "MyMazeGenerator");
                properties.setProperty("mazeSearchingAlgorithm", "BestFirstSearch");
            } else {
                properties.load(input);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    // Global access point for the Singleton instance
    public static Configurations getInstance() {
        if (instance == null) {
            instance = new Configurations();
        }
        return instance;
    }
    // Returns the thread pool size (defaults to 5)
    public int getThreadPoolSize() {
        return Integer.parseInt(properties.getProperty("threadPoolSize", "5"));
    }
    // Returns the selected maze generation algorithm
    public String getMazeGeneratingAlgorithm() {
        return properties.getProperty("mazeGeneratingAlgorithm", "MyMazeGenerator");
    }
    // Returns the selected maze solving algorithm
    public String getMazeSearchingAlgorithm() {
        return properties.getProperty("mazeSearchingAlgorithm", "BestFirstSearch");
    }
}