package even.rrsquiz;

import even.rrsquiz.animation.AbstractEvent;
import java.util.ArrayList;
import javax.swing.Action;

/**
 *
 * @author even
 */
public class Response
{

    private String label;
    private String description;
    private int points;
    private ArrayList<AbstractEvent> events;

    public Response() {
        label = null;
        description = null;
        points = 0;
        events = new ArrayList<>();
    }

    public ArrayList<AbstractEvent> getEvents() {
        return events;
    }

    public void AddAction(AbstractEvent a) {
        events.add(a);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

}
