package be.relab.projection.animation;

import be.relab.projection.core.Letter;
import be.relab.projection.core.Projection;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * Created with IntelliJ IDEA.
 * User: MacBookPro17
 * Date: 1/10/13
 * Time: 17:49
 * To change this template use File | Settings | File Templates.
 */
public class LetterFall extends LetterAnimation{
    PVector velocity;
    PVector accel;
    PVector gravity;

    public LetterFall(Projection p) {
        super(p);
        velocity = new PVector(p.random(0),p.random(2.5f));
        gravity  = new PVector(0,p.random(-.3f,6));
        accel = new PVector(0,0);
    }


    public void animate(Animable l){

        if(((Letter) l).getPosition().y > 1024){
            ((Letter) l).position.y=0;
            velocity.mult(0);
        }

        PVector force = PVector.div(gravity,((Letter)l).content.charAt(0));

        velocity.add(accel);
        velocity.add(force);
        ((Letter)l).position.add(velocity);
        accel.mult(0);



    }


}
