package IO;

import java.io.IOException;
import java.io.OutputStream;

//Function which responsible for compressing byte array representing a maze,  and writing the compressed data to output stream
public class MyCompressorOutputStream extends OutputStream {

    private OutputStream out;

//Constructor who get the output stream object to write to
    public MyCompressorOutputStream(OutputStream out) {

        this.out = out;
    }

    @Override
    public void write(int b) throws IOException {
        out.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {

        if (b == null || b.length == 0) return;
        //The first 12 bytes are the maze meta-data written as is, and the rest of the maze body compressed using bit-Packing method
        for (int i = 0; i <12; i++) {
            out.write(b[i]);
        }
        byte currentByte = 0;
        int bitCounter = 0;
        //starting from index 12 in bytes array
            for (int i = 12; i < b.length ; i++) {
                //(b[i] & 1) taking the LSB using op &, can be 0 or 1, and currentByte << 1 shift all bits one place left to create space for the new bit in the compressed new bite.
                currentByte = (byte) ((currentByte << 1) | (b[i] & 1));
                bitCounter++;
        //if the compressed byte is full (8 bits) I write it to out and start with new byte.
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

