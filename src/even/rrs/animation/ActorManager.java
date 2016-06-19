package even.rrs.animation;

import even.rrs.render.SceneElement;
import even.rrs.render.Scene;
import java.util.ArrayList;
import java.util.List;


/**
 * Handles everything that is visible in the animation
 *
 * @author even
 */
public class ActorManager
{
    List<Actor> actors;

    public ActorManager() {
        actors = new ArrayList<>();
    }

    public void addActor(Actor a) {
        actors.add(a);
    }

    public void act(int time) {
        System.out.println("\nActing out the action for time " + time);
        for (Actor a : actors) {
            System.out.println("Actor " + a + " act() < --" + time);
            a.act(time);
        }
    }

//    public List<Actor> getActors() {
//        return actors;
//    }
}
