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
public class BoatBuilder implements XmlNameConstants
{
    private static final String XSDFILE = "boatdata.xsd";
    private static Namespace namespace;
    Map<String, BoatType> boatTypes;
    HashMap<String, Hull> hulls;
    XmlParser parser;

    public BoatBuilder() {
        hulls = new HashMap<>();
        boatTypes = new HashMap<>();
    }

    public Boat buildBoat(String typeName, String boatName, Color colour) {
        BoatType type = boatTypes.get(typeName);
        Boat boat = new Boat(type, boatName, colour);
        return boat;
    }

    public void loadFile(String filename) {
        if (parser != null) {
            Document doc = parser.parseXmlFile(filename, XSDFILE, "designs");
            loadDesigns(doc);
        }

    }

    public void loadDesigns(Document dom) {
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
        double beam = parser.getDoubleValue(hullElt, BEAM);
        double len = parser.getDoubleValue(hullElt, LENGTH);
        Hull hull = new Hull(id, beam, len);
        Shape shape = getShape(hullElt);
        hull.setShape(shape);

        return hull;
    }

    private Shape getShape(Element hullElt) {
        Path2D.Double path = new Path2D.Double();
        for (Element ce : hullElt.getChildren()) {
            double x = parser.getDoubleValue(ce, X);
            double y = parser.getDoubleValue(ce, Y);
            double cx1 = parser.getDoubleValue(ce, CX1);
            double cy1 = parser.getDoubleValue(ce, CY1);
            double cx2 = parser.getDoubleValue(ce, CX2);
            double cy2 = parser.getDoubleValue(ce, CY2);
            String tag = ce.getName();

            if (START.equals(tag)) path.moveTo(x, y);
            else if (LINE.equals(tag)) path.lineTo(x, y);
            else if (SPLINE2.equals(tag)) path.quadTo(cx1, cy1, x, y);
            else if (SPLINE3.equals(tag))
                path.curveTo(cx1, cy1, cx2, cy2, x, y);
        }
        return path;
    }

    private Rig getRig(Element rigElt, Namespace namespace) {
        String id = rigElt.getAttributeValue(ID);
        Rig rig = new Rig(id);

        Element spe = rigElt.getChild(SPINPOLE, namespace);
        double length = parser.getDoubleValue(spe, LENGTH);
        rig.setPoleLen(length);

        List<Element> sailElts = rigElt.getChildren(SAIL, namespace);
        for (Element se : sailElts) {
            String type = se.getAttributeValue(TYPE);
            double x = parser.getDoubleValue(se, X);
            double y = parser.getDoubleValue(se, Y);
            double chord = parser.getDoubleValue(se, CHORD);
            double camber = parser.getDoubleValue(se, CAMBER);
            double minAngle = parser.getDoubleValue(se, MINANGLE);
            double maxAngle = parser.getDoubleValue(se, MAXANGLE);
            double minTwa = parser.getDoubleValue(se, MINTWA);
            Sail sail = new Sail(Sail.Type.valueOf(type),
                                 x, y, chord, camber,
                                 minAngle, maxAngle, minTwa);
            rig.addSail(sail);
        }
        return rig;
    }
}
