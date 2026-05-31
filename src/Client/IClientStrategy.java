package Client;

import java.io.InputStream;
import java.io.OutputStream;

// Strategy pattern interface for different client behaviors
public interface IClientStrategy {
    // Implementation will handle the custom I/O logic with the server
    void clientStrategy(InputStream inFromServer, OutputStream outToServer);
}
