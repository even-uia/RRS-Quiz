package even.rrsquiz.parser.tagparsers;

import even.rrsquiz.parser.ProblemContentHandler;
import org.xml.sax.Attributes;

/**
 *
 * @author even
 */
public abstract class ContentParser implements TagParser
{

    ProblemContentHandler contentHandler;
    int start, end;
    StringBuilder buf;

    public ContentParser(ProblemContentHandler handler) {
        this.contentHandler = handler;
        this.buf = new StringBuilder();
    }

    public void handleStart(Attributes atts) {
        start = contentHandler.buf.length();
    }

    public void handleEnd() {
        String content = "";
        end = contentHandler.buf.length();
        if (end > start) {
            content = contentHandler.buf.substring(start, end);
        }
        contentHandler.buf.delete(start, end);
        storeContent(content);
    }

    public abstract void storeContent(String content);
}
