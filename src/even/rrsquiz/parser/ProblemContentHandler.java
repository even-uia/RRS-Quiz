package even.rrsquiz.parser;

import even.rrsquiz.animation.Animation;
import even.rrsquiz.animation.boat.Sailboat;
import even.rrsquiz.animation.boat.StartBoat;
import even.rrsquiz.Problem;
import even.rrsquiz.Response;
import even.rrsquiz.parser.tagparsers.ActionsParser;
import even.rrsquiz.parser.tagparsers.AnimationParser;
import even.rrsquiz.parser.tagparsers.ResponseParser;
import even.rrsquiz.parser.tagparsers.CommentParser;
import even.rrsquiz.parser.tagparsers.DescriptionParser;
import even.rrsquiz.parser.tagparsers.GybeParser;
import even.rrsquiz.parser.tagparsers.HailParser;
import even.rrsquiz.parser.tagparsers.HullParser;
import even.rrsquiz.parser.tagparsers.IntroParser;
import even.rrsquiz.parser.tagparsers.MarkParser;
import even.rrsquiz.parser.tagparsers.NavDataParser;
import even.rrsquiz.parser.tagparsers.ProblemParser;
import even.rrsquiz.parser.tagparsers.QuestionParser;
import even.rrsquiz.parser.tagparsers.SailboatParser;
import even.rrsquiz.parser.tagparsers.SignalParser;
import even.rrsquiz.parser.tagparsers.SizeParser;
import even.rrsquiz.parser.tagparsers.StartBoatParser;
import even.rrsquiz.parser.tagparsers.TackParser;
import even.rrsquiz.parser.tagparsers.TagParser;
import even.rrsquiz.parser.tagparsers.TrimParser;
import even.rrsquiz.parser.tagparsers.TurnParser;
import even.rrsquiz.parser.tagparsers.WindParser;
import java.util.HashMap;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

/**
 *
 * @author even
 */
public class ProblemContentHandler implements ContentHandler
{

    public String intro;

    public Problem problem;
    public Response response;
    public String boatId;
    public Animation animation;
    public Sailboat sailBoat;
    public StartBoat startBoat;

    public StringBuilder buf;
    private HashMap<Tag, TagParser> handlers;

    public ProblemContentHandler() {
        problem = new Problem();

        handlers = new HashMap<>();
        buf = new StringBuilder();

        handlers.put(Tag.actions, new ActionsParser(this));
        handlers.put(Tag.animation, new AnimationParser(this));
        handlers.put(Tag.comment, new CommentParser(this));
        handlers.put(Tag.description, new DescriptionParser(this));
        handlers.put(Tag.gybe, new GybeParser(this));
        handlers.put(Tag.hail, new HailParser(this));
        handlers.put(Tag.hull, new HullParser(this));
        handlers.put(Tag.intro, new IntroParser(this));
        handlers.put(Tag.mark, new MarkParser(this));
        handlers.put(Tag.navdata, new NavDataParser(this));
        handlers.put(Tag.problem, new ProblemParser(this));
        handlers.put(Tag.question, new QuestionParser(this));
        handlers.put(Tag.response, new ResponseParser(this));
        handlers.put(Tag.sailboat, new SailboatParser(this));
        handlers.put(Tag.signal, new SignalParser(this));
        handlers.put(Tag.size, new SizeParser(this));
        handlers.put(Tag.startboat, new StartBoatParser(this));
        handlers.put(Tag.tack, new TackParser(this));
        handlers.put(Tag.trim, new TrimParser(this));
        handlers.put(Tag.turn, new TurnParser(this));
        handlers.put(Tag.wind, new WindParser(this));
    }

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes atts)
            throws SAXException {
        Tag t = Tag.valueOf(localName);
        TagParser handler = handlers.get(t);
        if (null != handler) {
            handler.handleStart(atts);
        }
        else
            throw new SAXException("No handler for tag " + t);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws
            SAXException {
        Tag t = Tag.valueOf(localName);
        TagParser handler = handlers.get(t);
        if (null != handler) {
            handler.handleEnd();
        }
        else
            throw new SAXException("No handler for tag " + t);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (buf != null)
            buf.append(ch, start, length);
    }

//    private void startBoat(Attributes atts) {
//    }
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
