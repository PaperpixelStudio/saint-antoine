package be.relab.projection.core;

import be.relab.projection.animation.*;
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
    protected Projection parent;
    protected int lineNumber;
    protected PVector position;

    protected float size;

    Word(Projection p,String word, int lineN){
        parent = p;
        lineNumber = lineN;
        position = new PVector();
        letters = new ArrayList<Letter>();
        setPosFromLine();

       for(int i = 0; i < word.length(); i++){
           Letter l =  new Letter(word.charAt(i));
           l.setPosition(new PVector(position.x+(p.vitRectWidth*i)+(p.colonne*i)-(10*i)+parent.vitRectWidth/2, position.y+parent.vitRectHeight));
           LetterChangeSize la = new LetterChangeSize(parent);
           l.setAnimation(la);
           letters.add(l);
       }
    }
    private void setPosFromLine(){
        position.y = (70+parent.vitRectHeight)+(parent.vitRectHeight*lineNumber)+5;
        position.x = parent.marginLeft;


    }
    public void display(){
        animate();
        Iterator i = letters.iterator();
        while(i.hasNext()){
            Letter l = (Letter) i.next();
            l.display(parent);


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
