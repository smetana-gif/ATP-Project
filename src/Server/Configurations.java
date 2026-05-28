package Server;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configurations {

    private static Configurations instance = null;
    private Properties properties;

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

    public static Configurations getInstance() {
        if (instance == null) {
            instance = new Configurations();
        }
        return instance;
    }

    public int getThreadPoolSize() {
        return Integer.parseInt(properties.getProperty("threadPoolSize", "5"));
    }

    public String getMazeGeneratingAlgorithm() {
        return properties.getProperty("mazeGeneratingAlgorithm", "MyMazeGenerator");
    }

    public String getMazeSearchingAlgorithm() {
        return properties.getProperty("mazeSearchingAlgorithm", "BestFirstSearch");
    }
}