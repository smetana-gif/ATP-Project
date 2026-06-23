package IO;

import java.io.IOException;
import java.io.InputStream;

public class SimpleDecompressorInputStream extends InputStream {

    private InputStream in;

    public SimpleDecompressorInputStream(InputStream in){

        this.in = in;
    }

    @Override
    public int read() throws IOException {
        return in.read();
    }

    @Override
    public int read(byte[] b) throws IOException {
        if (b == null || b.length == 0) return -1;
        for (int i = 0; i < 12; i++) {
            b[i] = (byte) in.read();
        }
        int outputIndex = 12;
        byte currentSignal = 0;
        int compressedByte;

        while ((compressedByte = in.read()) != -1) {
            int count = compressedByte & 0xFF;
            for (int i = 0; i < count; i++) {
                if (outputIndex < b.length) {
                    b[outputIndex++] = currentSignal;
                }
            }
            currentSignal = (byte) (1 - currentSignal);
        }
        return outputIndex;
    }
}
