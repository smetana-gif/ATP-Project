package IO;

import java.io.IOException;
import java.io.OutputStream;

public class SimpleCompressorOutputStream extends OutputStream {

    private  OutputStream out;

    public SimpleCompressorOutputStream(OutputStream out) {

        this.out = out;
    }

    @Override
    public void write(int b) throws IOException {
        out.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        if (b == null || b.length == 0) return;
        for (int i = 0; i < 12; i++) {
            out.write(b[i]);
        }

        byte currentTarget = 0;
        int counter = 0;


        for (int i = 12; i < b.length; i++) {
            if (b[i] == currentTarget) {
                counter++;
                if (counter == 255) {
                    out.write(255);
                    out.write(0);
                    counter = 0;
                }
            } else {

                out.write(counter);
                currentTarget = b[i];
                counter = 1;
            }
        }
        if (counter > 0) {
            out.write(counter);
        }
    }
}
