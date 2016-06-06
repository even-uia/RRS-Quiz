package even.rrs.xml.parsers;

import java.awt.Color;
import org.xml.sax.Attributes;


/**
 *
 * @author even
 */
public abstract class AbstractTagHandler implements TagHandler
{

    public AbstractTagHandler() {
    }

    public void handleStart(Attributes atts) {
    }

    public void handleEnd() {
    }

    // attribute value converters
    /**
     * Convert an attribute value to int
     *
     * @param atts the attribute list
     * @param name the attribute name
     * @param defVal default value
     * @return
     */
    public int getIntValue(Attributes atts, Attribute name, int defVal) {
        String s = atts.getValue(name.toString());
        if (null == s || s.length() == 0) {
            return defVal;
        }
        try {
            int val = Integer.valueOf(s);
            return val;
        }
        catch (Exception e) {
            return defVal;
        }
    }

    /**
     * Convert an attribute to double
     */
    public double getFloatValue(Attributes atts, Attribute name, double defVal) {
        String s = atts.getValue(name.toString());
        if (null == s || s.length() == 0) {
            return defVal;
        }
        try {
            double val = Float.valueOf(s);
            return val;
        }
        catch (Exception e) {
            return defVal;
        }
    }

    public Color getColourValue(Attributes atts, Attribute name, Color defVal) {
        String s = atts.getValue(name.toString());
        if (null == s || s.length() == 0) {
            return defVal;
        }
        try {
            int rgb = Integer.parseInt(s, 16);
            Color c = new Color(rgb);
            return c;
        }
        catch (Exception e) {
            return defVal;
        }
    }

    public boolean getBooleanValue(Attributes atts, Attribute name, boolean defVal) {
        String s = atts.getValue(name.toString());
        if (null == s || s.length() == 0) {
            return defVal;
        }
        try {
            boolean b = Boolean.valueOf(s);
            return b;
        }
        catch (Exception e) {
            return defVal;
        }
    }

    public String getStringValue(Attributes atts, Attribute name, String defVal) {
        String s = atts.getValue(name.toString());
        if (null == s || s.length() == 0)
            return defVal;
        else
            return s;
    }

}
