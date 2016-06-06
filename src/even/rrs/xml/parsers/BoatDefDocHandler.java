package even.rrs.xml.parsers;

import even.rrs.animation.boat.BoatType;
import even.rrs.animation.boat.Hull;
import even.rrsquiz.animation.boat.PerformanceProfile;
import even.rrsquiz.animation.boat.Rig;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.xml.sax.Attributes;


/**
 * Interprets the xml tags in the boat definition file
 *
 * @author even
 */
public class BoatDefDocHandler extends AbstractDocHandler<List<BoatType>>
{
    BoatType boatType;
    Hull hull;
    Path2D.Double path;

    HashMap<String, Hull> hulls;
    HashMap<String, Rig> rigs;
    HashMap<String, PerformanceProfile> perfs;

    public BoatDefDocHandler() {
        super();
        result = new ArrayList<>();
    }

    /**
     * Set up tag handlers
     */
    public void setupHandlers() {
        handlers.put(Tag.boatdefs, new BoatDefsHandler());
        handlers.put(Tag.boattype, new BoatTypeHandler());
        handlers.put(Tag.hullref, new HullRefHandler());
        handlers.put(Tag.hull, new HullHandler());
        handlers.put(Tag.rigref, new RigRefHandler());
        handlers.put(Tag.performance, new PerfRefHandler());
        handlers.put(Tag.boattype, null);
        handlers.put(Tag.boattype, null);
        handlers.put(Tag.boattype, null);
        handlers.put(Tag.boattype, null);
        handlers.put(Tag.boattype, null);
        handlers.put(Tag.boattype, null);
        handlers.put(Tag.boattype, null);
        handlers.put(Tag.boattype, null);
        handlers.put(Tag.boattype, null);
        handlers.put(Tag.boattype, null);
        handlers.put(Tag.boattype, null);

    }

    /**
     * Handle boatdef tag
     */
    private class BoatDefsHandler extends AbstractTagHandler
    {
        @Override
        public void handleEnd() {
        }

        @Override
        public void handleStart(Attributes atts) {
            result = new ArrayList<>();
        }

    }

    /**
     * Start parsing a new boattype
     */
    private class BoatTypeHandler extends AbstractTagHandler
    {
        @Override
        public void handleEnd() {
            boatType = null;
        }

        @Override
        public void handleStart(Attributes atts) {
            boatType = new BoatType();
            String name = getStringValue(atts, Attribute.name, null);
            boatType.setName(name);

            result.add(boatType);
        }

    }

    /**
     * handle ahead references to hull defintions
     */
    private class HullRefHandler extends AbstractTagHandler
    {
        @Override
        public void handleEnd() {
        }

        @Override
        public void handleStart(Attributes atts) {
            String id = getStringValue(atts, Attribute.id, null);
            boatType.setHullRef(id);
        }

    }

    /**
     * handle hull definitions
     */
    private class HullHandler extends AbstractTagHandler
    {

        @Override
        public void handleEnd() {
            hull = null;
            path = null;
        }

        @Override
        public void handleStart(Attributes atts) {
            String id = getStringValue(atts, Attribute.id, null);
            double beam = getFloatValue(atts, Attribute.beam, 0);
            double length = getFloatValue(atts, Attribute.length, 0);
            hull = new Hull(id, beam, length);
            hulls.put(id, hull);
        }

    }

    /**
     * handle
     */
    private class StartHandler extends AbstractTagHandler
    {

        @Override
        public void handleEnd() {
        }

        @Override
        public void handleStart(Attributes atts) {
            double x = getFloatValue(atts, Attribute.x, 0);
            double y = getFloatValue(atts, Attribute.y, 0);
            path = new Path2D.Double();
            path.moveTo(x, y);
            hull.setPath(path);
        }

    }

    /**
     * handle
     */
    private class LineHandler extends AbstractTagHandler
    {

        @Override
        public void handleEnd() {
        }

        @Override
        public void handleStart(Attributes atts) {
            double x = getFloatValue(atts, Attribute.x, 0);
            double y = getFloatValue(atts, Attribute.y, 0);
            path.lineTo(x, y);
        }

    }

    /**
     * handle ahead references to rig definitions
     */
    private class RigRefHandler extends AbstractTagHandler
    {
        @Override
        public void handleEnd() {
        }

        @Override
        public void handleStart(Attributes atts) {
            String id = getStringValue(atts, Attribute.id, null);
            boatType.setRigRef(id);
        }

    }

    /**
     * handle ahead references to rig definitions
     */
    private class PerfRefHandler extends AbstractTagHandler
    {

        @Override
        public void handleEnd() {
        }

        @Override
        public void handleStart(Attributes atts) {
            String id = getStringValue(atts, Attribute.id, null);
            boatType.setPerfRef(id);
        }

    }

    /**
     * handle
     */
    private class AbstHandler extends AbstractTagHandler
    {

        @Override
        public void handleEnd() {
        }

        @Override
        public void handleStart(Attributes atts) {
            result = new ArrayList<>();
        }

    }

    /**
     * handle
     */
    private class Handler extends AbstractTagHandler
    {

        @Override
        public void handleEnd() {
        }

        @Override
        public void handleStart(Attributes atts) {
            result = new ArrayList<>();
        }

    }

}
