/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package even.rrsquiz.animation.event;

import even.rrsquiz.animation.AbstractEvent;
import even.rrsquiz.animation.boat.Sailboat;

/**
 *
 * @author even
 */
public class AutoTrimEvent extends AbstractEvent
{

    private boolean auto;

    public AutoTrimEvent(int frame, Sailboat sailboat, boolean autoTrim) {
        super(frame, sailboat);
        this.auto = autoTrim;
    }

    public void happen() {
        Sailboat sailboat = (Sailboat) actor;
        sailboat.setAutoTrim(auto);
    }
}
