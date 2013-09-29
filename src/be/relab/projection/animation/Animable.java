package be.relab.projection.animation;

import java.io.InvalidClassException;

/**
 * Created with IntelliJ IDEA.
 * User: MacBookPro17
 * Date: 29/09/13
 * Time: 21:33
 * To change this template use File | Settings | File Templates.
 */
public interface Animable {
    public void animate();
    public void setAnimation(Animation a) throws InvalidClassException;
}
