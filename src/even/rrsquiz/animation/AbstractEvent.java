/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package even.rrsquiz.animation;

import even.util.Event;


/**
 *
 * @author even
 */
public abstract class AbstractEvent implements Event
{

    protected int time;
    protected Actor actor;


    public AbstractEvent(int frame, Actor actor) {
        this.time = frame;
        this.actor = actor;
    }


    @Override
    public int getTime() {
        return time;
    }

}
