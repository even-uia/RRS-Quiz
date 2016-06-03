package even.rrsquiz.xml.parsers;

import java.util.HashMap;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;


/**
 *
 * @author even
 */
public abstract class AbstractDocHandler<E> implements ContentHandler
{

    protected HashMap<Tag, TagHandler> handlers;
    protected StringBuilder buf;
    protected E result;


    public AbstractDocHandler() {
        handlers = new HashMap<Tag, TagHandler>();
        setupHandlers();
    }


    public abstract void setupHandlers();


    public E getResult() {
        return result;
    }


    protected TagHandler getHandler(String tag) throws SAXException {
        TagHandler handler = handlers.get(tag);
        if (null == handler) {
            throw new SAXException("Missing handler for tag " + tag);
        }
        return handler;

    }


    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes atts)
            throws SAXException {
        TagHandler handler = getHandler(localName);
        handler.handleStart(atts);
    }


    @Override
    public void endElement(String uri, String localName, String qName) throws
            SAXException {
        TagHandler handler = handlers.get(localName);
        handler.handleEnd();
    }


    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (buf != null)
            buf.append(ch, start, length);

    }


    @Override
    public void setDocumentLocator(Locator locator) {
    }


    @Override
    public void startDocument() throws SAXException {
    }


    @Override
    public void endDocument() throws SAXException {
    }


    @Override
    public void startPrefixMapping(String prefix, String uri) throws SAXException {
    }


    @Override
    public void endPrefixMapping(String prefix) throws SAXException {
    }


    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
    }


    @Override
    public void processingInstruction(String target, String data) throws SAXException {
    }


    @Override
    public void skippedEntity(String name) throws SAXException {
    }

}
