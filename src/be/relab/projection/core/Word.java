package be.relab.projection.core;

import be.relab.projection.animation.*;
import processing.core.PApplet;
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

    public Word(Projection p,String word, int lineN){
        parent = p;
        lineNumber = lineN;
        position = new PVector();
        letters = new ArrayList<Letter>();
        setPosFromLine();
        initLetters(word);
        updateLettersPosition();
    }

    private void setPosFromLine(){
        position.x = parent.MARGIN_LEFT;
        position.y = parent.VIT_POS_Y+(parent.RECT_HEIGHT)+(parent.RECT_HEIGHT *lineNumber)+5;
    }
   private void initLetters(String word){
       for(int i = 0; i < word.length(); i++){
           Letter l =  new Letter(word.charAt(i));
           // creates a new Default animation and assign it to letters
           // LetterChangeSize la = new LetterChangeSize(parent);
           LetterRandomize lr = new LetterRandomize(parent, parent.random(0.0001f,0.9f), parent.random(0.001f,0.9f));
           // l.setAnimation(lr);
           letters.add(l);
       }
   }

    public void updateLettersPosition(){
        for(int i=0;i<letters.size(); i++){
            Letter l = (Letter) letters.get(i);
            l.setPosition(
                new PVector(
                    position.x+(parent.RECT_WIDTH *i)+(parent.COLONNE *i)-(10*i)+parent.RECT_WIDTH /2,
                    position.y+parent.RECT_HEIGHT-5
                )
            );
        }
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

    public void setPartialAnimation(Animation a, String type){
        ArrayList<Letter> res = new ArrayList<Letter>();
        for(Letter l:letters){
            switch(l.content.charAt(0)){
                case 'a':case 'A':
                case 'e':case 'E':
                case 'i':case 'I':
                case 'o':case 'O':
                case 'u':case 'U':
                {
                    if(type.equals("vowel")){
                        l.setAnimation(a);
                    }
                    break;
                }
                default:{
                    if(type.equals("consonant")){
                        l.setAnimation(a);
                    }
                }

            }
        }

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
