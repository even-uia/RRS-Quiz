package even.rrsquiz.parser;

import even.rrsquiz.Problem;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 *
 * @author even
 */
public class Parser
{

    public boolean ready;
    File f;
    BufferedInputStream input;

    public Parser(File f) {
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

    final public Problem parse() throws ParserConfigurationException,
                                        SAXException,
                                        IOException {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        SAXParser saxParser = spf.newSAXParser();
        XMLReader xmlReader = saxParser.getXMLReader();
        ProblemContentHandler handler = new ProblemContentHandler();
        xmlReader.setContentHandler(handler);
        xmlReader.parse(new InputSource(new InputStreamReader(input)));
        handler.problem.setFile(f);
        return handler.problem;
    }
}
