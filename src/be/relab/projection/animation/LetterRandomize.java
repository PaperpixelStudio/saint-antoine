package be.relab.projection.animation;

import be.relab.projection.core.Letter;
import be.relab.projection.core.Projection;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * Created with IntelliJ IDEA.
 * User: MacBookPro17
 * Date: 30/09/13
 * Time: 14:52
 * To change this template use File | Settings | File Templates.
 */
public class LetterRandomize extends LetterAnimation {
    float offsetX,offsetY;

    public LetterRandomize(Projection p, float _offsetX,float _offsetY) {
        super(p);
        offsetX = _offsetX;
        offsetY = -_offsetY;
    }
    @Override
    public void animate(Animable a){

        float noise = parent.noise(offsetX,offsetY)*.95f*parent.random(-1,1);
        float x = ((Letter)a).getPosition().x += noise;
        float y = ((Letter)a).getPosition().y += noise;
        ((Letter) a).setPosition(new PVector(x,y));
        offsetX+=.0001f;
        offsetY+=.003f;
        ((Letter)a).setSize(parent.noise(offsetX,offsetY)*500);

    }
}
