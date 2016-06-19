package even.util;

import java.util.Comparator;
import java.util.PriorityQueue;


/**
 *
 * @author even
 */
public class EventManager
{
    PriorityQueue<Event> eventQ;
    int time;

    public EventManager() {
        eventQ = new PriorityQueue(new EventComparator());
        time = 0;
    }

    public int stepTime() {
        time++;
        Event e = eventQ.peek();
        while (e != null && e.getTime() == time) {
            e = eventQ.poll();
            e.happen();
        }
        return time;
    }

    public void addEvent(Event e) {
        eventQ.add(e);
    }


    private class EventComparator implements Comparator<Event>
    {

        @Override
        public int compare(Event o1, Event o2) {
            return o1.getTime() - o2.getTime();
        }

    }

}
