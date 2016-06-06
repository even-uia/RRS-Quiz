package even.rulequiz.boatparser;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 *
 * @author even
 */
public class BoatParser
{

    private boolean ready;
    private BufferedInputStream input;

    public BoatParser(File f) {
        ready = false;
        try {
            input = new BufferedInputStream(new FileInputStream(f));
            ready = true;
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
            input = null;
        }
    }
}
