package IO;

import java.io.IOException;
import java.io.OutputStream;

public class MyCompressorOutputStream extends OutputStream {

    private OutputStream out;

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

        for (int i = 0; i <12; i++) {
            out.write(b[i]);
        }
        byte currentByte = 0;
        int bitCounter = 0;

            for (int i = 12; i < b.length ; i++) {

                currentByte = (byte) ((currentByte << 1) | (b[i] & 1));
                bitCounter++;

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

