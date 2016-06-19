package even.rrs.animation;

import even.rrs.render.Drawable;
import even.rrs.render.Scene;
import even.rrs.render.SceneElement;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author even
 */
public class SceneManager
{
    List<Drawable> drawableItems;

    public SceneManager(Dimension dim) {
        drawableItems = new ArrayList<>();
    }

    public void add(Drawable d) {
        drawableItems.add(d);
    }

    public Scene makeScene(Dimension dim) {
        System.out.println("Creating scene items");
        Scene scene = new Scene(dim);

        for (Drawable d : drawableItems) {
            List<SceneElement> items = d.getSceneItems();
            for (SceneElement i : items) {
                System.out.println("Adding scene element " + i + " for " + d.getClass());
                scene.addItem(i);
            }
        }
        // System.out.println("makeScene() ->" + scene);
        return scene;
    }

}
