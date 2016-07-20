package even.rrs.animation;

import even.rrs.render.Flag;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaderJDOMFactory;
import org.jdom2.input.sax.XMLReaderXSDFactory;


/**
 *
 * @author even
 */
public class XmlParser
{
    public static final String DEFAULT_DIR = "xml";
    public static final File ANIMATION_XSD_FILE = new File("xml", "animation.xsd");

    private ArrayList<Record> history;

    public XmlParser() {
        history = new ArrayList<>();
    }

    /**
     * Parse and validate an xml file
     *
     * @param xmlFile the path to the xml file to be parsed
     * @param xsdFile the path to the xsd scheme, which the xml file will be
     * validated against
     * @return a Document (JDom model) representation of the xml file contents
     */
    public Document parseXmlFile(File xmlFile, File xsdFile) {
        try {
            XMLReaderJDOMFactory factory2 = new XMLReaderXSDFactory(xsdFile);
            SAXBuilder sb2 = new SAXBuilder(factory2);
            sb2.setIgnoringBoundaryWhitespace(true);
            sb2.setIgnoringElementContentWhitespace(true);
            assert sb2.isValidating();
            Document doc2 = sb2.build(xmlFile);
            return doc2;
        }
        catch (JDOMException | IOException jde) {
            jde.printStackTrace();
        }
        return null;
    }

    public String getStringValue(Element elt, String attr) {
        return elt.getAttributeValue(attr);
    }

    public int getIntValue(Element elt, String attr) {
        String sval = elt.getAttributeValue(attr);
        int ival = Integer.valueOf(sval);
        return ival;
    }

    public double getDoubleValue(Element elt, String attr) {
        try {
            String sval = elt.getAttributeValue(attr);
            if (null == sval) return Double.NaN;
            double dval = Double.valueOf(sval);
            return dval;
        }
        catch (NumberFormatException nfe) {
            nfe.printStackTrace();
            return Double.NaN;
        }
    }

    public Color getColourValue(Element elt, String attr) {
        String sval = elt.getAttributeValue(attr);
        if (null == sval) return Color.PINK;
        try {
            int rgb = Integer.parseInt(sval, 16);
            Color c = new Color(rgb);
            return c;
        }
        catch (Exception e) {
            return Color.PINK;
        }
    }

    public Flag getFlagValue(Element elt, String attr) {
        String sval = elt.getAttributeValue(attr);
        if (null == sval) return null;

        Flag f = Flag.valueOf(sval);
        return f;
    }

    /**
     * History keeping version...
     */
    public Document parseXmlFile(File xmlFile, File xsdFile, String key) {
        Document doc = parseXmlFile(xmlFile, xsdFile);
        if (null == key) return doc;

        Record record = getHistory(key);
        if (record == null) {
            record = new Record();
            history.add(record);
        }
        record.xmlFile = xmlFile;
        record.xsdFile = xsdFile;
        record.key = key;
        return doc;
    }

    public Document parseXmlFile(String filename, String xsdFilename, String key) {
        File xmlFile = new File(DEFAULT_DIR, filename);
        File xsdFile = new File(DEFAULT_DIR, xsdFilename);
        return parseXmlFile(xmlFile, xsdFile, key);
    }

    public Document parseXmlFileAgain(String key) {
        Record record = getHistory(key);
        if (record == null) return null;

        return parseXmlFile(record.xmlFile, record.xsdFile);
    }

    private Record getHistory(String key) {
        for (Record rec : history) {
            if (rec.key.equals(key)) {
                return rec;
            }
        }
        return null;

    }


    private static class Record
    {
        File xmlFile;
        File xsdFile;
        String key;
    }

}
