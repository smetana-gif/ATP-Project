package IO;

import java.io.IOException;
import java.io.InputStream;


public class MyDecompressorInputStream extends InputStream {

    private InputStream in;

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
        for (int i = 0; i < 12; i++) {
            b[i] = (byte) in.read();
        }
        int outputIndex = 12;
        int compressedByte;

        while ((compressedByte = in.read()) != -1) {
            int cleanByte = compressedByte & 0xFF;

            for (int bit = 7; bit >= 0; bit--) {
                if (outputIndex < b.length) {
                    b[outputIndex++] = (byte) ((cleanByte >> bit) & 1);
                }
            }
        }

        return outputIndex;
    }
}