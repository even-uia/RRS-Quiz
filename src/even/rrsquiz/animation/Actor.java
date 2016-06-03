package even.rrsquiz.animation;

/**
 *
 * @author even
 */
public interface Actor
{

    /**
     * Update actor state for the next frame
     *
     * @param frame
     */
    public void update(int frame);

    /**
     * Make the actor ready to start the animation
     *
     * @return
     */
    public void setup();

    public void reset();

    public void setHail(String hail);

    public String getHail();
}
