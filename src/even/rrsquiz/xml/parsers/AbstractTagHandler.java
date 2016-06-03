package even.rrsquiz.xml.parsers;

import even.rrsquiz.animation.action.TurnRate;
import even.rrsquiz.animation.boat.Flag;
import even.rrsquiz.animation.boat.HullType;
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
    public int getIntValue(Attributes atts, even.rrsquiz.parser.Attribute name, int defVal) {
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
    public double getFloatValue(Attributes atts, even.rrsquiz.parser.Attribute name, double defVal) {
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


    public Color getColourValue(Attributes atts, even.rrsquiz.parser.Attribute name, Color defVal) {
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


    public boolean getBooleanValue(Attributes atts, even.rrsquiz.parser.Attribute name, boolean defVal) {
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


    public Flag getFlagValue(Attributes atts, even.rrsquiz.parser.Attribute name,
                             Flag defval) {
        Flag flag = defval;
        String s = atts.getValue(name.toString());
        if (null != s)
            flag = Flag.valueOf(s.trim());
        return flag;
    }


    public TurnRate getTurnRateValue(Attributes atts, even.rrsquiz.parser.Attribute name, TurnRate defVal) {
        String s = atts.getValue(name.toString());
        if (null == s || s.length() == 0)
            return defVal;
        else
            return TurnRate.valueOf(s);
    }


    public HullType getHullTypeValue(Attributes atts, even.rrsquiz.parser.Attribute name, HullType defval) {
        String s = atts.getValue(name.toString());
        if (null == s || s.length() == 0)
            return defval;
        else
            return HullType.valueOf(s);
    }


    public String getStringValue(Attributes atts, even.rrsquiz.parser.Attribute name, String defVal) {
        String s = atts.getValue(name.toString());
        if (null == s || s.length() == 0)
            return defVal;
        else
            return s;
    }

}
