package IO;

import java.io.IOException;
import java.io.InputStream;

//Class which implements using decorator pattern and extends inputStream
public class MyDecompressorInputStream extends InputStream {

    private InputStream in;

    // Constructor saves the wrapped input stream
    public MyDecompressorInputStream(InputStream in) {

        this.in = in;
    }

    // Reads a single byte using the wrapped stream
    @Override
    public int read() throws IOException {

        return in.read();
    }

    // Handles reading into a byte array using decompression
    @Override
    public int read(byte[] b) throws IOException {
        if (b == null || b.length == 0) {
            return -1;
        }

        // The first 12 bytes are the maze meta-data, read and copied exactly as they are
        for (int i = 0; i < 12; i++) {
            b[i] = (byte) in.read();
        }

        // Decompress the remaining maze body starting from index 12
        return decompress(b, 12);
    }

    // decompression logic - Reads compressed bytes from the stream,
    // takes each individual bit, and unpacks them back into the original 0 and 1 maze cells.

    private int decompress(byte[] b, int startIndex) throws IOException {
        int outputIndex = startIndex;
        int compressedByte;

        // Read compressed bytes from the stream until the end of the input.

        while ((compressedByte = in.read()) != -1) {
            // Convert signed byte to unsigned int.
            int cleanByte = compressedByte & 0xFF;

            // extract each bit from msb to lsb to get the original 0s and 1s
            for (int bit = 7; bit >= 0; bit--) {
                if (outputIndex < b.length) {
                    b[outputIndex++] = (byte) ((cleanByte >> bit) & 1);
                }
            }
        }

        return outputIndex;
    }
}