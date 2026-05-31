package IO;

import java.io.IOException;
import java.io.InputStream;

// Function responsible for decompressing data from an input stream back into a byte array representing a maze
public class MyDecompressorInputStream extends InputStream {

    private InputStream in;
    // Constructor that receives the input stream object to read from
    public MyDecompressorInputStream(InputStream in) {

        this.in = in;
    }

    @Override
    public int read() throws IOException {

        return in.read();
    }

    @Override
    public int read(byte[] b) throws IOException {
        if (b == null || b.length == 0) {
            return -1;
        }
        // The first 12 bytes are the maze meta-data, read and copied exactly as they are
        for (int i = 0; i < 12; i++) {
            b[i] = (byte) in.read();
        }
        //Starting to fill the maze body from index 12 in the destination array
        int outputIndex = 12;
        int compressedByte;
        // Read compressed bytes from the input stream until the end of the stream or until the destination array is full
        while ((compressedByte = in.read()) != -1) {
            //convert the signed byte to an unsigned integer (prevents sign extension issues in Java)
            int cleanByte = compressedByte & 0xFF;
            // Extract each bit from MSB (bit 7, left) to LSB (bit 0, right) to reconstruct the original maze cells
            for (int bit = 7; bit >= 0; bit--) {
                if (outputIndex < b.length) {
                    //shift to the LSB and &1 takes the original cell value of 0 or 1.
                    b[outputIndex++] = (byte) ((cleanByte >> bit) & 1);
                }
            }
        }

        return outputIndex;
    }
}