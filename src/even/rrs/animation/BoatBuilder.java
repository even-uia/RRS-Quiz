package even.rrs.animation;

import even.rrs.animation.boatdef.BoatType;
import even.rrs.animation.boatdef.Hull;
import even.rrs.animation.boatdef.Rig;
import even.rrs.animation.boatdef.Sail;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Path2D;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaderJDOMFactory;
import org.jdom2.input.sax.XMLReaderXSDFactory;



/**
 *
 * @author even
 */
public class BoatBuilder
{


    public static final String BEAM = "beam";
    public static final String BOATDEFS = "boatdefs";
    public static final String BOATTYPE = "boattype";

    public static final String CAMBER = "camber";
    public static final String CHORD = "chord";
    public static final String CX1 = "cx1";
    public static final String CX2 = "cx2";
    public static final String CY1 = "cy1";
    public static final String CY2 = "cy2";

    public static final String DATA = "data";

    public static final String FORESTAYPOS = "forestaypos";

    public static final String HULL = "hull";
    public static final String HULLREF = "hullref";

    public static final String ID = "id";

    public static final String LEN = "len";
    public static final String LENGTH = "length";
    public static final String LINE = "line";

    public static final String MASTPOS = "mastpos";
    public static final String MAXANGLE = "maxangle";
    public static final String MINANGLE = "minangle";
    public static final String MINTWA = "mintwa";

    public static final String PERFORMANCE = "performance";
    public static final String PERFREF = "perfref";
    public static final String POLAR = "polar";

    public static final String RIG = "rig";
    public static final String RIGREF = "rigref";

    public static final String SAIL = "sail";
    public static final String SPINPOLE = "spinpole";
    public static final String SPEED = "speed";
    public static final String SPLINE2 = "spline2";
    public static final String SPLINE3 = "spline3";
    public static final String START = "start";

    public static final String TWA = "twa";
    public static final String TYPE = "type";

    public static final String X = "x";
    public static final String Y = "y";

    private static final String SCHEMA_FILE_NAME = "boatdef.xsd";

    private static Namespace namespace;

    Map<String, BoatType> boatTypes;

    public BoatBuilder() {
        boatTypes = new HashMap<>();
    }

    public Boat buildBoat(String typeName, String boatName, Color colour) {
        BoatType type = boatTypes.get(typeName);
        Boat boat = new Boat(type, boatName, colour);
        return boat;
    }


    public void loadDesigns(File xmlFile) {
        Document dom = parseXmlFile(xmlFile);
        analyzeDOM(dom);
    }

    public Document parseXmlFile(File xmlFile) {
        File xsdFile = new File("xml", SCHEMA_FILE_NAME);

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

    public void analyzeDOM(Document dom) {
        HashMap<String, Hull> hulls = new HashMap<>();
        HashMap<String, Rig> rigs = new HashMap<>();
        Element root = dom.getRootElement();
        namespace = root.getNamespace();

        List<Element> hullElts = root.getChildren(HULL, namespace);
        for (Element he : hullElts) {
            Hull hull = getHull(he, namespace);
            hulls.put(hull.getId(), hull);
        }

        List<Element> rigElts = root.getChildren(RIG, namespace);
        for (Element re : rigElts) {
            Rig rig = getRig(re, namespace);
            rigs.put(rig.getId(), rig);
        }

        List<Element> boatTypeElts = root.getChildren(BOATTYPE, namespace);
        for (Element bte : boatTypeElts) {
            Hull hull = resolveHullRef(bte, hulls);
            System.out.println("Hull = " + hull);
            Rig rig = resolveRigRef(bte, rigs);

            String id = bte.getAttributeValue(ID);
            System.out.println(" for boat type " + id);
            BoatType boatType = new BoatType(id, hull, rig, null);
            boatTypes.put(boatType.getId(), boatType);
        }
    }

    private Hull resolveHullRef(Element bte, HashMap<String, Hull> hulls) {
        Element hullDef = bte.getChild(HULL, namespace);
        Element hullRef = bte.getChild(HULLREF, namespace);
        Hull hull = null;
        System.out.println("hulldef = " + hullDef);
        System.out.println("hullref = " + hullRef);
        if (null != hullDef) {
            hull = getHull(hullDef, namespace);
        }
        else if (null != hullRef) {
            String id = hullRef.getAttributeValue(ID);
            hull = hulls.get(id);
        }
        return hull;
    }

    private Rig resolveRigRef(Element bte, HashMap<String, Rig> rigs) {
        Element rigDef = bte.getChild(RIG, namespace);
        Element rigRef = bte.getChild(RIGREF, namespace);
        Rig rig = null;

        if (null != rigDef) {
            rig = getRig(rigDef, namespace);
        }
        else if (null != rigRef) {
            String id = rigRef.getAttributeValue(ID);
            rig = rigs.get(id);
        }
        return rig;
    }

    private Hull getHull(Element hullElt, Namespace namespace) {
        String id = hullElt.getAttributeValue(ID);
        double beam = getDoubleValue(hullElt, BEAM);
        double len = getDoubleValue(hullElt, LENGTH);
        Hull hull = new Hull(id, beam, len);
        Shape shape = getShape(hullElt);
        hull.setShape(shape);

        return hull;
    }

    private Shape getShape(Element hullElt) {
        Path2D.Double path = new Path2D.Double();
        for (Element ce : hullElt.getChildren()) {
            double x = getDoubleValue(ce, X);
            double y = getDoubleValue(ce, Y);
            double cx1 = getDoubleValue(ce, CX1);
            double cy1 = getDoubleValue(ce, CY1);
            double cx2 = getDoubleValue(ce, CX2);
            double cy2 = getDoubleValue(ce, CY2);
            String tag = ce.getName();

            if (START.equals(tag)) path.moveTo(x, y);
            else if (LINE.equals(tag)) path.lineTo(x, y);
            else if (SPLINE2.equals(tag)) path.quadTo(cx1, cy1, x, y);
            else if (SPLINE3.equals(tag)) path.curveTo(cx1, cy1, cx2, cy2, x, y);
        }
        return path;
    }


    private Rig getRig(Element rigElt, Namespace namespace) {
        String id = rigElt.getAttributeValue(ID);
        double mastPos = getDoubleValue(rigElt, MASTPOS);
        double forestayPos = getDoubleValue(rigElt, FORESTAYPOS);
        Rig rig = new Rig(id, mastPos, forestayPos);

        Element spe = rigElt.getChild(SPINPOLE, namespace);
        double length = getDoubleValue(spe, LENGTH);
        rig.setPoleLen(length);

        List<Element> sailElts = rigElt.getChildren(SAIL, namespace);
        for (Element se : sailElts) {
            String type = se.getAttributeValue(TYPE);
            double chord = getDoubleValue(se, CHORD);
            double camber = getDoubleValue(se, CAMBER);
            double minAngle = getDoubleValue(se, MINANGLE);
            double maxAngle = getDoubleValue(se, MAXANGLE);
            double minTwa = getDoubleValue(se, MINTWA);
            Sail sail = new Sail(Sail.Type.main, chord, camber, minAngle, maxAngle, minTwa);
            rig.addSail(sail);
        }
        rig.createShapes();
        return rig;
    }

    private double getDoubleValue(Element hullElt, String attr) {
        try {
            String sval = hullElt.getAttributeValue(attr);
            if (null == sval) return Double.NaN;
            double dval = Double.valueOf(sval);
            return dval;
        }
        catch (NumberFormatException nfe) {
            nfe.printStackTrace();
            return Double.NaN;
        }
    }

    public static void main(String[] args) {
        BoatBuilder bb = new BoatBuilder();
        bb.loadDesigns(new File("xml", "boatdefs.xml"));
    }
}
