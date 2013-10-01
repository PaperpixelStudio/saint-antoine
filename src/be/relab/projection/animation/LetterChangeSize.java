package be.relab.projection.animation;

import be.relab.projection.core.Letter;
import be.relab.projection.core.Projection;
import processing.core.PApplet;

/**
 * Created with IntelliJ IDEA.
 * User: MacBookPro17
 * Date: 30/09/13
 * Time: 00:32
 * To change this template use File | Settings | File Templates.
 */
public class LetterChangeSize extends LetterAnimation {
    boolean grow;
    public LetterChangeSize(Projection p) {
        super(p);
        grow = false;
    }

    @Override
    public void animate(Animable a) {

        Letter l = (Letter) a;
        if(! grow){
            l.setSize(l.getSize()-1);
        }else{
            l.setSize(l.getSize()+1);
        }

        // l.setAngle(- l.getSize());
        if(l.getSize() > 360 || l.getSize() < 10){
            grow = ! grow;
        }
    }
}
