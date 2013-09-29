package be.relab.projection.core;

import be.relab.projection.animation.Animable;
import be.relab.projection.animation.Animation;
import be.relab.projection.animation.WordAnimation;
import processing.core.PVector;

import java.io.InvalidClassException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: MacBookPro17
 * Date: 29/09/13
 * Time: 20:00
 * To change this template use File | Settings | File Templates.
 */
public class Word implements Animable {

    Animation animation;
    protected ArrayList<Letter> letters;

    protected int lineNumber;
    protected PVector position;

    protected float size;

    Word(String word){

    }

    public void display(){
        animate();
        Iterator i = letters.iterator();
        while(i.hasNext()){
            Letter l = (Letter) i.next();
            l.display();


        }
    }

    public void setLettersAnimation(){

    }

    @Override
    public void animate() {
        if(animation != null){
            animation.animate(this);
        }
    }

    @Override
    public void setAnimation(Animation a) throws InvalidClassException{
        if(a.getClass() != WordAnimation.class){
            throw new InvalidClassException("Parameter must be WordAnimation");
        }
        animation = a;
    }
}
