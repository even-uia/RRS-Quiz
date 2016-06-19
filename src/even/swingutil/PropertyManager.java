package even.swingutil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;


/**
 * This class provides a simple interface to using properties for various
 * settings. The class is a singleton. Call getInstance() to get the single
 * instance.
 *
 * The PropertyManager handles named sets of properties. To get a property value
 * two keys are needed: The property set name, and the property name. The
 * intention is to allow application writers to use more than one file for
 * settings
 *
 * If only the value of a single property is needed, call getProperty() to get
 * the value without getting the property manger first.
 *
 * @author evenal
 */
public class PropertyManager
{
    // the single instance
    private static final PropertyManager instance = new PropertyManager();

    private String[] propertyLocations;

    // mapping from
    private Map<String, Properties> propMap;

    private PropertyManager() {
        propMap = new HashMap();
        String sysConfDir = "NonExisting";
        String userConfDir = System.getProperty("user.home");
        String currentDir = System.getProperty("user.dir");
        propertyLocations = new String[]{sysConfDir, userConfDir, currentDir};
    }

    /**
     * Get the single instance
     */
    public static PropertyManager getInstance() {
        // System.out.println("returning " + instance);
        return instance;
    }

    /**
     * Get a specific variant of the property set basename
     *
     * @param basename the name of the property set, e.g guistrings
     * @param variant the name of the variant. e.g. language
     */
    public Properties getProperties(String basename, String variant) {
        String variantName = basename + "_" + variant;
        Properties props = getProps(variantName);
        if (props == null) props = getProps(basename);
        return props;
    }

    private Properties getProps(String name) {
        String filename = name + ".properties";
        Properties defaults = loadDefaults(filename);

        for (String loc : propertyLocations) {
            File propFile = new File(loc, filename);
            if (propFile.canRead()) {
                try (InputStream in = new FileInputStream(propFile)) {
                    Properties props = new Properties(defaults);
                    props.load(in);
                    defaults = props;
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return defaults;
    }

    private Properties loadDefaults(String filename) {
        Properties props = new Properties();
        try (InputStream in
                = this.getClass().getResourceAsStream("/" + filename)) {
            if (null != in) {
                props.load(in);
            }
        }
        catch (IOException e) {
            return null;
        }
        return props;
    }

    private Properties getLocalizedProperties(String basename, Locale locale) {
        return getProperties(basename, locale.toString());
    }

}
