package even.rrs.xml.parsers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;


/**
 * Generic xml parser. It needs a content handler to work, and the content
 * handler may need a number of tag handlers
 *
 * @author even
 */
public class XmlParser<E>
{

    public boolean ready;
    XMLReader xmlReader;
    AbstractDocHandler<E> handler;


    /**
     * Set up the parser in preparation for parsing
     *
     * @param handler
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    public XmlParser(AbstractDocHandler<E> handler)
            throws SAXException, ParserConfigurationException {
        ready = false;
        this.handler = handler;
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        SAXParser saxParser = spf.newSAXParser();
        xmlReader = saxParser.getXMLReader();
        xmlReader.setContentHandler(handler);
        ready = true;
    }


    /**
     * Parse file f, and return the java object that represents the root element
     *
     * @param f
     * @return
     * @throws IOException
     * @throws SAXException
     * @throws IOException
     */
    final public E parse(File f) throws IOException,
                                        SAXException,
                                        IOException {
        BufferedInputStream input = new BufferedInputStream(new FileInputStream(f));
        xmlReader.parse(new InputSource(new InputStreamReader(input)));
        return handler.getResult();
    }

}
