package dsa;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Tool {

    public static byte[] getbytes(String path) throws IOException {

        File file=new File(path);
        FileInputStream fin=new FileInputStream(file);
        byte[] output=new byte[fin.available()];
        fin.read(output);
        return output;
    }
}
