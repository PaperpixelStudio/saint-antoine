package be.relab.projection.animation;

import be.relab.projection.core.Projection;

/**
 * Created with IntelliJ IDEA.
 * User: MacBookPro17
 * Date: 29/09/13
 * Time: 21:33
 * To change this template use File | Settings | File Templates.
 */
public abstract class Animation {

    protected Animable obj;
    protected Projection parent;
    Animation(Projection p){
        parent = p;
    }
    public abstract void animate(Animable a);

}
