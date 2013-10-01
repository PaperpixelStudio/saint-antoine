package be.relab.projection.core;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: MacBookPro17
 * Date: 29/09/13
 * Time: 20:00
 * To change this template use File | Settings | File Templates.
 */
public class Message {

    ArrayList<Word> words;
    float lifespan=500;
    Projection parent;
    public Message(Projection p){
        parent = p;
        words = new ArrayList<Word>();

    }

    public Message(Projection p, String message){
        PApplet.println(message);
        parent =p;
        words = new ArrayList<Word>();
        if(! message.contains(" ")){
            words.add(new Word(parent, message, PApplet.parseInt(parent.random(0,3))));
        }else{
            String[] wordSplit = message.split(" ");
            initFromArray(wordSplit);
        }
    }

    public Message(Projection p, String[] _words){
        parent =p;
        words=new ArrayList<Word>();
        initFromArray(_words);

    }
    private void initFromArray(String[] _words){
        for(int i=0; i<_words.length; i++){

            _words[i].replace("'","");
            _words[i].replace(",","");
            if(_words[i].length()>1){
                words.add(new Word(parent,_words[i],i%parent.LINES));
            }
        }
    }




    void addWord(Word w){
        words.add(w);
    }

    void display(){
        Iterator<Word> it = words.iterator();
        while(it.hasNext()){
            Word w = (Word)it.next();
            if(parent.mustUpdateGrid == true){
                w.updateLettersPosition();
            }
            w.display();
        }

        // lifespan--;
    }

    boolean isDead(){

        return lifespan < 0;
    }
}
