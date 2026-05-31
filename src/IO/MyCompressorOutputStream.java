package IO;

import java.io.IOException;
import java.io.OutputStream;

//Class which implements using decorator pattern and extends OutputStream
public class MyCompressorOutputStream extends OutputStream {

    private OutputStream out;

    // Constructor saves the wrapped output stream
    public MyCompressorOutputStream(OutputStream out) {

        this.out = out;
    }

    // Writes a single byte using the wrapped stream
    @Override
    public void write(int b) throws IOException {
        out.write(b);
    }

    // Handles the byte array writing and using compression
    @Override
    public void write(byte[] b) throws IOException {

        if (b == null || b.length == 0) return;
        //The first 12 bytes are the maze meta-data written as is
        for (int i = 0; i < 12; i++) {
            out.write(b[i]);
        }

        // Compress the remaining maze body starting from index 12
        if (b.length > 12) {
            compress(b, 12);
        }
    }

    //compression logic using Bit-Packing.Takes 8 consecutive maze cells (0s and 1s) from the uncompressed byte array
    // and packs them into a single 8-bit byte to minimize file size.

    private void compress(byte[] b, int startIndex) throws IOException {
        byte currentByte = 0;
        int bitCounter = 0;
        for (int i = startIndex; i < b.length; i++) {

            // Pack 8 maze cells (0 or 1) into a single byte using bitwise operations
            currentByte = (byte) ((currentByte << 1) | (b[i] & 1));
            bitCounter++;

            // If byte is full (8 bits), write it and reset counters
            if (bitCounter == 8) {
                out.write(currentByte);
                currentByte = 0;
                bitCounter = 0;
            }
        }

        if (bitCounter > 0) {
            currentByte = (byte) (currentByte << (8 - bitCounter));
            out.write(currentByte);
        }
    }
}


