package even.rrsquiz;

import even.rrsquiz.parser.Parser;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author even
 */
public class RrsQuiz
{

    private ArrayList<Problem> problems;
    private File dataDir;

    public RrsQuiz(File f) {
//        this.problems = new ArrayList<Problem>();
        this.dataDir = new File("xml");
    }
}
