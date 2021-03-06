package even.rrs.render;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;


/**
 *
 * @author even
 */
public enum Flag
{

    CLASSFLAG,
    P, I, Z, BLACKFLAG,
    IND_RECALL, GEN_RECALL;

    private BufferedImage image;

    private Flag() {
        String filename = this.name() + ".png";
        URL url = ClassLoader.getSystemResource(filename);
        try {
            image = ImageIO.read(url);
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public BufferedImage getImage() {
        return image;
    }
}
